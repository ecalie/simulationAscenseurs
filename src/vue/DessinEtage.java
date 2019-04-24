package vue;

import java.awt.*;

public class DessinEtage {
    private int numeroEtage;
    private int abscisse;
    private int ordonnee;
    private int longueur;

    public DessinEtage(int numeroEtage, int abscisse, int ordonnee, int longueur) {
        this.numeroEtage = numeroEtage;
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
        this.longueur = longueur - abscisse;
    }

    public void dessiner(Graphics g) {
        // dessiner l'étage
        g.drawLine(abscisse, ordonnee, abscisse + longueur, ordonnee);

        // afficher le numéro de l'étage
        g.drawChars(new char[]{(char) (numeroEtage + 48)},
                0,
                1,
                abscisse + longueur - 10,
                ordonnee - 5);
    }
}
