package vue;

import modele.Ascenseur;
import modele.Batiment;
import modele.Horloge;
import modele.Personne;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DessinBatiment extends JFrame {
    private Batiment batiment;
    private Horloge horloge;

    public DessinBatiment(Batiment batiment, Horloge horloge) {
        super("Simulation du pavillon de l'humanité");
        this.batiment = batiment;
        this.horloge = horloge;
        this.setVisible(true);
        this.setSize(new Dimension(500, 500));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int width = this.getWidth();
        int height = this.getHeight();

        // dessiner les étages
        for (int i = 0; i < batiment.getNombreEtages(); i++)
            new DessinEtage(
                    i,
                    batiment.getAscenseurs().size() * (width / 8 + 5) + 5,
                    (height - 50) - (height - 50) * i / batiment.getNombreEtages() + 25,
                    width
            ).dessiner(g);

        // dessiner les ascenseurs
        int j = 0;
        for (Ascenseur a : batiment.getAscenseurs()) {
            new DessinAscenseur(
                    j * (width / 8 + 5) + 5,
                    (height - 25) - ((height - 50) / batiment.getNombreEtages() * a.getEtageCourant()),
                    width / 8,
                    (height - 50) / batiment.getNombreEtages() - 10,
                    a
            ).dessiner(g);
            j++;
        }

        // dessiner les personnes qui attendent
        List<Integer> abscissesPersonnes = new ArrayList<>();
        for (int i = 0; i < batiment.getNombreEtages(); i++)
            abscissesPersonnes.add(batiment.getAscenseurs().size() * width / 8 + 40);

        synchronized (batiment.getPersonnes()) {
            for (Personne p : batiment.getPersonnes()) {
                if (p.getAscenseur() == null) {
                    new DessinPersonne(
                            abscissesPersonnes.get(p.getNumeroEtageCourant()),
                            (height - 50) - (height - 50) * p.getNumeroEtageCourant() / batiment.getNombreEtages() + 25
                    ).dessiner(g);
                    abscissesPersonnes.set(p.getNumeroEtageCourant(), abscissesPersonnes.get(p.getNumeroEtageCourant()) + 15);
                }
            }
        }

        // afficher l'heure
        String heure = horloge.getHeure() + "";
        char[] data = new char[heure.length() + 25];
        data[0] = 't';
        data[1] = ' ';
        data[2] = '=';
        data[3] = ' ';

        for (int i = 0; i < heure.length(); i++)
            data[i + 4] = heure.charAt(i);

        String tmp = " ticks (1 tick = 10s)";
        for (int i = 0; i < tmp.length(); i++)
            data[i + heure.length() + 4] = tmp.charAt(i);

        g.drawChars(data, 0, data.length, width - 200, 45);
    }
}
