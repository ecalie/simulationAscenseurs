package modele.evenement;

import java.util.List;

public abstract class Evenement {
    protected int temps;

    public Evenement(int temps) {
        this.temps = temps;
    }

    public int getTemps() {
        return temps;
    }

    public abstract void executer();

    public abstract List<Evenement> genererProchainsEvenements();

}
