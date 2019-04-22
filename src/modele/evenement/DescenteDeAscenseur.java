package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import modele.Personne;

import java.util.ArrayList;
import java.util.List;

public class DescenteDeAscenseur extends Evenement {
    private Personne personne;
    private Ascenseur ascenseur;
    private Batiment batiment;

    public DescenteDeAscenseur(int temps, Personne personne, Ascenseur ascenseur, Batiment batimen) {
        super(temps);
        this.personne = personne;
        this.ascenseur = ascenseur;
        this.batiment = batimen;
    }

    @Override
    public void executer() {
        this.personne.setAscenseur(null);
        this.ascenseur.getPersonnes().remove(personne);

        if (ascenseur.getPersonnes().isEmpty()) {
            ascenseur.choisirProchaineDemande(batiment.getDemandes(), batiment);
        }

        System.out.println("descente en " + temps + " de " + personne.getNumeroEtageCible());
    }

    @Override
    public List<Evenement> genererProchainsEvenements() {
        // ajouter l'arrivée de la personne après son temps de travail
        List<Evenement> evenemnts = new ArrayList<>();
        if (personne.getNumeroEtageCible() != 1) {
            // mettre à jour les étages de la personne
            personne.setNumeroEtageCourant(personne.getNumeroEtageCible());
            personne.setNumeroEtageCible(1);
            // ajouter un évènement pour son retour
            evenemnts.add(new ArriveeClient(temps + personne.calculerTempsTravail(), batiment, personne));
        }
        return evenemnts;

    }
}