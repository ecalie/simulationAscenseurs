package controleur;

import modele.Batiment;
import modele.Constante;
import modele.GestionnaireEvenement;
import modele.evenement.ArriveeClient;
import vue.FenetreParametres;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionDemarrer implements ActionListener {

    private FenetreParametres fenetreParametres;


    public ActionDemarrer(FenetreParametres fenetreParametres) {
        this.fenetreParametres = fenetreParametres;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int nbEtages = fenetreParametres.getNbEtages();
        int nbAscenseurs = fenetreParametres.getNbAscenseurs();
        Constante.strategieService = fenetreParametres.getStrtategieService();
        Constante.strategieRalenti = fenetreParametres.getStrategieRalenti();
        Constante.lambda = fenetreParametres.getLoiArrivee() / 6;
        Constante.tempsTravail = fenetreParametres.getTempsTravail() * 6;

        fenetreParametres.setVisible(false);

        Batiment batiment = new Batiment(nbAscenseurs, nbEtages);

        // générer le premier événement
        GestionnaireEvenement ge = batiment.getGestionnaireEvenement();
        ge.ajouterEvenement(new ArriveeClient(0, batiment));
        ge.start();
    }
}
