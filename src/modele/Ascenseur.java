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
    private boolean occupe;

    public Ascenseur(GestionnaireEvenement gestionnaireEvenements) {
        this.etageCourant = 1;
        this.sens = 0;
        this.tempsOccupation = 0;
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
                if (Constante.strategieService == StrategieService.fcfs) {
                    demandeChoisie = demandes.get(0);
                } else if (Constante.strategieService == StrategieService.sstf) {
                    int distanceMin = 1000;
                    for (Demande d : demandes) {
                        int distance = Math.abs(etageCourant - d.getEtageCourant());
                        if (distance < distanceMin) {
                            demandeChoisie = d;
                            distanceMin = distance;
                        }
                    }
                }
                traiterDemande(demandeChoisie, batiment);
                demandes.remove(demandeChoisie);
            }
        }
    }
}
