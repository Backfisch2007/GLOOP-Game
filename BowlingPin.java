import GLOOP.*;

/**
 * Repräsentiert einen Bowling-Pin im Spiel.
 */
public class BowlingPin {
    private GLZylinder basis; // Unterteil des Pins (Zylinder)
    private GLKugel oberteil; // Oberteil des Pins (Kugel)
    private boolean umgeworfen; // Speichert, ob der Pin umgeworfen wurde
    private double aktuellerWinkel; // Trackt den aktuellen Drehwinkel des Pins beim Umfallen
    private final int position; // Eindeutige Nummer des Pins (0 bis 9)
    private final double startX, startZ; // Startkoordinaten des Pins (für Zurücksetzen)
    private double vekX, vekZ; // Vektoren für die Richtung der Verschiebung beim Umfallen
    private double fallGeschwindigkeit; // Steuert, wie schnell der Pin umfällt

    /**
     * Konstruktor für einen Bowling-Pin. Erstellt die Basis und das Oberteil des Pins an der angegebenen Position.
     * @param pX X-Position des Pins
     * @param pZ Z-Position des Pins
     * @param pPosition Eindeutige Nummer des Pins (0 bis 9)
     */
    public BowlingPin(double pX, double pZ, int pPosition) {
        position = pPosition; // Setzt die Position des Pins (z. B. 0 für den ersten Pin)
        startX = pX; // Speichert die Start-X-Position fürs Zurücksetzen
        startZ = pZ; // Speichert die Start-Z-Position fürs Zurücksetzen

        // Erstellt den unteren Teil des Pins (Zylinder)
        basis = new GLZylinder(pX, 5, pZ, 2.5, 15);
        basis.drehe(90, 90, 0); // Dreht den Zylinder um 90 Grad (aufrecht stellen)
        basis.setzeFarbe(0, 100, 0);

        // Erstellt den oberen Teil des Pins (Kugel)
        oberteil = new GLKugel(pX, 11, pZ, 3);
        oberteil.setzeFarbe(0, 100, 0);

        umgeworfen = false;
        aktuellerWinkel = 0;
        fallGeschwindigkeit = 5; // Geschwindigkeit des Umfallens (höher = schneller)
    }

    /**
     * Startet die Umwerf-Animation des Pins und erhöht die Punkte des aktuellen Spielers.
     * @param scoreboard Das Scoreboard, das die Punkte verwaltet
     * @param aktuellerSpieler Der aktuelle Spieler (1 oder 2)
     */
    public void starteUmwerfen(Scoreboard scoreboard, int aktuellerSpieler) {
        if (!umgeworfen) {
            if (aktuellerSpieler == 1) {
                scoreboard.erhoeheSpieler1Punkte();
            } else {
                scoreboard.erhoeheSpieler2Punkte();
            }
            umgeworfen = true; // Markiert den Pin als umgeworfen
            
            // Berechnet die Richtung, in die der Pin fallen soll
            double ballX = 0;
            double ballZ = -200;
            vekX = basis.gibX() - ballX;
            vekZ = basis.gibZ() - ballZ;

            double betrag = Math.sqrt(vekX * vekX + vekZ * vekZ); // Berechnet die Länge des Vektors
            if (betrag > 0) {
                vekX /= betrag;
                vekZ /= betrag;
            }

            // Fügt Zufälligkeit zur Fallrichtung hinzu
            double zufallsFaktor = 0.5; // Stärke der Zufälligkeit (höher = stärker verteilt)
            vekX += Math.random() * zufallsFaktor * 2 - zufallsFaktor;
            vekZ += Math.random() * zufallsFaktor * 2 - zufallsFaktor;

            betrag = Math.sqrt(vekX * vekX + vekZ * vekZ); // Berechnet die neue Länge des Vektors
            if (betrag > 0) { // Verhindert Division durch 0
                vekX /= betrag;
                vekZ /= betrag;
            }
        }
    }

    /**
     * Aktualisiert die Drehung und Verschiebung des Pins beim Umfallen.
     */
    public void aktualisiere() {
        if (umgeworfen && aktuellerWinkel < 90) {
            double drehpunktY = 0;
            double drehpunktZ = basis.gibZ();

            // Erhöht den Winkel schrittweise mit der Fallgeschwindigkeit
            aktuellerWinkel += fallGeschwindigkeit;
            if (aktuellerWinkel > 90) aktuellerWinkel = 90; // Begrenzt den Winkel auf maximal 90 Grad

            // Dreht den Pin um die X-Achse
            basis.drehe(-fallGeschwindigkeit, 0, 0, basis.gibX(), drehpunktY, drehpunktZ);
            oberteil.drehe(-fallGeschwindigkeit, 0, 0, basis.gibX(), drehpunktY, drehpunktZ);

            // Berechnet die Verschiebung des Pins in die Fallrichtung
            double verschiebungX = vekX * fallGeschwindigkeit * 0.5;
            double verschiebungZ = vekZ * fallGeschwindigkeit * 0.5;
            double verschiebungY = -Math.sin(Math.toRadians(aktuellerWinkel)) * 0.5;

            // Verschiebt Basis und Oberteil des Pins
            basis.verschiebe(verschiebungX, verschiebungY, verschiebungZ);
            oberteil.verschiebe(verschiebungX, verschiebungY, verschiebungZ);
        }
    }

    /**
     * Setzt den Pin zurück in seine Ausgangsposition.
     */
    public void zuruecksetzen() {
        basis.setzeDrehung(90, 90, 0);
        oberteil.setzeDrehung(0, 0, 0);
        basis.setzePosition(startX, 5, startZ);
        oberteil.setzePosition(startX, 11, startZ);
        umgeworfen = false; // Markiert den Pin als nicht umgeworfen
        aktuellerWinkel = 0;
    }

    /**
     * Gibt zurück, ob der Pin umgeworfen ist.
     * @return true, wenn der Pin umgeworfen ist, sonst false
     */
    public boolean istUmgeworfen() {
        return umgeworfen;
    }

    /**
     * Gibt die X-Position des Pins zurück.
     * @return X-Position des Pins
     */
    public double gibX() {
        return basis.gibX();
    }

    /**
     * Gibt die Z-Position des Pins zurück.
     * @return Z-Position des Pins
     */
    public double gibZ() {
        return basis.gibZ();
    }
}