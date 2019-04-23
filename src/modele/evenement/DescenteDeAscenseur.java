package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import modele.Personne;
import modele.Statistique;
import vue.FenetreLogging;

import java.util.ArrayList;
import java.util.List;

public class DescenteDeAscenseur extends Evenement {
    private Ascenseur ascenseur;

    public DescenteDeAscenseur(int temps, Ascenseur ascenseur, Batiment batiment) {
        super(temps, batiment);
        this.ascenseur = ascenseur;
    }

    @Override
    public List<Evenement> executer(FenetreLogging fenetreLogging) {
        List<Evenement> evenements = new ArrayList<>();
        int i = 0;
        // pour toutes les personnes dans l'ascenseur
        while ( i < ascenseur.getPersonnes().size()) {
            Personne p = ascenseur.getPersonnes().get(i);

            // si l'ascenseur est arrivé à l'étage souhaité par la personne
            if (p.getNumeroEtageCible() == ascenseur.getEtageCourant()) {
                // faire descendre la personnes de l'ascenseur
                p.setAscenseur(null);
                this.ascenseur.getPersonnes().remove(p);
                batiment.supprimerPersonne(p);

                // ajouter le temps de sercvice dans les statistiques
                Statistique.getInstance().ajouterTempsService(temps - p.getHeureArrivee());

                // afficher l'événement dans le logging
                fenetreLogging.ajouterEvenement(this);

                // générer l'événement correspondant au retour de la personne (après le temps de travail) s'il y a lieu
                if (p.getNumeroEtageCible() != 1) {
                    // mettre à jour les étages de la personne
                    p.setNumeroEtageCourant(p.getNumeroEtageCible());
                    p.setNumeroEtageCible(1);
                    // ajouter un évènement pour son retour
                    evenements.add(new ArriveeClient(temps + p.calculerTempsTravail(), batiment, p));
                }
            } else {
                i++;
            }
        }

        // choisir la prochaine destination de l'ascenseur
        ascenseur.choisirProchaineDemande(batiment);

        return evenements;
    }

    @Override
    public String toString() {
        return "une personne descend à l'étage " + ascenseur.getEtageCourant() + " au temps " + temps;
    }
}