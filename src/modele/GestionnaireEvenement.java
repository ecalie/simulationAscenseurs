package modele;

import modele.evenement.ArriveeClient;
import modele.evenement.Evenement;
import vue.DessinBatiment;
import vue.FenetreLogging;
import vue.GraphiqueTempsAttente;
import vue.GraphiqueTempsService;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class GestionnaireEvenement extends Thread {
    private Horloge horloge;
    private Queue<Evenement> evenements;
    private DessinBatiment dessinBatiment;
    private FenetreLogging fenetreLogging;
    private int dureeSimulation;

    public GestionnaireEvenement(Batiment batiment, int dureeSimulation) {
        this.horloge = batiment.getHorloge();
        this.dessinBatiment = new DessinBatiment(batiment, horloge);
        this.dureeSimulation = dureeSimulation;
        this.evenements = new PriorityQueue<>(Comparator.comparingInt(Evenement::getTemps));
        this.fenetreLogging = new FenetreLogging();
    }

    public Horloge getHorloge() {
        return horloge;
    }

    public void ajouterEvenement(Evenement e) {
        this.evenements.add(e);
    }

    @Override
    public void run() {
        while (horloge.getHeure() <= dureeSimulation) {
            Evenement e = evenements.poll();
            horloge.avancer(e.getTemps());
            if (!(e instanceof ArriveeClient) || !contientArrivee())
                evenements.addAll(e.executer(fenetreLogging));
            else
                e.executer(fenetreLogging);
            dessinBatiment.repaint();
        }

        new GraphiqueTempsAttente();
        new GraphiqueTempsService();
    }

    private boolean contientArrivee() {
        for (Evenement e : evenements)
            if (e instanceof ArriveeClient && ((ArriveeClient) e).getPersonne().getNumeroEtageCourant() == 1)
                return true;
        return false;
    }

}
