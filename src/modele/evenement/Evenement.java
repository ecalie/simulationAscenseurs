package modele.evenement;

import modele.Batiment;
import vue.FenetreLogging;

import java.util.List;

public abstract class Evenement {
    protected int temps;
    protected Batiment batiment;

    public Evenement(int temps, Batiment batiment) {
        this.temps = temps;
        this.batiment = batiment;
    }

    public int getTemps() {
        return temps;
    }

    public abstract List<Evenement> executer(FenetreLogging fenetreLogging);

}
