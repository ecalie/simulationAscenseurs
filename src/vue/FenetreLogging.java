package vue;

import modele.evenement.Evenement;

import javax.swing.*;
import java.awt.*;

public class FenetreLogging extends JFrame {

    private JTextArea area;
    private JScrollPane scroll;

    public FenetreLogging() {
        super("Evenements");
        this.area = new JTextArea();
        area.setForeground(Color.LIGHT_GRAY);
        area.setBackground(Color.DARK_GRAY);
        this.scroll = new JScrollPane(area);
        this.add(scroll);

        this.setSize(400,10);
        this.setExtendedState(MAXIMIZED_VERT);
        this.setLocation(700,0);
        this.setVisible(true);
    }

    public void ajouterEvenement(Evenement e) {
        this.area.setText(this.area.getText() + "\n" + e.toString());
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }
}
