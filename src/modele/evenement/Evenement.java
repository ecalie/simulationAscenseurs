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

    /**
     * Condition qui doit être vérifiée arpès l'exécution de l'événement.
     * @return La condition
     */
    public abstract boolean postcondition();

    /**
     * Condition qui doit être vérifiée pour exécuter l'événement.
     * @return La condition
     */
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

    /**
     * Traiter l'événement et générer les prochains événements.
     * @return La liste des prochains événements
     */
    public abstract List<Evenement> executer();

    /**
     * Décrire l'événement.
     * @return Un chaîne de caractère décrivant l'événement
     */
    protected abstract String decrire();

    @Override
    public String toString() {
        return "t = " + temps + " : " + this.decrire();
    }
}
