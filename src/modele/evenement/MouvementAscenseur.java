package modele.evenement;

import modele.Ascenseur;
import modele.Personne;

import java.util.ArrayList;
import java.util.List;

public class MouvementAscenseur extends Evenement {
    private Ascenseur ascenseur;
    private int etage;
    private Personne personne;

    public MouvementAscenseur(int temps, Ascenseur ascenseur, int etage, Personne personne) {
        super(temps);
        this.ascenseur = ascenseur;
        this.etage = etage;
        this.personne = personne;
    }

    @Override
    public void executer() {
        if (ascenseur.getEtageCourant() < etage)
            ascenseur.setSens(-1);
        else if (ascenseur.getEtageCourant() > etage)
            ascenseur.setSens(1);

        this.ascenseur.setEtageCourant(etage);


        System.out.println("déplacement en " + temps + " vers " + etage);
    }

    @Override
    public List<Evenement> genererProchainsEvenements() {
       return new ArrayList<>();
    }
}
