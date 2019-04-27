package modele;

import modele.evenement.ArriveePersonne;
import modele.evenement.Evenement;
import vue.DessinBatiment;
import vue.FenetreLogging;
import vue.GraphiqueStatistique;

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
        this.evenements = new PriorityQueue<>((t0, t1) -> {
            if (t0.getTemps() != t1.getTemps())
                return t0.getTemps() - t1.getTemps();
            else
                return t0.getPriorite() - t1.getPriorite();
        });
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
        // tant que la simulation n'est pas terminée
        while (horloge.getHeure() <= dureeSimulation) {
            // récupérer le prochain événement
            Evenement e = evenements.poll();

            // avancer l'horloge jusqu'à cet événement
            horloge.avancer(e.getTemps());

            // l'exécuter et ajouter les prochains événements à la liste
            // sauf dans le cas particulier où plusieurs personnes arrivent en même temps
            // dans ce cas, on n'ajoute qu'une seule fois les prochaines arrivées
            if (!(e instanceof ArriveePersonne) || !contientArrivee())
                evenements.addAll(e.executer(fenetreLogging));
            else
                e.executer(fenetreLogging);

            // redessiner le batiment
            dessinBatiment.repaint();
        }

        // afficher les statistiques à la fin de la simulation
        new GraphiqueStatistique();
    }

    /**
     * Regarder s'il y a déjà une arrivée (pas un retour) dans la liste.
     *
     * @return Vrai s'il y a déjà une arrivée dans la liste
     */
    private boolean contientArrivee() {
        for (Evenement e : evenements)
            if (e instanceof ArriveePersonne && ((ArriveePersonne) e).getPersonne().getNumeroEtageCourant() == 1)
                return true;
        return false;
    }

}
