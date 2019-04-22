import modele.Batiment;
import modele.GestionnaireEvenement;
import modele.evenement.ArriveeClient;

public class Main {
    public static void main(String[] args) {
        int nbEtages = 5;
        int nbAscenseurs = 2;
        Batiment batiment = new Batiment(nbAscenseurs, nbEtages);

        GestionnaireEvenement ge = new GestionnaireEvenement(batiment);
        batiment.setGestionnaireEvenement(ge);

        // générer le premier événement
        ge.ajouterEvenement(new ArriveeClient(0, batiment));
        ge.demarrer();
    }
}
