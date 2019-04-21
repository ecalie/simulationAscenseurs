package modele.evenement;

import modele.Ascenseur;
import modele.Personne;

import java.util.ArrayList;
import java.util.List;

public class MonteeDansAscenseur extends Evenement {
    private Ascenseur ascenseur;
    private Personne personne;

    public MonteeDansAscenseur(int temps, Ascenseur ascenseur, Personne personne) {
        super(temps);
        this.ascenseur = ascenseur;
        this.personne = personne;
    }

    @Override
    public void executer() {
        this.personne.setAscenseur(ascenseur);
        this.ascenseur.getPersonnes().add(personne);
        personne.setEnAttente(false);

        System.out.println("montée en " + temps + " de " + personne.getNumeroEtageCourant() + " vers " + personne.getNumeroEtageCible());
    }

    @Override
    public List<Evenement> genererProchainsEvenements() {
        return new ArrayList<>();
    }
}
