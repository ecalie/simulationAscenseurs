package modele;

import java.util.ArrayList;
import java.util.List;

public class Statistique {
    private static Statistique instance;

    private List<Integer> tempsAttentes;

    private Statistique() {
        this.tempsAttentes = new ArrayList<>();
    }

    public static Statistique getInstance() {
        if (instance == null)
            instance = new Statistique();
        return instance;
    }

    public List<Integer> getTempsAttentes() {
        return tempsAttentes;
    }

    public void ajouterTempsAttente(int tempsAttente) {
        this.tempsAttentes.add(tempsAttente);
    }

    public double calculerTempsAttenteMoyen() {
        int nbClients = this.tempsAttentes.size();
        double tempsAttenteTotal = 0.0;

        for (int temps : tempsAttentes)
            tempsAttenteTotal += temps;

        return tempsAttenteTotal / nbClients;
    }
}
