package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Batiment {

    private List<Ascenseur> ascenseurs;
    private List<Etage> etages;
    private List<Personne> personnes;
    private GestionnaireEvenement gestionnaireEvenement;

    public Batiment(int nombreAscenseurs, int nombreEtages) {
        this.etages = new ArrayList<>();
        this.ascenseurs = new ArrayList<>();
        this.personnes = new ArrayList<>();

        for (int i = 0; i < nombreAscenseurs; i++)
            this.ascenseurs.add(new Ascenseur());

        for (int i = 0; i < nombreEtages; i++)
            this.etages.add(new Etage(i));
    }

    public void setGestionnaireEvenement(GestionnaireEvenement gestionnaireEvenement) {
        this.gestionnaireEvenement = gestionnaireEvenement;
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
        Demande demande = new Demande(
                personne.getNumeroEtageCourant(),
                personne.getNumeroEtageCible(),
                personne);

        if (Constante.strategie == Strategie.fifo) {
            Ascenseur ascenseurChoisit = null;
            int tempsRetourMin = 10000;
            for (Ascenseur a : ascenseurs) {
                int tempsArrivee = Math.abs(a.getEtageCourant() - personne.getNumeroEtageCourant()) * Constante.tempsDeplacement;
                if (tempsArrivee < tempsRetourMin) {
                    ascenseurChoisit = a;
                    tempsRetourMin = tempsArrivee;
                }
            }

            ascenseurChoisit.notifierFifo(demande, gestionnaireEvenement, this);
        }
    }
}
