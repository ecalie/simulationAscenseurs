package modele.evenement;

import modele.Ascenseur;

import java.util.ArrayList;
import java.util.List;

public class ArriveeAscenseur extends Evenement {
    private Ascenseur ascenseur;
    private int etage;

    public ArriveeAscenseur(int temps, Ascenseur ascenseur, int etage) {
        super(temps);
        this.ascenseur = ascenseur;
        this.etage = etage;
    }

    @Override
    public void executer() {
        if (ascenseur.getEtageCourant() < etage)
            ascenseur.setSens(-1);
        else if (ascenseur.getEtageCourant() > etage)
            ascenseur.setSens(1);

        this.ascenseur.setEtageCourant(etage);
        ascenseur.setEnMouvement(false);


        System.out.println("déplacement en " + temps + " vers " + etage);
    }

    @Override
    public List<Evenement> genererProchainsEvenements() {
        return new ArrayList<>();
    }
}
