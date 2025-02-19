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
        basis = new GLZylinder(pX, 5, pZ, 2.5, 15); // Unterteil Pin
        basis.drehe(90, 90, 0); // Aufrechtstellen 
        basis.setzeFarbe(0, 100, 0); // Farbe Pin

        oberteil = new GLKugel(pX, 11, pZ, 3); // Oberteil Pin
        oberteil.setzeFarbe(0, 100, 0); // Farbe Pin

        umgeworfen = false;
        aktuellerWinkel = 0;
    }

    // Startet die Umwerf-Animation
    public void starteUmwerfen(Scoreboard scoreboard) {
        if (!umgeworfen) {
            scoreboard.erhoeheSpieler1Punkte(); // Erhöhe die Punkte für Spieler 1
            scoreboard.erhoeheSpieler2Punkte(); // Erhöhe die Punkte für Spieler 2
        }
        umgeworfen = true;
    }


    // Führt die Drehung schrittweise aus
    public void aktualisiere() {
        if (umgeworfen && aktuellerWinkel < 90) {
            // Drehpunkt: Unterer Rand der Basis (Y = 0)
            double drehpunktY = 0; // Drehpunkt auf dem Boden
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
        basis.setzePosition(startX, 5, startZ);
        oberteil.setzePosition(startX, 11, startZ);

        // Zurücksetzen des Status
        umgeworfen = false;
        aktuellerWinkel = 0;
    }

    public boolean istUmgeworfen() {
        return umgeworfen;
    }

    // Neue Methode zur Rückgabe der X-Position des Pins
    public double gibX() {
        return basis.gibX();
    }

    // Neue Methode zur Rückgabe der Z-Position des Pins
    public double gibZ() {
        return basis.gibZ();
    }
}