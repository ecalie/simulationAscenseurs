package modele;

import modele.evenement.DepartAscenseur;
import modele.evenement.MonteeDansAscenseur;

import java.util.ArrayList;
import java.util.List;

public class Ascenseur {
    private int etageCourant;
    private int sens;
    private List<Personne> personnes;
    private GestionnaireEvenement gestionnaireEvenements;
    private boolean occupe;
    private boolean arrete;

    public Ascenseur(GestionnaireEvenement gestionnaireEvenements) {
        this.etageCourant = 1;
        this.sens = 0;
        this.gestionnaireEvenements = gestionnaireEvenements;
        this.personnes = new ArrayList<>();
        this.occupe = false;
        this.arrete = true;
    }

    public boolean isArrete() {
        return arrete;
    }

    public void setArrete(boolean arrete) {
        this.arrete = arrete;
    }

    public boolean isOccupe() {
        return occupe;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    public int getEtageCourant() {
        return etageCourant;
    }

    public void setEtageCourant(int etageCourant) {
        this.etageCourant = etageCourant;
    }

    public List<Personne> getPersonnes() {
        return personnes;
    }

    /**
     * Démarrer le déplacement de l'ascenseur.
     *
     * @param etageDestination L'étage de destinatin de l'ascensuer
     * @param batiment         Le bâtiment dans lequel se déplace l'ascenseur
     */
    public void traiterDemande(int etageDestination, Batiment batiment) {
        if (etageCourant != etageDestination) {
            // faire déplacer l'ascenseur
            gestionnaireEvenements.ajouterEvenement(
                    new DepartAscenseur(
                            gestionnaireEvenements.getHorloge().getHeure() + 1,
                            this,
                            etageDestination,
                            batiment));

            batiment.getAscenseursParEtages().set(etageCourant, batiment.getAscenseursParEtages().get(etageCourant) - 1);
            batiment.getAscenseursParEtages().set(etageDestination, batiment.getAscenseursParEtages().get(etageDestination) + 1);
        } else {
            //      - faire monter les personnes qui attendent dans l'ascenseur
            gestionnaireEvenements.ajouterEvenement(
                    new MonteeDansAscenseur(
                            gestionnaireEvenements.getHorloge().getHeure() + 1,
                            this,
                            batiment.getPersonnes(),
                            batiment));
        }
    }

    /**
     * Choisir la prochaine destination
     * selon les clients qui attendent ou
     * la politique de marche au ralenti s'il personne n'attend.
     *
     * @param batiment Le bâtiment dans lequel se déplace l'ascenseur
     */
    public synchronized void choisirProchaineDemande(Batiment batiment) {
        // récupérer la liste des personnes qui attendent
        List<Personne> personnesEnAttente = new ArrayList<>();
        for (Personne p : batiment.getPersonnes())
            if (p.getAscenseur() == null)
                personnesEnAttente.add(p);

        if (personnesEnAttente.isEmpty() && personnes.isEmpty()) {
            // si l'ascenseur est vide est personne n'attend d'ascenseur
            // suivre la stratégie de marche au ralentit
            traiterRalenti(batiment);
        } else {
            if (personnes.isEmpty())
                // si l'ascenseur est vide, chercher parmi la liste des personnes qui attendent l'ascenseur
                choisirDestination(personnesEnAttente, false, batiment);
            else if (Constante.strategieService == StrategieService.scan)
                // sinon si la stratégie est scan, chercher parmi les deux listes
                choisirDestination(personnes, personnesEnAttente, batiment, true, sens == 1);
            else
                // sinon choisir parmi les personnes dans l'ascenseur
                choisirDestination(personnes, true, batiment);
        }
    }

    /**
     * Choisir la prochaine destination.
     *
     * @param personnes La liste des personnes à satisfaire
     * @param deposer   Vrai si la destination est celle d'une personne dans l'ascenseur
     *                  Faux si la destination est celle d'une personne qui attend l'ascenseur
     * @param batiment  Le bâtiment dans lequel se déplace l'ascenseur
     */
    private void choisirDestination(List<Personne> personnes, boolean deposer, Batiment batiment) {
        Personne demandeChoisie = null;
        switch (Constante.strategieService) {
            case fcfs:
                // prendre la première demande qui n'est pas en cours de traitement
                for (Personne p : personnes) {
                    if (this.personnes.contains(p) || batiment.getAscenseursParEtages().get(p.getNumeroEtageCourant()) == 0) {
                        demandeChoisie = p;
                        break;
                    }
                }
                break;
            case sstf:
                // chercher la demande la plus proche
                int distanceMin = 1000;
                int distance;
                for (Personne p : personnes) {
                    if (this.personnes.contains(p) || batiment.getAscenseursParEtages().get(p.getNumeroEtageCible()) == 0) {
                        if (deposer)
                            distance = Math.abs(etageCourant - p.getNumeroEtageCible());
                        else
                            distance = Math.abs(etageCourant - p.getNumeroEtageCourant());

                        if (distance < distanceMin) {
                            demandeChoisie = p;
                            distanceMin = distance;
                        }
                    }
                }
                break;
        }

        if (demandeChoisie != null)
            // si toutes les traitements ne sont pas déjà en cours de traitement, traiter la demande choisie
            if (deposer)
                traiterDemande(demandeChoisie.getNumeroEtageCible(), batiment);
            else
                traiterDemande(demandeChoisie.getNumeroEtageCourant(), batiment);
        // sinon suivre le politique de marche au ralenti
        else
            traiterRalenti(batiment);
    }

    /**
     * Choisir la destination parmi la liste des personnes dans l'ascenseur et la liste des pesonnes qui attendent l'ascenseur.
     * Seulement dans le cas de Linear Scan
     *
     * @param personnesADeposer La liste des personnes dans l'ascenseur
     * @param personnesARecuperer La liste des personnes qui attendent l'ascenseur
     * @param batiment Le bâtiment
     * @param premierPassage Vrai si la fonction est exécutée pour la première fois
     *                       Faux sinon (deuxième exécution dans l'autre sens de déplacement si aucun demande trouvée dans le sens actuel de déplacement)
     * @param incrementer Vrai si l'ascenseur se déplace vers le haut du bâtiment
     *                    Faux sinon
     */
    private void choisirDestination(List<Personne> personnesADeposer,
                                    List<Personne> personnesARecuperer,
                                    Batiment batiment,
                                    boolean premierPassage,
                                    boolean incrementer) {
        int etage = etageCourant;

        boolean trouve = false;
        // Tant qu'on a pas trouvé de demande à traiter et qu'il reste des étages
        while (!trouve && etage >= 0 && etage < batiment.getNombreEtages()) {
            // chercher une demande parmi les personnes dans l'ascenseur
            for (Personne p : personnesADeposer)
                if (p.getNumeroEtageCible() == etage) {
                    trouve = true;
                    traiterDemande(p.getNumeroEtageCible(), batiment);
                    break;
                }

            // si personne ne descend à l'étage courant, regarder si quelqu'un attend l'ascenseur
            if (!trouve)
                for (Personne p : personnesARecuperer)
                    if (p.getNumeroEtageCourant() == etage) {
                        trouve = true;
                        traiterDemande(p.getNumeroEtageCourant(), batiment);
                        break;
                    }

                    // passer à l'étage suivant
            if (incrementer)
                etage++;
            else
                etage--;
        }

        // si aucune demande trouvée, on change le sens de déplacement et recommence
        if (!trouve)
            if (premierPassage) {
                choisirDestination(personnesADeposer, personnesARecuperer, batiment, false, !incrementer);
            } else {
                traiterRalenti(batiment);
            }
    }

    /**
     * Choisir la destination selon la politique de marche au ralenti.
     *
     * @param batiment
     */
    private void traiterRalenti(Batiment batiment) {
        this.sens = 0;
        int etageDestination = -1;
        boolean bouger = true;

        // choisir l'étage de destination selon la stratégie
        switch (Constante.strategieRalenti) {
            case inferieur:
                if (this.etageCourant > 0)
                    etageDestination = this.etageCourant - 1;
                else
                    bouger = false;
                break;
            case superieur:
                if (this.etageCourant < batiment.getNombreEtages() - 1)
                    etageDestination = this.etageCourant + 1;
                else bouger = false;
                break;
            case milieu:
                etageDestination = batiment.getNombreEtages() / 2;
                break;
            case etage1:
                etageDestination = 1;
                break;
            case immobile:
                bouger = false;
                break;
        }

        // générer l'événement de départ de l'ascenseur s'il doit bouger
        if (bouger && etageDestination != etageCourant) {
            gestionnaireEvenements.ajouterEvenement(
                    new DepartAscenseur(
                            gestionnaireEvenements.getHorloge().getHeure(),
                            this, etageDestination,
                            batiment));

            batiment.getAscenseursParEtages().set(etageCourant, batiment.getAscenseursParEtages().get(etageCourant) - 1);
            batiment.getAscenseursParEtages().set(etageDestination, batiment.getAscenseursParEtages().get(etageDestination) + 1);
        } else {
            occupe = false;
        }
    }
}
