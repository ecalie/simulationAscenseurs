package modele;

public class Personne /* extends Thread */ {
    private Ascenseur ascenseur;
    private int numeroEtageCourant;
    private int numeroEtageCible;
    private boolean enAttente;

    public Personne(int nombreEtages) {
        this.ascenseur = null;
        this.numeroEtageCourant = 1;
        this.enAttente = false;
        this.numeroEtageCible = this.genererEtageCible(nombreEtages);
    }

    public boolean isEnAttente() {
        return enAttente;
    }

    public void setEnAttente(boolean enAttente) {
        this.enAttente = enAttente;
    }

    public Ascenseur getAscenseur() {
        return ascenseur;
    }

    public void setAscenseur(Ascenseur ascenseur) {
        this.ascenseur = ascenseur;
    }

    public int getNumeroEtageCourant() {
        return numeroEtageCourant;
    }

    public void setNumeroEtageCourant(int numeroEtageCourant) {
        this.numeroEtageCourant = numeroEtageCourant;
    }

    public int getNumeroEtageCible() {
        return numeroEtageCible;
    }

    public void setNumeroEtageCible(int numeroEtageCible) {
        this.numeroEtageCible = numeroEtageCible;
    }

    public int calculerTempsTravail() {
        double u = Math.random();
        return (int) (-Math.log(1 - u) * (60 * 6));
    }

    private int genererEtageCible(int nombreEtages) {
        int etageDestination = (int) (Math.random() * nombreEtages);
        while (etageDestination == 1)
            etageDestination = (int) (Math.random() * nombreEtages);
        return etageDestination;
    }
}
