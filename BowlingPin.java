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
    //public void starteUmwerfen(Scoreboard scoreboard) {
    //  if (!umgeworfen) {
    //    scoreboard.erhoeheSpieler1Punkte(); // Erhöhe die Punkte für Spieler 1
    //  scoreboard.erhoeheSpieler2Punkte(); // Erhöhe die Punkte für Spieler 2
    //}
    //umgeworfen = true;
    //}

    public void starteUmwerfen(Scoreboard scoreboard, double ballX, double ballZ) { //!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (!umgeworfen) {
            scoreboard.erhoeheSpieler1Punkte(); // Erhöhe die Punkte für Spieler 1
            scoreboard.erhoeheSpieler2Punkte(); // Erhöhe die Punkte für Spieler 2
        }
        umgeworfen = true;

        // Bestimme die Richtung, aus der der Ball kommt
        double deltaX = ballX - basis.gibX();
        double deltaZ = ballZ - basis.gibZ();

        // Setze die Drehrichtung basierend auf der Position des Balls
        if (Math.abs(deltaX) > Math.abs(deltaZ)) {
            // Ball kommt von links oder rechts
            if (deltaX > 0) {
                // Ball kommt von rechts, Pin fällt nach links
                drehrichtung = -1; // Negative Drehung um die Z-Achse
            } else {
                // Ball kommt von links, Pin fällt nach rechts
                drehrichtung = 1; // Positive Drehung um die Z-Achse
            }
        } else {
            // Ball kommt von vorne oder hinten
            if (deltaZ > 0) {
                // Ball kommt von hinten, Pin fällt nach vorne
                drehrichtung = -1; // Negative Drehung um die X-Achse
            } else {
                // Ball kommt von vorne, Pin fällt nach hinten
                drehrichtung = 1; // Positive Drehung um die X-Achse
            }
        }
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


