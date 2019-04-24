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

    /**
     * Calculer le temps de travail de la personne (avant qu'elle ne redemande un ascenseur).
     *
     * @return Le temps de travail
     */
    public int calculerTempsTravail() {
        double u = Math.random();
        return (int) (-Math.log(1 - u) * Constante.tempsTravail);
    }

    /**
     * Générer aléatoirement l'étage de destination de la personne.
     *
     * @param nombreEtages Nombre d'étages dans le bâtiment
     * @return L'étage de destination de la personne (différent de 1 : l'étage de départ)
     */
    private int genererEtageCible(int nombreEtages) {
        int etageDestination = (int) (Math.random() * nombreEtages);
        while (etageDestination == 1)
            etageDestination = (int) (Math.random() * nombreEtages);
        return etageDestination;
    }
}
