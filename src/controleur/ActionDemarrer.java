package controleur;

import modele.Batiment;
import modele.Constante;
import modele.GestionnaireEvenement;
import modele.evenement.ArriveePersonne;
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
        // démarer la simulation
        //       - récupérer les paramètres
        int nbEtages = fenetreParametres.getNbEtages();
        int nbAscenseurs = fenetreParametres.getNbAscenseurs();
        Constante.strategieService = fenetreParametres.getStrtategieService();
        Constante.strategieRalenti = fenetreParametres.getStrategieRalenti();
        Constante.lambda = fenetreParametres.getLoiArrivee() / 6;
        Constante.tempsTravail = fenetreParametres.getTempsTravail() * 6;
        int dureeSimualtion = fenetreParametres.getDureeSimulation() * 6;

        //      - masquer la fenetre des paramètres
        fenetreParametres.setVisible(false);

        //      - créer le batiment
        Batiment batiment = new Batiment(nbAscenseurs, nbEtages, dureeSimualtion);

        //      - générer le premier événement
        GestionnaireEvenement ge = batiment.getGestionnaireEvenement();
        ge.ajouterEvenement(new ArriveePersonne(0, batiment));

        //      - démarer le gestionaire d'événements
        ge.start();
    }
}
