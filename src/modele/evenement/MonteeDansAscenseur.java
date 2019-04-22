package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import modele.Personne;
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
                p.setAscenseur(ascenseur);
                this.ascenseur.getPersonnes().add(p);
                fenetreLogging.ajouterEvenement(this);
                nouvellePersonne = true;
            }

        if (nouvellePersonne) {
            ascenseur.choisirProchaineDemande(batiment);
        }

        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "une personne monte à l'étage " + ascenseur.getEtageCourant() + " au temps " + temps;
    }
}
