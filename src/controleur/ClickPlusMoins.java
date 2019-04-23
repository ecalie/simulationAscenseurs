package controleur;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickPlusMoins implements MouseListener {

    private JLabel label;
    private boolean plus;
    private int valeurMin;

    public ClickPlusMoins(JLabel label, boolean plus, int valeurMin) {
        this.label = label;
        this.plus = plus;
        this.valeurMin = valeurMin;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int ancienneValeur = Integer.parseInt(this.label.getText());
        int nouvelleValeur = plus ? (ancienneValeur + 1) : (ancienneValeur == valeurMin)?valeurMin:(ancienneValeur - 1);
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
