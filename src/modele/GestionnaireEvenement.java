package modele;

import modele.evenement.ArriveeClient;
import modele.evenement.Evenement;
import vue.DessinBatiment;
import vue.FenetreLogging;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class GestionnaireEvenement {
    private Horloge horloge;
    private Queue<Evenement> evenements;
    private DessinBatiment dessinBatiment;
    private FenetreLogging fenetreLogging;

    public GestionnaireEvenement(Batiment batiment) {
        this.horloge = batiment.getHorloge();
        this.dessinBatiment = new DessinBatiment(batiment, horloge);
        this.evenements = new PriorityQueue<>(Comparator.comparingInt(Evenement::getTemps));
        this.fenetreLogging = new FenetreLogging();
    }

    public Horloge getHorloge() {
        return horloge;
    }

    public void ajouterEvenement(Evenement e) {
        this.evenements.add(e);
    }

    public void demarrer() {
        while (evenements.size() > 0) {
            Evenement e = evenements.poll();
            horloge.avancer(e.getTemps());
            if (!(e instanceof ArriveeClient) || !contientArrivee())
                evenements.addAll(e.executer(fenetreLogging));
            else
                e.executer(fenetreLogging);
            dessinBatiment.repaint();
        }
    }

    private boolean contientArrivee() {
        for (Evenement e : evenements)
            if (e instanceof ArriveeClient && ((ArriveeClient) e).getPersonne().getNumeroEtageCourant() == 1)
                return true;
        return false;
    }

}
