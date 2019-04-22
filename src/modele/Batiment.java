package modele;

import java.util.ArrayList;
import java.util.List;

public class Batiment {

    private List<Ascenseur> ascenseurs;
    private List<Etage> etages;
    private List<Personne> personnes;
    private GestionnaireEvenement gestionnaireEvenement;
    private Horloge horloge;

    public Batiment(int nombreAscenseurs, int nombreEtages) {
        this.etages = new ArrayList<>();
        this.ascenseurs = new ArrayList<>();
        this.personnes = new ArrayList<>();
        this.horloge = new Horloge();

        for (int i = 0; i < nombreAscenseurs; i++)
            this.ascenseurs.add(new Ascenseur());

        for (int i = 0; i < nombreEtages; i++)
            this.etages.add(new Etage(i));
    }

    public void setGestionnaireEvenement(GestionnaireEvenement gestionnaireEvenement) {
        this.gestionnaireEvenement = gestionnaireEvenement;
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
        Demande demande = new Demande(
                personne.getNumeroEtageCourant(),
                personne.getNumeroEtageCible(),
                personne);

        if (Constante.strategie == Strategie.fcfs) {
            // chercher l'ascenseur le plus proche
            Ascenseur ascenseurChoisit = null;
            int tempsMin = 10000;
            for (Ascenseur a : ascenseurs) {
                int temps = Math.max(horloge.getHeure(), a.getOccupation()) +
                        Math.abs(a.getEtageCourant() - personne.getNumeroEtageCourant()) * Constante.tempsDeplacement;
                if (temps < tempsMin) {
                    ascenseurChoisit = a;
                    tempsMin = temps;
                }
            }

            ascenseurChoisit.notifierFifo(demande, gestionnaireEvenement, this);
        }
    }
}
