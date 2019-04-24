package modele;

public class Horloge {

    private int heure;

    public Horloge() {
        this.heure = 0;
    }

    public int getHeure() {
        return heure;
    }

    /**
     * Faire avanncer l'horloge.
     *
     * @param temps Le temps du prochain événement
     */
    public void avancer(int temps) {
        if (heure != temps) {
            this.heure = temps;
            try {
                Thread.sleep(Constante.tempsAttente);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
