import GLOOP.*;

public class BowlingPin {
    private GLZylinder basis;
    private GLKugel oberteil;
    private boolean umgeworfen;
    private double aktuellerWinkel; // Trackt den Drehwinkel
    private final int position; // Eindeutige Position des Pins
    private final double startX, startZ; // Startposition des Pins
    private double vekX, vekZ; // Vektoren für die Verschiebung
    private double fallGeschwindigkeit; // Steuert die Geschwindigkeit des Falls

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
        fallGeschwindigkeit = 5; // Geschwindigkeit umfallen (Höher = schneller)
    }

    // Umwerf Animation (Zufällig)
    public void starteUmwerfen(Scoreboard scoreboard, int aktuellerSpieler) {
        if (!umgeworfen) {
            if (aktuellerSpieler == 1) {
                scoreboard.erhoeheSpieler1Punkte();
            } else {
                scoreboard.erhoeheSpieler2Punkte();
            }
            umgeworfen = true;
            // Berechne die Richtung, in die der Pin fallen soll
            double ballX = 0; // Annahme: Ball kommt etwa von der Mittellinie
            double ballZ = -200; // Startposition des Balls
            vekX = basis.gibX() - ballX; // Richtung relativ zum Ball
            vekZ = basis.gibZ() - ballZ; 

            // Normalisiere den Vektor für gleichmäßige Bewegung
            double betrag = Math.sqrt(vekX * vekX + vekZ * vekZ);
            if (betrag > 0) {
                vekX /= betrag;
                vekZ /= betrag;
            }

            // Füge Zufälligkeit hinzu
            double zufallsFaktor = 0.5; // Stärke der Zufälligkeit Normal 0.25
            vekX += Math.random() * zufallsFaktor * 2 - zufallsFaktor;
            vekZ += Math.random() * zufallsFaktor * 2 - zufallsFaktor;

            // Normalisiere den Vektor erneut, um die Länge konstant zu halten
            betrag = Math.sqrt(vekX * vekX + vekZ * vekZ);
            if (betrag > 0) {
                vekX /= betrag;
                vekZ /= betrag;
            }
        }
    }

    // Führt die Drehung und Verschiebung aus
    public void aktualisiere() {
        if (umgeworfen && aktuellerWinkel < 90) {
            // Drehpunkt: Unterer Rand der Basis
            double drehpunktY = 0; // Am Boden
            double drehpunktZ = basis.gibZ(); // Aktuelle Z-Position als Drehpunktbasis

            // Erhöhe den Winkel schrittweise mit fallGeschwindigkeit
            aktuellerWinkel += fallGeschwindigkeit;
            if (aktuellerWinkel > 90) aktuellerWinkel = 90; // Begrenze auf 90 Grad

            // Drehung um die X-Achse (realistischer Kippeffekt)
            basis.drehe(-fallGeschwindigkeit, 0, 0, basis.gibX(), drehpunktY, drehpunktZ);
            oberteil.drehe(-fallGeschwindigkeit, 0, 0, basis.gibX(), drehpunktY, drehpunktZ);

            // Verschiebe den Pin in Fallrichtung mit abnehmender Höhe
            double verschiebungX = vekX * fallGeschwindigkeit * 0.5; // Horizontale Bewegung
            double verschiebungZ = vekZ * fallGeschwindigkeit * 0.5; 
            double verschiebungY = -Math.sin(Math.toRadians(aktuellerWinkel)) * 0.5; // Vertikale Absenkung

            basis.verschiebe(verschiebungX, verschiebungY, verschiebungZ);
            oberteil.verschiebe(verschiebungX, verschiebungY, verschiebungZ);
        }
    }

    // Setzt den Pin zurück in die Ausgangsposition
    public void zuruecksetzen() {
        basis.setzeDrehung(90, 90, 0);
        oberteil.setzeDrehung(0, 0, 0);
        basis.setzePosition(startX, 5, startZ);
        oberteil.setzePosition(startX, 11, startZ);
        umgeworfen = false;
        aktuellerWinkel = 0;
    }

    public boolean istUmgeworfen() {
        return umgeworfen;
    }

    public double gibX() {
        return basis.gibX();
    }

    public double gibZ() {
        return basis.gibZ();
    }
}