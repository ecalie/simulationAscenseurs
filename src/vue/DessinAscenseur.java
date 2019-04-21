package vue;

import modele.Personne;

import java.awt.*;
import java.util.List;

public class DessinAscenseur {

    private List<Personne> personnes;
    private int abscisse;
    private int ordonnee;
    private int longueur;
    private int hauteur;

    public DessinAscenseur(int abscisse, int ordonnee, int longueur, int hauteur, List<Personne> personnes) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.personnes = personnes;
    }

    public void dessiner(Graphics g) {
        g.drawRect(abscisse,
                ordonnee - hauteur,
                longueur,
                hauteur);

        int decalage = 15;
        for (Personne p : personnes) {
            new DessinPersonne(
                    abscisse + decalage,
                    ordonnee
            ).dessiner(g);
            decalage += 15;
        }
    }
}
