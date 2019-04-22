package modele;

import modele.evenement.DescenteDeAscenseur;
import modele.evenement.MonteeDansAscenseur;
import modele.evenement.MouvementAscenseur;

import java.util.ArrayList;
import java.util.List;

public class Ascenseur {
    private int etageCourant;
    private int sens;
    private List<Personne> personnes;
    private int occupation;

    public Ascenseur() {
        this.etageCourant = 1;
        this.sens = 0;
        this.occupation = 0;
        this.personnes = new ArrayList<>();
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

    public int getOccupation() {
        return occupation;
    }

    public void notifierFifo(Demande demande, GestionnaireEvenement gestionnaireEvenements, Batiment batiment) {
        int tempsDepart1 = Math.max(gestionnaireEvenements.getHorloge().getHeure(), occupation);
        int tempsArrivee1 = tempsDepart1 + (Math.abs(this.etageCourant - demande.getEtageCourant())) * Constante.tempsDeplacement;
        int tempsMontee = tempsArrivee1 + 1;
        int tempsDepart2 = tempsMontee + 1;
        int tempsArrivee2 = tempsDepart2 + (Math.abs(demande.getEtageDestination() - demande.getEtageCourant())) * Constante.tempsDeplacement;
        int tempsDescente = tempsArrivee2 + 1;

        gestionnaireEvenements.ajouterEvenement(
                new MouvementAscenseur(
                        tempsArrivee1,
                        this,
                        demande.getEtageCourant(),
                        demande.getPersonne()));
        gestionnaireEvenements.ajouterEvenement(
                new MouvementAscenseur(
                        tempsArrivee2,
                        this,
                        demande.getEtageDestination(),
                        demande.getPersonne()));

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
        occupation = tempsDescente;
    }


    // TODO
    public void notifierScan(Demande demande, GestionnaireEvenement gestionnaireEvenements) {
        // regarder la demande est sur le chemin
        if (sens == 1 && etageCourant <= demande.getEtageCourant()) {

        }
    }
}
