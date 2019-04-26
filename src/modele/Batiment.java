package modele;

import java.util.ArrayList;
import java.util.List;

public class Batiment {

    private List<Ascenseur> ascenseurs;
    private int nombreEtages;
    private List<Personne> personnes;
    private GestionnaireEvenement gestionnaireEvenement;
    private Horloge horloge;
    private List<Integer> ascenseursParEtages;

    public Batiment(int nombreAscenseurs, int nombreEtages, int dureeSimuation) {
        this.nombreEtages = nombreEtages;
        this.ascenseurs = new ArrayList<>();
        this.personnes = new ArrayList<>();
        this.horloge = new Horloge();
        this.gestionnaireEvenement = new GestionnaireEvenement(this, dureeSimuation);

        for (int i = 0; i < nombreAscenseurs; i++)
            this.ascenseurs.add(new Ascenseur(gestionnaireEvenement));

        this.ascenseursParEtages = new ArrayList<>();
        for (int i = 0; i < nombreEtages; i++)
            ascenseursParEtages.add(0);
        ascenseursParEtages.set(1, 2);
    }

    public GestionnaireEvenement getGestionnaireEvenement() {
        return gestionnaireEvenement;
    }

    public Horloge getHorloge() {
        return horloge;
    }

    public List<Integer> getAscenseursParEtages() {
        return ascenseursParEtages;
    }

    public List<Ascenseur> getAscenseurs() {
        return ascenseurs;
    }

    public List<Personne> getPersonnes() {
        return personnes;
    }

    public int getNombreEtages() {
        return nombreEtages;
    }

    public void ajouterPersonne(Personne p) {
        this.personnes.add(p);
    }

    public void supprimerPersonne(Personne p) {
        this.personnes.remove(p);
    }

    /**
     * Répondre à une demande d'une personne qui attend l'ascenseur.
     *
     * @param personne La personne qui attend l'ascenseur
     */
    public void demanderAscenseur(Personne personne) {
        // trouver l'acenseur non occupé le plus proche
        int distanceMin = 1000;
        Ascenseur ascenseurDisponible = null;
        int distance;
        for (Ascenseur a : ascenseurs) {
            if (!a.isOccupe()/* && a.getPersonnes().isEmpty()*/) {
                distance = Math.abs(a.getEtageCourant() - personne.getNumeroEtageCourant());
                if (distance < distanceMin) {
                    distanceMin = distance;
                    ascenseurDisponible = a;
                }
            }
        }

        // si un ascensuer est disponible, traiter la demande
        if (ascenseurDisponible != null)
            ascenseurDisponible.traiterDemande(personne.getNumeroEtageCourant(), this);
    }
}
