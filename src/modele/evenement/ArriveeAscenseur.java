package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import vue.FenetreLogging;

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
    public List<Evenement> executer(FenetreLogging fenetreLogging) {
        // l'ascenseur arrive à destination
        this.ascenseur.setEtageCourant(etage);
        ascenseur.setEnMouvement(false);

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

        // afficher l'événement
        fenetreLogging.ajouterEvenement(this);

        return evenements;
    }

    @Override
    public String toString() {
        return "un ascenseur arrive à l'étage " + etage + " au temps " + temps;
    }
}
