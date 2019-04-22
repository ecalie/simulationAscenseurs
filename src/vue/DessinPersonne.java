package vue;

import java.awt.*;

public class DessinPersonne {

    private int abscisse;
    private int ordonnee;

    public DessinPersonne(int abscisse, int ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    public void dessiner(Graphics g) {
        g.drawArc(abscisse - 5, ordonnee - 40, 10, 10, 0, 360);
        g.drawLine(abscisse, ordonnee - 30, abscisse, ordonnee - 15);
        g.drawLine(abscisse, ordonnee - 15, abscisse - 10, ordonnee);
        g.drawLine(abscisse, ordonnee - 15, abscisse + 10, ordonnee);
        g.drawLine(abscisse - 10, ordonnee - 25, abscisse + 10, ordonnee - 25);
    }
}
