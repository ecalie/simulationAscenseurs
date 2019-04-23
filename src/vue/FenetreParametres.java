package vue;

import controleur.ActionDemarrer;
import controleur.ClickPlusMoins;
import modele.StrategieRalenti;
import modele.StrategieService;

import javax.swing.*;
import java.awt.*;

public class FenetreParametres extends JFrame {

    private JLabel nbAscenseurs;
    private JLabel nbEtages;
    private JComboBox<StrategieService> strategieService;
    private JComboBox<StrategieRalenti> strategieRalenti;

    public FenetreParametres() {
        super("Choix des paramètres");
        this.setLayout(new FlowLayout());

        this.ajouterNbAscenseurs();
        this.ajouterNbEtages();
        this.ajouterStrategieService();
        this.ajouterStrategieRalenti();
        this.ajouterBtnGo();

        this.getContentPane().setBackground(Color.LIGHT_GRAY);

        this.setSize(new Dimension(250, 200));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void ajouterNbAscenseurs() {
        JLabel labelAscenseurs = new JLabel("Nombre d'ascenseurs ");
        nbAscenseurs = new JLabel("1");
        nbAscenseurs.setForeground(Color.DARK_GRAY);
        nbAscenseurs.setSize(30, 20);
        JLabel btnAscenseurPlus = new JLabel(" + ");
        JLabel btnAscenseurMoins = new JLabel(" - ");
        btnAscenseurMoins.addMouseListener(new ClickPlusMoins(nbAscenseurs, false, 1));
        btnAscenseurPlus.addMouseListener(new ClickPlusMoins(nbAscenseurs, true, 1));

        JPanel panelBoutonsAscenseur = new JPanel(new BorderLayout());
        panelBoutonsAscenseur.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelBoutonsAscenseur.setBackground(Color.GRAY);
        panelBoutonsAscenseur.add(btnAscenseurMoins, BorderLayout.WEST);
        panelBoutonsAscenseur.add(btnAscenseurPlus, BorderLayout.EAST);
        JPanel panelCentre = new JPanel();
        panelCentre.add(nbAscenseurs);
        panelCentre.setBackground(Color.LIGHT_GRAY);
        panelBoutonsAscenseur.add(panelCentre, BorderLayout.CENTER);

        this.add(labelAscenseurs);
        this.add(panelBoutonsAscenseur);
    }

    private void ajouterNbEtages() {
        JLabel labelEtages = new JLabel("Nombre d'étages        ");
        nbEtages = new JLabel("4");
        nbEtages.setForeground(Color.DARK_GRAY);
        nbEtages.setSize(30, 20);
        JLabel btnEtagePlus = new JLabel(" + ");
        JLabel btnEtageMoins = new JLabel(" - ");
        btnEtageMoins.addMouseListener(new ClickPlusMoins(nbEtages, false, 2));
        btnEtagePlus.addMouseListener(new ClickPlusMoins(nbEtages, true, 2));

        JPanel panelBoutonsEtages = new JPanel(new BorderLayout());
        panelBoutonsEtages.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelBoutonsEtages.setBackground(Color.GRAY);
        panelBoutonsEtages.add(btnEtageMoins, BorderLayout.WEST);
        panelBoutonsEtages.add(btnEtagePlus, BorderLayout.EAST);
        JPanel panelCentre = new JPanel();
        panelCentre.add(nbEtages);
        panelCentre.setBackground(Color.LIGHT_GRAY);
        panelBoutonsEtages.add(panelCentre, BorderLayout.CENTER);

        this.add(labelEtages);
        this.add(panelBoutonsEtages);
    }

    private void ajouterStrategieService() {
        JLabel labelService = new JLabel("Stratégie de service ");
        strategieService = new JComboBox<>(StrategieService.values());
        strategieService.setBackground(Color.GRAY);
        strategieService.setRenderer(new ComboRenderer());
        strategieService.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        this.add(labelService);
        this.add(strategieService);
    }

    private void ajouterStrategieRalenti() {
        JLabel labelRalenti = new JLabel("Stratégie idle   ");
        strategieRalenti = new JComboBox<>(StrategieRalenti.values());
        strategieRalenti.setBackground(Color.GRAY);
        strategieRalenti.setRenderer(new ComboRenderer());
        strategieRalenti.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));


        this.add(labelRalenti);
        this.add(strategieRalenti);
    }

    private void ajouterBtnGo() {
        JButton btnGo = new JButton("Go");
        btnGo.addActionListener(new ActionDemarrer(this));
        btnGo.setBackground(Color.DARK_GRAY);
        btnGo.setForeground(Color.LIGHT_GRAY);

        this.add(btnGo);
    }

    public int getNbAscenseurs() {
        return Integer.parseInt(this.nbAscenseurs.getText());
    }

    public int getNbEtages() {
        return Integer.parseInt(this.nbEtages.getText());
    }

    public StrategieRalenti getStrategieRalenti() {
        return (StrategieRalenti) this.strategieRalenti.getSelectedItem();
    }

    public StrategieService getStrtategieService() {
        return (StrategieService) this.strategieService.getSelectedItem();
    }
}

class ComboRenderer extends DefaultListCellRenderer {

    @Override
    public Color getBackground() {
        return Color.lightGray;
    }

    @Override
    public Component getListCellRendererComponent(JList pList, Object pValue, int pIndex, boolean pSelected, boolean pHasFocus) {

        Component c = super.getListCellRendererComponent(
                pList, pValue, pIndex, pSelected, pHasFocus);
        pList.setSelectionBackground(Color.LIGHT_GRAY);
        return c;
    }
}