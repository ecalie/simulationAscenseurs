package modele;

import java.util.ArrayList;
import java.util.List;

public class Batiment {

    private List<Ascenseur> ascenseurs;
    private List<Etage> etages;
    private List<Personne> personnes;
    private GestionnaireEvenement gestionnaireEvenement;
    private Horloge horloge;
    private List<Demande> demandes;

    public Batiment(int nombreAscenseurs, int nombreEtages) {
        this.etages = new ArrayList<>();
        this.ascenseurs = new ArrayList<>();
        this.personnes = new ArrayList<>();
        this.demandes = new ArrayList<>();
        this.horloge = new Horloge();
        this.gestionnaireEvenement = new GestionnaireEvenement(this);

        for (int i = 0; i < nombreAscenseurs; i++)
            this.ascenseurs.add(new Ascenseur(gestionnaireEvenement));

        for (int i = 0; i < nombreEtages; i++)
            this.etages.add(new Etage(i));
    }

    public GestionnaireEvenement getGestionnaireEvenement() {
        return gestionnaireEvenement;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public Horloge getHorloge() {
        return horloge;
    }

    public List<Ascenseur> getAscenseurs() {
        return ascenseurs;
    }

    public List<Etage> getEtages() {
        return etages;
    }

    public List<Personne> getPersonnes() {
        return personnes;
    }

    public void ajouterPersonne(Personne p) {
        this.personnes.add(p);
    }

    public void supprimerPersonne(Personne p) {
        this.personnes.remove(p);
    }

    public void demanderAscenseur(Personne personne) {

        this.demandes.add(new Demande(
                personne.getNumeroEtageCourant(),
                personne.getNumeroEtageCible(),
                personne));

        // trouver un acenseur non occupé
        // TODO prendre le plus proche
        for (Ascenseur a : ascenseurs) {
            if (!a.isOccupe()) {
                a.choisirProchaineDemande(demandes, this);
                break;
            }
        }

//        if (Constante.strategie == Strategie.fcfs) {
//            // chercher l'ascenseur le plus proche
//            Ascenseur ascenseurChoisit = null;
//            int tempsMin = 10000;
//            for (Ascenseur a : ascenseurs) {
//                int temps = Math.max(horloge.getHeure(), a.getOccupation()) +
//                        Math.abs(a.getEtageCourant() - personne.getNumeroEtageCourant()) * Constante.tempsDeplacement;
//                if (temps < tempsMin) {
//                    ascenseurChoisit = a;
//                    tempsMin = temps;
//                }
//            }
//
//            ascenseurChoisit.notifierFifo(demande, gestionnaireEvenement, this);
//        } else if (Constante.strategie == Strategie.sstf) {
//            this.demandes.add(demande);
//        }
    }
}
