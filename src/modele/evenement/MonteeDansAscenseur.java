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


        List<Evenement> evenements = new ArrayList<>();

        if (nouvellePersonne) {
            switch (Constante.strategieService) {
                case sstf:
                    int distanceMin = 1000;
                    int etageDestination = 1;
                    for (Personne p : ascenseur.getPersonnes()) {
                        if (p.getNumeroEtageCible() - ascenseur.getEtageCourant() < distanceMin) {
                            distanceMin = p.getNumeroEtageCible() - ascenseur.getEtageCourant();
                            etageDestination = p.getNumeroEtageCible();
                        }
                    }
                    evenements.add(new DepartAscenseur(
                            temps + 1,
                            ascenseur,
                            etageDestination,
                            batiment));
                    break;
                default:
                    // fcfs par défaut
                    evenements.add(new DepartAscenseur(
                            temps + 1,
                            ascenseur,
                            ascenseur.getPersonnes().get(0).getNumeroEtageCible(),
                            batiment));
                    break;
            }
        }

        return evenements;
    }
}
