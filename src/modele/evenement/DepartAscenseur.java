package modele.evenement;

import modele.Ascenseur;

import java.util.ArrayList;
import java.util.List;

public class DepartAscenseur extends Evenement {
    private Ascenseur ascenseur;

    public DepartAscenseur(int temps, Ascenseur ascenseur) {
        super(temps);
        this.ascenseur = ascenseur;
    }

    @Override
    public void executer() {
        this.ascenseur.setOccupe(true);
    }

    @Override
    public List<Evenement> genererProchainsEvenements() {
        return new ArrayList<>();
    }
}
