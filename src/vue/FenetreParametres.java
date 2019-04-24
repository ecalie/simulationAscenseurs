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
    private JLabel nbArrivee;
    private JLabel tempsTravail;
    private JLabel dureeSimulation;
    private JComboBox<StrategieService> strategieService;
    private JComboBox<StrategieRalenti> strategieRalenti;

    public FenetreParametres() {
        super("Choix des paramètres");
        this.setLayout(new FlowLayout());

        this.ajouterNbAscenseurs();
        this.ajouterNbEtages();
        this.ajouterStrategieService();
        this.ajouterStrategieRalenti();
        this.ajouterLoiArrivee();
        this.ajouterLoiTempsTravail();
        this.ajouterDureeSimulation();

        this.ajouterBtnGo();

        this.getContentPane().setBackground(Color.LIGHT_GRAY);

        this.setSize(new Dimension(270, 300));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public int getNbAscenseurs() {
        return (int) Double.parseDouble(this.nbAscenseurs.getText());
    }

    public int getNbEtages() {
        return (int) Double.parseDouble(this.nbEtages.getText());
    }

    public StrategieRalenti getStrategieRalenti() {
        return (StrategieRalenti) this.strategieRalenti.getSelectedItem();
    }

    public StrategieService getStrtategieService() {
        return (StrategieService) this.strategieService.getSelectedItem();
    }

    public double getLoiArrivee() {
        return Double.parseDouble(this.nbArrivee.getText());
    }

    public int getTempsTravail() {
        return (int) Double.parseDouble(this.tempsTravail.getText());
    }

    public int getDureeSimulation() {
        return (int) Double.parseDouble(this.dureeSimulation.getText());
    }

    private void ajouterNbAscenseurs() {
        JLabel labelAscenseurs = new JLabel("Nombre d'ascenseurs      ");
        nbAscenseurs = new JLabel("1.0");
        nbAscenseurs.setForeground(Color.DARK_GRAY);
        JLabel btnAscenseurPlus = new JLabel(" + ");
        JLabel btnAscenseurMoins = new JLabel(" - ");
        btnAscenseurMoins.addMouseListener(new ClickPlusMoins(nbAscenseurs, 1, 1));
        btnAscenseurPlus.addMouseListener(new ClickPlusMoins(nbAscenseurs, 1));

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
        JLabel labelEtages = new JLabel("Nombre d'étages             ");
        nbEtages = new JLabel("4.0");
        nbEtages.setForeground(Color.DARK_GRAY);
        JLabel btnEtagePlus = new JLabel(" + ");
        JLabel btnEtageMoins = new JLabel(" - ");
        btnEtageMoins.addMouseListener(new ClickPlusMoins(nbEtages, 2, 1));
        btnEtagePlus.addMouseListener(new ClickPlusMoins(nbEtages, 1));

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
        JLabel labelService = new JLabel("Stratégie de service         ");
        strategieService = new JComboBox<>(StrategieService.values());
        strategieService.setBackground(Color.GRAY);
        strategieService.setRenderer(new ComboRenderer());
        strategieService.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        this.add(labelService);
        this.add(strategieService);
    }

    private void ajouterStrategieRalenti() {
        JLabel labelRalenti = new JLabel("Stratégie idle           ");
        strategieRalenti = new JComboBox<>(StrategieRalenti.values());
        strategieRalenti.setBackground(Color.GRAY);
        strategieRalenti.setRenderer(new ComboRenderer());
        strategieRalenti.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));


        this.add(labelRalenti);
        this.add(strategieRalenti);
    }

    private void ajouterLoiArrivee() {
        JLabel labelArrivee = new JLabel("Nombre d'arrivées (/min)");
        nbArrivee = new JLabel("0.5");
        nbArrivee.setForeground(Color.DARK_GRAY);
        JLabel btnArriveePlus = new JLabel(" + ");
        JLabel btnArriveeMoins = new JLabel(" - ");
        btnArriveeMoins.addMouseListener(new ClickPlusMoins(nbArrivee, 0.5, 0.5));
        btnArriveePlus.addMouseListener(new ClickPlusMoins(nbArrivee, 0.5));

        JPanel panelBoutonsArrivee = new JPanel(new BorderLayout());
        panelBoutonsArrivee.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelBoutonsArrivee.setBackground(Color.GRAY);
        panelBoutonsArrivee.add(btnArriveeMoins, BorderLayout.WEST);
        panelBoutonsArrivee.add(btnArriveePlus, BorderLayout.EAST);
        JPanel panelCentre = new JPanel();
        panelCentre.add(nbArrivee);
        panelCentre.setBackground(Color.LIGHT_GRAY);
        panelBoutonsArrivee.add(panelCentre, BorderLayout.CENTER);

        this.add(labelArrivee);
        this.add(panelBoutonsArrivee);
    }

    private void ajouterLoiTempsTravail() {
        JLabel labelTempsTravail = new JLabel("Temps de travail (min)  ");
        tempsTravail = new JLabel("30.0");
        tempsTravail.setForeground(Color.DARK_GRAY);
        JLabel btnTempsTravailPlus = new JLabel(" + ");
        JLabel btnTempsTravailMoins = new JLabel(" - ");
        btnTempsTravailMoins.addMouseListener(new ClickPlusMoins(tempsTravail, 10, 5));
        btnTempsTravailPlus.addMouseListener(new ClickPlusMoins(tempsTravail, 5));

        JPanel panelBoutonsTempsTravail = new JPanel(new BorderLayout());
        panelBoutonsTempsTravail.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelBoutonsTempsTravail.setBackground(Color.GRAY);
        panelBoutonsTempsTravail.add(btnTempsTravailMoins, BorderLayout.WEST);
        panelBoutonsTempsTravail.add(btnTempsTravailPlus, BorderLayout.EAST);
        JPanel panelCentre = new JPanel();
        panelCentre.add(tempsTravail);
        panelCentre.setBackground(Color.LIGHT_GRAY);
        panelBoutonsTempsTravail.add(panelCentre, BorderLayout.CENTER);

        this.add(labelTempsTravail);
        this.add(panelBoutonsTempsTravail);
    }

    private void ajouterDureeSimulation() {
        JLabel labelDuree = new JLabel("Durée simulation (min) ");
        dureeSimulation = new JLabel("20.0");
        dureeSimulation.setForeground(Color.DARK_GRAY);
        JLabel btnDureePlus = new JLabel(" + ");
        JLabel btnDureeMoins = new JLabel(" - ");
        btnDureeMoins.addMouseListener(new ClickPlusMoins(dureeSimulation, 10, 5));
        btnDureePlus.addMouseListener(new ClickPlusMoins(dureeSimulation, 5));

        JPanel panelBoutonsDuree = new JPanel(new BorderLayout());
        panelBoutonsDuree.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelBoutonsDuree.setBackground(Color.GRAY);
        panelBoutonsDuree.add(btnDureeMoins, BorderLayout.WEST);
        panelBoutonsDuree.add(btnDureePlus, BorderLayout.EAST);
        JPanel panelCentre = new JPanel();
        panelCentre.add(dureeSimulation);
        panelCentre.setBackground(Color.LIGHT_GRAY);
        panelBoutonsDuree.add(panelCentre, BorderLayout.CENTER);

        this.add(labelDuree);
        this.add(panelBoutonsDuree);
    }

    private void ajouterBtnGo() {
        JButton btnGo = new JButton("Go");
        btnGo.addActionListener(new ActionDemarrer(this));
        btnGo.setBackground(Color.DARK_GRAY);
        btnGo.setForeground(Color.LIGHT_GRAY);

        this.add(btnGo);
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