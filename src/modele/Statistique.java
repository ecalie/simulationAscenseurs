package modele;

import java.util.ArrayList;
import java.util.List;

public class Statistique {
    private static Statistique instance;

    private List<Integer> tempsAttentes;
    private List<Integer> tempsService;

    private Statistique() {
        this.tempsAttentes = new ArrayList<>();
        this.tempsService = new ArrayList<>();
    }

    public static Statistique getInstance() {
        if (instance == null)
            instance = new Statistique();
        return instance;
    }

    public List<Integer> getTempsAttentes() {
        return tempsAttentes;
    }

    public List<Integer> getTempsService() {
        return tempsService;
    }

    public void ajouterTempsAttente(int tempsAttente) {
        this.tempsAttentes.add(tempsAttente);
    }

    public void ajouterTempsService(int tempsService) {
        this.tempsService.add(tempsService);
    }

    public double calculerTempsAttenteMoyen() {
        int nbClients = this.tempsAttentes.size();
        double tempsAttenteTotal = 0.0;

        for (int temps : tempsAttentes)
            tempsAttenteTotal += temps;

        return tempsAttenteTotal / nbClients;
    }

    public double calculerTempsServiceMoyen() {
        int nbClients = this.tempsService.size();
        double tempsServivceTotal = 0.0;

        for (int temps : tempsService)
            tempsServivceTotal += temps;

        return tempsServivceTotal / nbClients;
    }
}
