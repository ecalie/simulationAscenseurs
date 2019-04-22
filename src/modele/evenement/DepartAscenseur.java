package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import modele.Constante;

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
    public List<Evenement> executer() {
        this.ascenseur.setEnMouvement(true);

        List<Evenement> evenements = new ArrayList<>();
        evenements.add(
                new ArriveeAscenseur(
                        temps + Math.abs(ascenseur.getEtageCourant() - etageDestination) * Constante.tempsDeplacement,
                        ascenseur,
                        etageDestination,
                        batiment));
        return evenements;
    }
}
