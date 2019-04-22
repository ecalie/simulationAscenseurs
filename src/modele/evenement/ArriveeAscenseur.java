package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;

import java.util.ArrayList;
import java.util.List;

public class ArriveeAscenseur extends Evenement {
    private Ascenseur ascenseur;
    private int etage;

    public ArriveeAscenseur(int temps, Ascenseur ascenseur, int etage, Batiment batiment) {
        super(temps, batiment);
        this.ascenseur = ascenseur;
        this.etage = etage;
    }

    @Override
    public List<Evenement> executer() {
        if (ascenseur.getEtageCourant() < etage)
            ascenseur.setSens(-1);
        else if (ascenseur.getEtageCourant() > etage)
            ascenseur.setSens(1);

        this.ascenseur.setEtageCourant(etage);
        ascenseur.setEnMouvement(false);

        System.out.println("déplacement en " + temps + " vers " + etage);

        List<Evenement> evenements = new ArrayList<>();
        evenements.add(
                new MonteeDansAscenseur(
                        temps+1,
                        ascenseur,
                        batiment.getPersonnes(),
                        batiment));

        if (!ascenseur.getPersonnes().isEmpty()) {
            evenements.add(
                    new DescenteDeAscenseur(
                            temps + 1,
                            ascenseur,
                            batiment));
        }

        return evenements;
    }
}
