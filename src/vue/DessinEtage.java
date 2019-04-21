package vue;

import modele.Etage;

import javax.swing.*;
import java.awt.*;

public class DessinEtage {
    private Etage etage;
    private int abscisse;
    private int ordonnee;
    private int longueur;

    public DessinEtage(Etage etage, int abscisse, int ordonnee, int longueur) {
        this.etage = etage;
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
        this.longueur = longueur - abscisse;
    }

    public void dessiner(Graphics g) {
        g.drawLine(abscisse, ordonnee, abscisse + longueur, ordonnee);
        g.drawChars(new char[]{(char) (etage.getNumeroEtage()+48)}, 0, 1, abscisse + longueur - 10 , ordonnee - 5);
    }
}
