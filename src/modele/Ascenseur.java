package modele;

import modele.evenement.DepartAscenseur;

import java.util.ArrayList;
import java.util.List;

public class Ascenseur {
    private int etageCourant;
    private int sens;
    private List<Personne> personnes;
    private GestionnaireEvenement gestionnaireEvenements;
    private boolean enMouvement;

    public Ascenseur(GestionnaireEvenement gestionnaireEvenements) {
        this.etageCourant = 1;
        this.sens = 0;
        this.gestionnaireEvenements = gestionnaireEvenements;
        this.personnes = new ArrayList<>();
        this.enMouvement = false;
    }

    public boolean isEnMouvement() {
        return enMouvement;
    }

    public void setEnMouvement(boolean enMouvement) {
        this.enMouvement = enMouvement;
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
        // faire déplacer l'ascenseur
        gestionnaireEvenements.ajouterEvenement(
                new DepartAscenseur(
                        gestionnaireEvenements.getHorloge().getHeure(),
                        this,
                        etageDestination,
                        batiment));

    }

    /**
     * Choisir la prochaine destination
     * selon les clients qui attendent ou
     * la politique de marche au ralenti s'il personne n'attend.
     *
     * @param batiment Le bâtiment dans lequel se déplace l'ascenseur
     */
    public synchronized void choisirProchaineDemande(Batiment batiment) {
        if (batiment.getPersonnes().isEmpty()) {
            // suivre la stratégie de marche au ralentit
            traiterRalenti(batiment);
        } else if (personnes.isEmpty()) {
            // si l'ascenseur est vide, chercher le prochain client à récupérer
            //      - récupérer la liste des personnes qui attendent
            List<Personne> personnesEnAttente = new ArrayList<>();
            for (Personne p : batiment.getPersonnes())
                if (p.getAscenseur() == null)
                    personnesEnAttente.add(p);
            //      - choisir la personne à aller chercher
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
                demandeChoisie = personnes.get(0);
                break;
            case sstf:
                // chercher la demande la plus proche
                int distanceMin = 1000;
                int distance;
                for (Personne p : personnes) {
                    if (deposer)
                        distance = Math.abs(etageCourant - p.getNumeroEtageCible());
                    else
                        distance = Math.abs(etageCourant - p.getNumeroEtageCourant());

                    if (distance < distanceMin) {
                        demandeChoisie = p;
                        distanceMin = distance;
                    }
                }
                break;
        }
        if (deposer)
            traiterDemande(demandeChoisie.getNumeroEtageCible(), batiment);
        else
            traiterDemande(demandeChoisie.getNumeroEtageCourant(), batiment);
    }

    /**
     * Chosiir la dsetination selon la politique de marche au ralenti.
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
            default:
                // immobile par défaut
                bouger = false;
                break;
        }

        // générer l'événement de départ de l'ascenseur s'il doit bouger
        if (bouger)
            gestionnaireEvenements.ajouterEvenement(
                    new DepartAscenseur(
                            gestionnaireEvenements.getHorloge().getHeure(),
                            this, etageDestination,
                            batiment));
    }
}
