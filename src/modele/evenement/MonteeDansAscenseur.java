package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import modele.Personne;
import modele.Statistique;

import java.util.ArrayList;
import java.util.List;

public class MonteeDansAscenseur extends Evenement {
    private Ascenseur ascenseur;
    private List<Personne> personnes;
    private int nbMontees;

    public MonteeDansAscenseur(int temps, Ascenseur ascenseur, List<Personne> personnes, Batiment batiment) {
        super(temps, batiment,  1);
        this.ascenseur = ascenseur;
        this.personnes = personnes;
        nbMontees = 0;
    }

    @Override
    public boolean precondition() {
        return ascenseur.getSens() == 0;
    }

    @Override
    public boolean postcondition() {
        return nbMontees > 0;
    }

    @Override
    public List<Evenement> executer() {
        boolean nouvellePersonne = false;
        nbMontees = 0;
        for (Personne p : personnes)
            if (ascenseur.getSens() == 0 && p.getAscenseur() == null && p.getNumeroEtageCourant() == ascenseur.getEtageCourant()) {
                // calculer le temps d'attente et l'enregistrer
                Statistique.getInstance().ajouterTempsAttente(temps - p.getHeureArrivee());

                // faire monter la personne dans l'ascenseur
                p.setAscenseur(ascenseur);
                this.ascenseur.getPersonnes().add(p);
                nbMontees++;

                // enregistrer qu'au moins une personne est montée
                nouvellePersonne = true;
            }

        // si au moins une personne est montée, recalculer la prochaine destination
        if (nouvellePersonne) {
            ascenseur.choisirProchaineDemande(batiment);
        }

        // aucun événements à générer
        return new ArrayList<>();
    }

    @Override
    public String decrire() {
        return nbMontees + (nbMontees == 1 ? " personne monte" : " personnes montent") + " à l'étage " + ascenseur.getEtageCourant();
    }
}
