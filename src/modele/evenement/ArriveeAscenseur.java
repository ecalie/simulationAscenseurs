package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;

import java.util.ArrayList;
import java.util.List;

public class ArriveeAscenseur extends Evenement {
    private Ascenseur ascenseur;
    private int etage;

    public ArriveeAscenseur(int temps, Ascenseur ascenseur, int etage, Batiment batiment) {
        super(temps, batiment, 3);
        this.ascenseur = ascenseur;
        this.etage = etage;
    }

    @Override
    public boolean precondition() {
        return true;
    }

    @Override
    public boolean postcondition() {
        return true;
    }

    @Override
    public List<Evenement> executer() {
        // l'ascenseur arrive à destination
        this.ascenseur.setArrete(true);

        if (ascenseur.getPersonnes().isEmpty())
            ascenseur.setOccupe(false);

        // générer les événemnts suivants
        List<Evenement> evenements = new ArrayList<>();
        //      - faire monter les personnes qui attendent dans l'ascenseur
        evenements.add(
                new MonteeDansAscenseur(
                        temps + 1,
                        ascenseur,
                        batiment.getPersonnes(),
                        batiment));

        //      - faire descendre les personnes de l'ascenseur s'il y a lieu
        if (!ascenseur.getPersonnes().isEmpty()) {
            evenements.add(
                    new DescenteDeAscenseur(
                            temps + 1,
                            ascenseur,
                            batiment));
        }

        return evenements;
    }

    @Override
    public String decrire() {
        return "un ascenseur arrive à l'étage " + etage;
    }
}
