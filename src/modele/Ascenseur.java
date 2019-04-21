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

    public Ascenseur() {
        this.etageCourant = 1;
        this.sens = 0;
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

    public void notifierFifo(Demande demande, GestionnaireEvenement gestionnaireEvenements, Batiment batiment) {
        gestionnaireEvenements.ajouterEvenement(
                new MouvementAscenseur(
                        gestionnaireEvenements.getHorloge().getHeure() +
                                (Math.abs(this.etageCourant - demande.getEtageCourant())) * Constante.tempsDeplacement,
                        this,
                        demande.getEtageCourant(),
                        demande.getPersonne()));
        gestionnaireEvenements.ajouterEvenement(
                new MouvementAscenseur(
                        gestionnaireEvenements.getHorloge().getHeure() + 1 +
                                (Math.abs(this.etageCourant - demande.getEtageCourant())) * Constante.tempsDeplacement +
                                (Math.abs(demande.getEtageDestination() - demande.getEtageCourant())) * Constante.tempsDeplacement,
                        this,
                        demande.getEtageDestination(),
                        demande.getPersonne()));


        // faire monter et descendre les personnes concernées
        gestionnaireEvenements.ajouterEvenement(
                new MonteeDansAscenseur(
                        gestionnaireEvenements.getHorloge().getHeure() + 1 +
                                (Math.abs(this.etageCourant - demande.getEtageCourant())) * Constante.tempsDeplacement,
                        this,
                        demande.getPersonne()));

        gestionnaireEvenements.ajouterEvenement(
                new DescenteDeAscenseur(
                        gestionnaireEvenements.getHorloge().getHeure() + 2 +
                                (Math.abs(this.etageCourant - demande.getEtageCourant())) * Constante.tempsDeplacement +
                                (Math.abs(demande.getEtageDestination() - demande.getEtageCourant())) * Constante.tempsDeplacement,
                        demande.getPersonne(),
                        this,
                        batiment));
    }


    // TODO
    public void notifierScan(Demande demande, GestionnaireEvenement gestionnaireEvenements) {
        // regarder la demande est sur le chemin
        if (sens == 1 && etageCourant <= demande.getEtageCourant()) {

        }
    }
}
