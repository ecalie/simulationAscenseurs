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

public class GraphiqueTempsService extends JFrame {

    public GraphiqueTempsService() {
        super("Temps de sevice moyen");
        this.setLayout(new BorderLayout());
        double tempsMoyen = Statistique.getInstance().calculerTempsServiceMoyen();
        double tempsMoyenTronque = ((int) (tempsMoyen * 100)) / 100.0;
        JLabel labelTempsMoyen = new JLabel(tempsMoyenTronque + " ticks");
        labelTempsMoyen.setFont((new Font("Arial", Font.BOLD, 25)));
        this.add(labelTempsMoyen, BorderLayout.NORTH);

        ChartPanel chartPanel = new ChartPanel(ChartFactory.createBarChart(
                "Temps service moyen",
                "temps",
                "nombre de personnes",
                createDataset(),
                PlotOrientation.VERTICAL,
                false, false, false));
        this.add(chartPanel, BorderLayout.SOUTH);

        this.setLocation(700, 200);
        this.pack();
        this.setVisible(true);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Integer> tempsService = new ArrayList<>();

        for (int i = 0; i < 11; i++)
            tempsService.add(0);

        for (int i : Statistique.getInstance().getTempsService())
            tempsService.set(i, tempsService.get(i) + 1);

        for (int i = 0; i < 11; i++)
            dataset.addValue(tempsService.get(i), "", i + "");

        return dataset;
    }
}