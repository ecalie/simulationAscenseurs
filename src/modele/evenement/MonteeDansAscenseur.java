package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import modele.Constante;
import modele.Personne;

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
    public List<Evenement> executer() {
        boolean nouvellePersonne = false;
        for (Personne p : personnes)
            if (!ascenseur.getPersonnes().contains(p) && p.getNumeroEtageCourant() == ascenseur.getEtageCourant()) {
                p.setAscenseur(ascenseur);
                this.ascenseur.getPersonnes().add(p);
                System.out.println("montée en " + temps);
                nouvellePersonne = true;
            }

        if (nouvellePersonne) {
            ascenseur.choisirProchaineDemande(batiment);
        }

        return new ArrayList<>();
    }
}
