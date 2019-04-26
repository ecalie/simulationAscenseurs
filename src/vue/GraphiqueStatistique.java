package vue;

import modele.Statistique;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphiqueStatistique extends JFrame {

    public GraphiqueStatistique() {
        super("Performance");
        this.setLayout(new BorderLayout());

        JPanel panelNord = new JPanel(new GridLayout(2,1));

        // Afficher le temps d'attente moyen
        double attenteMoyen = Statistique.getInstance().calculerTempsAttenteMoyen();
        double attenteTronque = ((int) (attenteMoyen * 100)) / 100.0;
        JLabel labelAttenteTempsMoyen = new JLabel(" Temps d'attente moyen : " + attenteTronque + " ticks");
        labelAttenteTempsMoyen.setFont((new Font("Arial", Font.BOLD, 25)));
        panelNord.add(labelAttenteTempsMoyen);

        // Afficher le temps de service moyen
        double serviceMoyen = Statistique.getInstance().calculerTempsServiceMoyen();
        double serviceTronque = ((int) (serviceMoyen * 100)) / 100.0;
        JLabel labelServiceMoyen = new JLabel("Temps de service moyen : " + serviceTronque + " ticks");
        labelServiceMoyen.setFont((new Font("Arial", Font.BOLD, 25)));
        panelNord.add(labelServiceMoyen);

        this.add(panelNord, BorderLayout.NORTH);

        // Afficher tous les teps d'attente dans un graphique
        ChartPanel chartPanel = new ChartPanel(ChartFactory.createBarChart(
                "Performance",
                "temps",
                "nombre de personnes",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false));
        this.add(chartPanel, BorderLayout.CENTER);

        // Afficher la fenere
        this.setLocation(0, 200);
        this.pack();
        this.setVisible(true);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Integer> tempsAttente = new ArrayList<>();
        List<Integer> tempsService = new ArrayList<>();

        // chercher la valeur maximale
        int max = 0;
        for (int i : Statistique.getInstance().getTempsAttentes())
            if (i > max)
                max = i;
        for (int i : Statistique.getInstance().getTempsService())
            if (i > max)
                max = i;

        // initialiser les listes
        for (int i = 0; i <= max; i++)
            tempsAttente.add(0);
        for (int i = 0; i <= max; i++)
            tempsService.add(0);

        // remplir les listes avec les données
        for (int i : Statistique.getInstance().getTempsAttentes())
            tempsAttente.set(i, tempsAttente.get(i) + 1);
        for (int i : Statistique.getInstance().getTempsService())
            tempsService.set(i, tempsService.get(i) + 1);

        // ajouter les données au dataset
        for (int i = 0; i <= max; i++) {
            dataset.addValue(tempsAttente.get(i), "attente", i + "");
            dataset.addValue(tempsService.get(i), "service", i + "");
        }

        return dataset;
    }
}