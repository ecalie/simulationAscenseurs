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

public class GraphiqueTempsAttente extends JFrame {

    public GraphiqueTempsAttente() {
        super("Temps d'attente moyen");
        this.setLayout(new BorderLayout());

        // Afficher le temps d'attente moyen
        double tempsMoyen = Statistique.getInstance().calculerTempsAttenteMoyen();
        double tempsMoyenTronque = ((int) (tempsMoyen * 100)) / 100.0;
        JLabel labelTempsMoyen = new JLabel(tempsMoyenTronque + " ticks");
        labelTempsMoyen.setFont((new Font("Arial", Font.BOLD, 25)));
        this.add(labelTempsMoyen, BorderLayout.NORTH);

        // Afficher tous les teps d'attente dans un graphique
        ChartPanel chartPanel = new ChartPanel(ChartFactory.createBarChart(
                "Temps attente moyen",
                "temps",
                "nombre de personnes",
                createDataset(),
                PlotOrientation.VERTICAL,
                false, false, false));
        this.add(chartPanel, BorderLayout.SOUTH);

        // Afficher la fenere
        this.setLocation(0, 200);
        this.pack();
        this.setVisible(true);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Integer> tempsAttente = new ArrayList<>();

        for (int i = 0; i < 11; i++)
            tempsAttente.add(0);

        for (int i : Statistique.getInstance().getTempsAttentes())
            tempsAttente.set(i, tempsAttente.get(i) + 1);

        for (int i = 0; i < 11; i++)
            dataset.addValue(tempsAttente.get(i), "", i + "");

        return dataset;
    }
}