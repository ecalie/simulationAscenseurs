package modele;

import java.util.ArrayList;
import java.util.List;

public class Batiment {

    private List<Ascenseur> ascenseurs;
    private List<Etage> etages;
    private List<Personne> personnes;
    private GestionnaireEvenement gestionnaireEvenement;
    private Horloge horloge;

    public Batiment(int nombreAscenseurs, int nombreEtages, int dureeSimuation) {
        this.etages = new ArrayList<>();
        this.ascenseurs = new ArrayList<>();
        this.personnes = new ArrayList<>();
        this.horloge = new Horloge();
        this.gestionnaireEvenement = new GestionnaireEvenement(this, dureeSimuation);

        for (int i = 0; i < nombreAscenseurs; i++)
            this.ascenseurs.add(new Ascenseur(gestionnaireEvenement));

        for (int i = 0; i < nombreEtages; i++)
            this.etages.add(new Etage(i));
    }

    public GestionnaireEvenement getGestionnaireEvenement() {
        return gestionnaireEvenement;
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
        // trouver l'acenseur non occupé le plus proche
        int distanceMin = 1000;
        Ascenseur ascenseurDisponible = null;
        int distance;
        for (Ascenseur a : ascenseurs) {
            if (!a.isEnMouvement()) {
                distance = Math.abs(a.getEtageCourant() - personne.getNumeroEtageCourant());
                if (distance < distanceMin) {
                    distanceMin = distance;
                    ascenseurDisponible = a;
                }
            }
        }

        if (ascenseurDisponible != null)
            ascenseurDisponible.traiterDemande(personne.getNumeroEtageCourant(), this);
    }
}
