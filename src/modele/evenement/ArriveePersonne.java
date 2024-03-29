package modele.evenement;

import modele.Batiment;
import modele.Constante;
import modele.Personne;

import java.util.ArrayList;
import java.util.List;

public class ArriveePersonne extends Evenement {
    private Personne personne;

    public ArriveePersonne(int temps, Batiment batiment) {
        super(temps, batiment, 5);
        this.personne = new Personne(batiment.getNombreEtages());
    }

    public ArriveePersonne(int temps, Batiment batiment, Personne personne) {
        super(temps, batiment,  5);
        this.batiment = batiment;
        this.personne = personne;
    }

    public Personne getPersonne() {
        return personne;
    }

    @Override
    public boolean precondition() {
        return true;
    }

    @Override
    public boolean postcondition() {
        return true;
    }

    @Override
    public List<Evenement> executer() {
        synchronized (batiment.getPersonnes()) {
            // ajouter la personne dans le batiment
            batiment.ajouterPersonne(personne);
        }

        // appeler un ascenseur
        batiment.demanderAscenseur(personne);

        // initialiser l'heure d'arrivée
        personne.setHeureArrivee(temps);

        // générer la prochaine arrivée
        return genererProchainsEvenements();
    }

    /**
     * Générer l'arrivée du prochain client.
     *
     * @return Les événements d'arrivée des prochains clients
     */
    private List<Evenement> genererProchainsEvenements() {
        // trouver le temps de la prochaine arrivée
        // selon la loi de probabilité
        int nombreArrivee = nombreArriveesTempsSuivant();
        int tempsProchaineArrivee = temps + 1;
        while (nombreArrivee <= 0) {
            nombreArrivee = nombreArriveesTempsSuivant();
            tempsProchaineArrivee++;
        }

        // créer les événements
        List<Evenement> prochainesArrivees = new ArrayList<>();
        for (int i = 0; i < nombreArrivee; i++) {
            prochainesArrivees.add(new ArriveePersonne(tempsProchaineArrivee, batiment));
        }

        return prochainesArrivees;
    }

    /**
     * Caculer le nombre d'arrivées au prochain tick.
     *
     * @return Le nombre d'arrivées au prochain tick (potentiellement 0)
     */
    public int nombreArriveesTempsSuivant() {
        double u = Math.random();
        int k = 0;
        double p = probabilitePoisson(k);
        while (u <= p) {
            p = probabilitePoisson(++k);
        }
        return k - 1;
    }

    /**
     * Calculer la probabilité que k personnes arrivent.
     *
     * @param k Nombre de personne qui arrivent
     * @return La probabilité que k personnes arrivent
     */
    public double probabilitePoisson(int k) {
        return Math.pow(Constante.lambda, k) * Math.exp(-Constante.lambda) / factorielle(k);
    }

    /**
     * Calculer la factorielle d'un nombre.
     *
     * @param k Le paramètre de la fonction factorielle
     * @return Factorielle k = k*(k-1)*...*2*1
     */
    public double factorielle(int k) {
        if (k == 0)
            return 1;
        else
            return k * factorielle(k - 1);
    }

    @Override
    public String decrire() {
        return "une personne arrive à l'étage " + personne.getNumeroEtageCourant() + " et demande un ascenseur";
    }
}
