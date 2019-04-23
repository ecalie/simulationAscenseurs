package modele;

public class Constante {
    public static final double lambda = 0.0833;
    public static final int tempsDeplacement = 1;
    public static final int tempsAttente = 1000;
    public static StrategieService strategieService = StrategieService.sstf;
    public static StrategieRalenti strategieRalenti = StrategieRalenti.etage1;
}
