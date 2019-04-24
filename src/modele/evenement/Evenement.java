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

    /**
     * Exécuter l'évement.
     *
     * @param fenetreLogging Fenetre où sont affichés tous les événements
     * @return La liste des prochains événements
     */
    public abstract List<Evenement> executer(FenetreLogging fenetreLogging);

}
