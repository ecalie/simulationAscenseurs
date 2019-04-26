package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import modele.Constante;

import java.util.ArrayList;
import java.util.List;

public class DepartAscenseur extends Evenement {
    private Ascenseur ascenseur;
    private int etageDestination;
    private int etageDepart;

    public DepartAscenseur(int temps, Ascenseur ascenseur, int etageDestination, Batiment batiment) {
        super(temps, batiment, 3);
        this.ascenseur = ascenseur;
        this.etageDestination = etageDestination;
    }

    @Override
    public boolean precondition() {
        return ascenseur.getSens() == 0 && ascenseur.getEtageCourant() != etageDestination;
    }

    @Override
    public boolean postcondition() {
        return true;
    }

    @Override
    public List<Evenement> executer() {
        // l'ascenseur commence à se déplacer
        this.ascenseur.setOccupe(true);

        // modifier le sens de déplacement de l'ascenseur
        if (ascenseur.getEtageCourant() < etageDestination)
            ascenseur.setSens(-1);
        else
            ascenseur.setSens(1);
        this.etageDepart = this.ascenseur.getEtageCourant();
        this.ascenseur.setEtageCourant(etageDestination);

        // générer l'événemet d'arrivée de l'ascenseur
        List<Evenement> evenements = new ArrayList<>();
        evenements.add(
                new ArriveeAscenseur(
                        temps + Math.abs(etageDepart - etageDestination) * Constante.tempsDeplacement,
                        ascenseur,
                        etageDestination,
                        batiment));

        return evenements;
    }

    @Override
    public String decrire() {
        return "un ascenseur part de l'étage " + etageDepart + "   " + ascenseur;
    }
}
