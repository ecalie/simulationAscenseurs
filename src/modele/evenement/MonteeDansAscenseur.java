package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import modele.Personne;
import modele.Statistique;
import vue.FenetreLogging;

import java.util.ArrayList;
import java.util.List;

public class MonteeDansAscenseur extends Evenement {
    private Ascenseur ascenseur;
    private List<Personne> personnes;

    public MonteeDansAscenseur(int temps, Ascenseur ascenseur, List<Personne> personnes, Batiment batiment) {
        super(temps, batiment);
        this.ascenseur = ascenseur;
        this.personnes = personnes;
    }

    @Override
    public List<Evenement> executer(FenetreLogging fenetreLogging) {
        boolean nouvellePersonne = false;
        for (Personne p : personnes)
            if (!ascenseur.getPersonnes().contains(p) && p.getNumeroEtageCourant() == ascenseur.getEtageCourant()) {
                // calculer le temps d'attente et l'enregistrer
                Statistique.getInstance().ajouterTempsAttente(temps - p.getHeureArrivee());

                // faire monter la personne dans l'ascenseur
                p.setAscenseur(ascenseur);
                this.ascenseur.getPersonnes().add(p);

                // afficher l'événement dans la fenetre de logging
                fenetreLogging.ajouterEvenement(this);

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
    public String toString() {
        return "une personne monte à l'étage " + ascenseur.getEtageCourant() + " au temps " + temps;
    }
}
