package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import modele.Constante;
import vue.FenetreLogging;

import java.util.ArrayList;
import java.util.List;

public class DepartAscenseur extends Evenement {
    private Ascenseur ascenseur;
    private int etageDestination;

    public DepartAscenseur(int temps, Ascenseur ascenseur, int etageDestination, Batiment batiment) {
        super(temps, batiment);
        this.ascenseur = ascenseur;
        this.etageDestination = etageDestination;
    }

    @Override
    public List<Evenement> executer(FenetreLogging fenetreLogging) {
        // l'ascenseur commence à se déplacer
        this.ascenseur.setEnMouvement(true);

        // modifier le sens de déplacement de l'ascenseur
        if (ascenseur.getEtageCourant() < etageDestination)
            ascenseur.setSens(-1);
        else if (ascenseur.getEtageCourant() > etageDestination)
            ascenseur.setSens(1);

        // générer l'événemet d'arrivée de l'ascenseur
        List<Evenement> evenements = new ArrayList<>();
        evenements.add(
                new ArriveeAscenseur(
                        temps + Math.abs(ascenseur.getEtageCourant() - etageDestination) * Constante.tempsDeplacement,
                        ascenseur,
                        etageDestination,
                        batiment));

        // afficher l'événement
        fenetreLogging.ajouterEvenement(this);
        return evenements;
    }

    @Override
    public String toString() {
        return "un ascenseur part de l'étage " + ascenseur.getEtageCourant() + " au temps " + temps;
    }
}
