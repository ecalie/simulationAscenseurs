package modele;

public class Demande {

    private int etageCourant;
    private int etageDestination;
    private Personne personne;

    public Demande(int etageCourant, int etageDestination, Personne personne) {
        this.etageCourant = etageCourant;
        this.etageDestination = etageDestination;
        this.personne = personne;
    }

    public int getEtageCourant() {
        return etageCourant;
    }

    public int getEtageDestination() {
        return etageDestination;
    }

    public Personne getPersonne() {
        return personne;
    }
}
