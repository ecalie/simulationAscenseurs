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

    public void choisirProchaineDemande(List<Demande> demandes, Batiment batiment) {
        synchronized (demandes) {
            Demande demandeChoisie = null;
            if (!demandes.isEmpty()) {
                switch (Constante.strategieService) {
                    case fcfs:
                        demandeChoisie = demandes.get(0);
                        break;
                    case sstf:
                        int distanceMin = 1000;
                        for (Demande d : demandes) {
                            int distance = Math.abs(etageCourant - d.getEtageCourant());
                            if (distance < distanceMin) {
                                demandeChoisie = d;
                                distanceMin = distance;
                            }
                        }
                        break;
                }
                traiterDemande(demandeChoisie.getEtageCourant(), batiment);
                demandes.remove(demandeChoisie);
            } else {
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
            }
        }
    }

    private void traiterRalenti(int etageDestination, Batiment batiment) {
        gestionnaireEvenements.ajouterEvenement(
                new DepartAscenseur(
                        gestionnaireEvenements.getHorloge().getHeure(),
                        this, etageDestination,
                        batiment));
    }
}
