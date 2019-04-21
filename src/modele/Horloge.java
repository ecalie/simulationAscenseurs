package modele;

public class Horloge {

    private int heure;

    public Horloge() {
        this.heure = 0;
    }

    public int getHeure() {
        return heure;
    }

    public void avancer(int temps) {
        this.heure = temps;
        try {
            Thread.sleep(Constante.tempsAttente);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
