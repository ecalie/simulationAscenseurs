package modele.evenement;

import modele.Batiment;
import modele.Constante;
import modele.Personne;
import vue.FenetreLogging;

import java.util.ArrayList;
import java.util.List;

public class ArriveeClient extends Evenement {
    private Personne personne;

    public ArriveeClient(int temps, Batiment batiment) {
        super(temps, batiment);
        this.personne = new Personne(batiment.getEtages().size());
    }

    public ArriveeClient(int temps, Batiment batiment, Personne personne) {
        super(temps, batiment);
        this.batiment = batiment;
        this.personne = personne;
    }

    public Personne getPersonne() {
        return personne;
    }

    @Override
    public List<Evenement> executer(FenetreLogging fenetreLogging) {
        // ajouter la personne dans le batiment
        batiment.ajouterPersonne(personne);

        // appeler un ascenseur
        batiment.demanderAscenseur(personne);

        fenetreLogging.ajouterEvenement(this);
        return genererProchainsEvenements();
    }

    private List<Evenement> genererProchainsEvenements() {
        int nombreArrivee = prochaineArrivee();
        int tempsProchaineArrivee = temps + 1;
        while (nombreArrivee == 0) {
            nombreArrivee = prochaineArrivee();
            tempsProchaineArrivee++;
        }

        List<Evenement> prochainesArrivees = new ArrayList<>();
        for (int i = 0; i < nombreArrivee; i++) {
            prochainesArrivees.add(new ArriveeClient(tempsProchaineArrivee, batiment));
        }

        return prochainesArrivees;
    }

    public int prochaineArrivee() {
        double u = Math.random();
        int k = 0;
        double p = probabilitePoisson(k);
        while (p < u) {
            p += probabilitePoisson(++k);
        }
        return k;
    }

    public double probabilitePoisson(int k) {
        return Math.pow(Constante.lambda, k) * Math.exp(-Constante.lambda) / factorielle(k);
    }

    public double factorielle(int k) {
        if (k == 0)
            return 1;
        else
            return k * factorielle(k - 1);
    }

    @Override
    public String toString() {
        return "un client arrive à l'étage " + personne.getNumeroEtageCourant() + " au temps " + temps;
    }
}
