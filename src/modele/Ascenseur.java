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

    public Ascenseur(GestionnaireEvenement gestionnaireEvenements) {
        this.etageCourant = 1;
        this.sens = 0;
        this.gestionnaireEvenements = gestionnaireEvenements;
        this.personnes = new ArrayList<>();
        this.occupe = false;
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

    public int getSens() {
        return sens;
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
        } else if (personnes.isEmpty()) {
            // si l'ascenseur est vide, chercher le prochain client à récupérer
            choisirDestination(personnesEnAttente, false, batiment);
        } else {
            // sinon choisir la prochaine personne à déposer
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
            case scan:
                // TODO
        }

        if (demandeChoisie != null)
            if (deposer)
                traiterDemande(demandeChoisie.getNumeroEtageCible(), batiment);
            else
                traiterDemande(demandeChoisie.getNumeroEtageCourant(), batiment);
        else
            traiterRalenti(batiment);
    }

    /**
     * Choisir la destination selon la politique de marche au ralenti.
     *
     * @param batiment
     */
    private void traiterRalenti(Batiment batiment) {
        int etageDestination = -1;
        boolean bouger = true;

        // choisir l'étage de destination selon la stratégie
        switch (Constante.strategieRalenti) {
            case inferieur:
                if (this.etageCourant > 0)
                    etageDestination = this.etageCourant - 1;
                break;
            case superieur:
                if (this.etageCourant < batiment.getNombreEtages() - 1)
                    etageDestination = this.etageCourant + 1;
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
