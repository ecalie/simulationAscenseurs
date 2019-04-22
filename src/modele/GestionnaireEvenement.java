package modele;

import modele.evenement.Evenement;
import vue.DessinBatiment;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class GestionnaireEvenement {
    private Horloge horloge;
    private Queue<Evenement> evenements;
    private DessinBatiment dessinBatiment;

    public GestionnaireEvenement(Batiment batiment) {
        this.horloge = batiment.getHorloge();
        this.dessinBatiment = new DessinBatiment(batiment, horloge);
        this.evenements = new PriorityQueue<>(Comparator.comparingInt(Evenement::getTemps));

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
            e.executer();
            dessinBatiment.repaint();
            evenements.addAll(e.genererProchainsEvenements());
        }
    }

}
