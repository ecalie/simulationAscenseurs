import modele.Ascenseur;
import modele.Batiment;
import modele.GestionnaireEvenement;
import modele.evenement.ArriveeClient;

public class Main {
    public static void main(String[] args) {
        int nbEtages = 5;
        int nbAscenseurs = 1;
        Batiment batiment = new Batiment(nbAscenseurs, nbEtages);

        // générer le premier événement
        GestionnaireEvenement ge = batiment.getGestionnaireEvenement();
        ge.ajouterEvenement(new ArriveeClient(0, batiment));
        ge.demarrer();

    }
}
