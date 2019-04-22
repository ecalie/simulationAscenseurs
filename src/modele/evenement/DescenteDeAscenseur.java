package modele.evenement;

import modele.Ascenseur;
import modele.Batiment;
import modele.Personne;

import java.util.ArrayList;
import java.util.List;

public class DescenteDeAscenseur extends Evenement {
    private Ascenseur ascenseur;

    public DescenteDeAscenseur(int temps, Ascenseur ascenseur, Batiment batiment) {
        super(temps, batiment);
        this.ascenseur = ascenseur;
    }

    @Override
    public List<Evenement> executer() {
        List<Evenement> evenements = new ArrayList<>();
        int i = 0;
        while ( i < ascenseur.getPersonnes().size()) {
            Personne p = ascenseur.getPersonnes().get(i);
            if (p.getNumeroEtageCible() == ascenseur.getEtageCourant()) {

                System.out.println("descente en " + temps);


                p.setAscenseur(null);
                this.ascenseur.getPersonnes().remove(p);
                batiment.supprimerPersonne(p);
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

        if (ascenseur.getPersonnes().isEmpty()) {
            ascenseur.choisirProchaineDemande(batiment.getDemandes(), batiment);
        }

        return evenements;
    }
}