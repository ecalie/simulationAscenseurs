package controleur;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickPlusMoins implements MouseListener {

    private JLabel label;
    private boolean plus;
    private double valeurMin;
    private double increment;

    public ClickPlusMoins(JLabel label, double valeurMin, double increment) {
        this.label = label;
        this.plus = false;
        this.valeurMin = valeurMin;
        this.increment = increment;
    }

    public ClickPlusMoins(JLabel label, double increment) {
        this.label = label;
        this.plus = true;
        this.valeurMin = 0;
        this.increment = increment;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // modifier la valeur
        double ancienneValeur = Double.parseDouble(this.label.getText());
        double nouvelleValeur = plus ?
                (ancienneValeur + increment) :
                (ancienneValeur == valeurMin)?valeurMin:(ancienneValeur - increment);
        this.label.setText(nouvelleValeur + "");
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }
}
