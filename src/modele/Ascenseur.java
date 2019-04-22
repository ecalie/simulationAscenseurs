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

    public void traiterDemande(int etageDestination, Batiment batiment) {
        // faire déplacer l'ascenseur
        gestionnaireEvenements.ajouterEvenement(
                new DepartAscenseur(
                        gestionnaireEvenements.getHorloge().getHeure(),
                        this,
                        etageDestination,
                        batiment));

    }

    public synchronized void choisirProchaineDemande(Batiment batiment) {
        if (batiment.getPersonnes().isEmpty()) {
            // suivre la stratégie de marche au ralentit
            switch (Constante.strategieRalenti) {
                case inferieur:
                    traiterRalenti(this.etageCourant - 1, batiment);
                    break;
                case superieur:
                    traiterRalenti(this.etageCourant + 1, batiment);
                    break;
                case milieu:
                    traiterRalenti(batiment.getEtages().size() / 2 + 1, batiment);
                    break;
                case etage1:
                    traiterRalenti(1, batiment);
                    break;
                default:
                    // immobile par défaut
                    break;
            }
        } else if (personnes.isEmpty()) {
            List<Personne> personnesEnAttente = new ArrayList<>();
            for (Personne p : batiment.getPersonnes())
                if (p.getAscenseur() == null)
                    personnesEnAttente.add(p);
            choisirDestination(personnesEnAttente, false, batiment);
        } else {
            choisirDestination(personnes, true, batiment);
        }
    }

    private void choisirDestination(List<Personne> personnes, boolean deposer, Batiment batiment) {
        Personne demandeChoisie = null;
        switch (Constante.strategieService) {
            case fcfs:
                demandeChoisie = personnes.get(0);
                break;
            case sstf:
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

    private void traiterRalenti(int etageDestination, Batiment batiment) {
        gestionnaireEvenements.ajouterEvenement(
                new DepartAscenseur(
                        gestionnaireEvenements.getHorloge().getHeure(),
                        this, etageDestination,
                        batiment));
    }
}
