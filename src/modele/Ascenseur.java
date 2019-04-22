package modele;

import modele.evenement.ArriveeAscenseur;
import modele.evenement.DepartAscenseur;
import modele.evenement.DescenteDeAscenseur;
import modele.evenement.MonteeDansAscenseur;

import java.util.ArrayList;
import java.util.List;

public class Ascenseur {
    private int etageCourant;
    private int sens;
    private List<Personne> personnes;
    private int tempsOccupation;
    private GestionnaireEvenement gestionnaireEvenements;
    private boolean enMouvement;

    public Ascenseur(GestionnaireEvenement gestionnaireEvenements) {
        this.etageCourant = 1;
        this.sens = 0;
        this.tempsOccupation = 0;
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

    public int getTempsOccupation() {
        return tempsOccupation;
    }

    public void traiterDemande(Demande demande, Batiment batiment) {
        int tempsDepart1 = Math.max(gestionnaireEvenements.getHorloge().getHeure(), tempsOccupation);
        int tempsArrivee1 = tempsDepart1 + (Math.abs(this.etageCourant - demande.getEtageCourant())) * Constante.tempsDeplacement;
        int tempsMontee = tempsArrivee1 + 1;
        int tempsDepart2 = tempsMontee + 1;
        int tempsArrivee2 = tempsDepart2 + (Math.abs(demande.getEtageDestination() - demande.getEtageCourant())) * Constante.tempsDeplacement;
        int tempsDescente = tempsArrivee2 + 1;

        // faire déplacer l'ascenseur
        gestionnaireEvenements.ajouterEvenement(
                new DepartAscenseur(tempsDepart1, this));

        gestionnaireEvenements.ajouterEvenement(
                new ArriveeAscenseur(
                        tempsArrivee1,
                        this,
                        demande.getEtageCourant()));

        gestionnaireEvenements.ajouterEvenement(
                new DepartAscenseur(tempsDepart2, this));

        gestionnaireEvenements.ajouterEvenement(
                new ArriveeAscenseur(
                        tempsArrivee2,
                        this,
                        demande.getEtageDestination()));

        // faire monter et descendre les personnes concernées
        gestionnaireEvenements.ajouterEvenement(
                new MonteeDansAscenseur(
                        tempsMontee,
                        this,
                        demande.getPersonne()));

        gestionnaireEvenements.ajouterEvenement(
                new DescenteDeAscenseur(
                        tempsDescente,
                        demande.getPersonne(),
                        this,
                        batiment));

        // mettre à jour le temps d'occupation de l'ascenseur
        tempsOccupation = tempsDescente;
    }

    public void choisirProchaineDemande(List<Demande> demandes, Batiment batiment) {
        synchronized (demandes) {
            Demande demandeChoisie = null;
            if (!demandes.isEmpty()) {
               switch(Constante.strategieService) {
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
                traiterDemande(demandeChoisie, batiment);
                demandes.remove(demandeChoisie);
            } else {
                // suivre la stratégie de marche au ralentit
                switch (Constante.strategieRalenti) {
                    case inferieur:
                        traiterRalenti(this.etageCourant - 1);
                        break;
                    case superieur:
                        traiterRalenti(this.etageCourant + 1);
                        break;
                    case milieu:
                        traiterRalenti(batiment.getEtages().size() / 2 + 1);
                        break;
                    case etage1:
                        traiterRalenti(1);
                        break;
                    default:
                        // immobile par défaut
                        break;
                }
            }
        }
    }

    private void traiterRalenti(int etageDestination) {
        int tempsDepart = Math.max(gestionnaireEvenements.getHorloge().getHeure(), tempsOccupation);
        int tempsArrivee = tempsDepart + (Math.abs(this.etageCourant - etageDestination)) * Constante.tempsDeplacement;

        gestionnaireEvenements.ajouterEvenement(
                new DepartAscenseur(tempsDepart, this)
        );

        gestionnaireEvenements.ajouterEvenement(
                new ArriveeAscenseur(tempsArrivee, this, etageDestination)
        );
    }
}
