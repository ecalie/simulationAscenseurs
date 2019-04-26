package modele.evenement;

import modele.Batiment;
import vue.FenetreLogging;

import java.util.ArrayList;
import java.util.List;

public abstract class Evenement {
    protected int temps;
    protected Batiment batiment;
    protected int priorite;

    public Evenement(int temps, Batiment batiment, int priorite) {
        this.temps = temps;
        this.batiment = batiment;
        this.priorite = priorite;
    }

    public int getTemps() {
        return temps;
    }

    public int getPriorite() {
        return priorite;
    }

    public abstract boolean postcondition();

    public abstract boolean precondition();

    /**
     * Exécuter l'évement.
     *
     * @param fenetreLogging Fenetre où sont affichés tous les événements
     * @return La liste des prochains événements
     */
    public List<Evenement> executer(FenetreLogging fenetreLogging) {
        if (precondition()) {
            List<Evenement> resultat = this.executer();
            if (postcondition()) {
                fenetreLogging.ajouterEvenement(this);
                return resultat;
            }
        }
        return new ArrayList<>();
    }

    public abstract List<Evenement> executer();

    protected abstract String decrire();

    @Override
    public String toString() {
        return "t = " + temps + " : " + this.decrire();
    }
}
