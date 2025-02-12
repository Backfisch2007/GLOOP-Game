import GLOOP.*;

public class BowlingPin {
    private GLZylinder basis;
    private GLKugel oberteil;
    private boolean umgeworfen;
    private double aktuellerWinkel; // Trackt den Drehwinkel
    private final int position; // Eindeutige Position des Pins
    private final double startX, startZ; // Startposition des Pins

    public BowlingPin(double pX, double pZ, int pPosition) {
        position = pPosition;
        startX = pX;
        startZ = pZ;
        basis = new GLZylinder(pX, 0, pZ, 2.5, 10); // Unterteil Pin
        basis.drehe(90, 90, 0); // Aufrechtstellen 
        basis.setzeFarbe(0, 100, 0); // Farbe Pin
        
        oberteil = new GLKugel(pX, 6, pZ, 2.5); // Oberteil Pin
        oberteil.setzeFarbe(0, 100, 0); // Farbe Pin
        
        umgeworfen = false;
        aktuellerWinkel = 0;
    }

    // Startet die Umwerf-Animation
    public void starteUmwerfen(Scoreboard scoreboard) {
        if (umgeworfen == false) {
            scoreboard.erhoeheSpieler1Punkte(); // Erhöhe die Punkte für Spieler 1
            scoreboard.erhoeheSpieler2Punkte(); // Erhöhe die Punkte für Spieler 2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        umgeworfen = true;
    }

    // Führt die Drehung schrittweise aus
    public void aktualisiere() {
        if (umgeworfen && aktuellerWinkel < 90) {
            // Drehpunkt: Unterer Rand der Basis (Y = 0)
            double drehpunktY = 0;
            // Drehung um die X-Achse am unteren Ende
            basis.drehe(2, 0, 0, basis.gibX(), drehpunktY, basis.gibZ());
            oberteil.drehe(2, 0, 0, basis.gibX(), drehpunktY, basis.gibZ());
            aktuellerWinkel += 2;
        }
    }

    // Setzt den Pin zurück in die Ausgangsposition
    public void zuruecksetzen() {
        // Zurücksetzen der Drehung
        basis.setzeDrehung(90, 90, 0);
        oberteil.setzeDrehung(0, 0, 0);

        // Zurücksetzen der Position
        basis.setzePosition(startX, 0, startZ);
        oberteil.setzePosition(startX, 6, startZ);

        // Zurücksetzen des Status
        umgeworfen = false;
        aktuellerWinkel = 0;
    }

    public boolean istUmgeworfen() {
        return umgeworfen;
    }
}