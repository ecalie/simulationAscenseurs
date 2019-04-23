package modele;

public class Personne {
    private Ascenseur ascenseur;
    private int numeroEtageCourant;
    private int numeroEtageCible;
    private int heureArrivee;

    public Personne(int nombreEtages) {
        this.ascenseur = null;
        this.numeroEtageCourant = 1;
        this.numeroEtageCible = this.genererEtageCible(nombreEtages);
    }

    public int getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(int heureArrivee) {
        this.heureArrivee = heureArrivee;
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
        return (int) (-Math.log(1 - u) * Constante.tempsTravail);
    }

    private int genererEtageCible(int nombreEtages) {
        int etageDestination = (int) (Math.random() * nombreEtages);
        while (etageDestination == 1)
            etageDestination = (int) (Math.random() * nombreEtages);
        return etageDestination;
    }
}
