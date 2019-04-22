package vue;

import modele.Ascenseur;
import modele.Personne;

import java.awt.*;

public class DessinAscenseur {

    private Ascenseur ascenseur;
    private int abscisse;
    private int ordonnee;
    private int longueur;
    private int hauteur;

    public DessinAscenseur(int abscisse, int ordonnee, int longueur, int hauteur, Ascenseur ascenseur) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.ascenseur = ascenseur;
    }

    public void dessiner(Graphics g) {
        if (ascenseur.isEnMouvement())
            g.setColor(Color.RED);

        g.drawRect(abscisse,
                ordonnee - hauteur,
                longueur,
                hauteur);

        g.setColor(Color.BLACK);

        int decalage = 15;
        for (Personne p : ascenseur.getPersonnes()) {
            new DessinPersonne(
                    abscisse + decalage,
                    ordonnee
            ).dessiner(g);
            decalage += 15;
        }
    }
}
