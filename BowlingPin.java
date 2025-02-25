import GLOOP.*;

public class BowlingPin {
    private GLZylinder basis; // Unterteil des Pins (Zylinder)
    private GLKugel oberteil; // Oberteil des Pins (Kugel)
    private boolean umgeworfen; // Speichert, ob der Pin umgeworfen wurde
    private double aktuellerWinkel; // Trackt den aktuellen Drehwinkel des Pins beim Umfallen
    private final int position; // Eindeutige Nummer des Pins (0 bis 9)
    private final double startX, startZ; // Startkoordinaten des Pins (für Zurücksetzen)
    private double vekX, vekZ; // Vektoren für die Richtung der Verschiebung beim Umfallen
    private double fallGeschwindigkeit; // Steuert, wie schnell der Pin umfällt

    // Konstruktor für einen Bowling-Pin
    public BowlingPin(double pX, double pZ, int pPosition) {
        position = pPosition; // Setzt die Position des Pins (z. B. 0 für den ersten Pin)
        startX = pX; // Speichert die Start-X-Position fürs Zurücksetzen
        startZ = pZ; // Speichert die Start-Z-Position fürs Zurücksetzen
        
        // Erstellt den unteren Teil des Pins (Zylinder)
        basis = new GLZylinder(pX, 5, pZ, 2.5, 15); // Position (x, y, z), Radius 2.5, Höhe 15
        basis.drehe(90, 90, 0); // Dreht den Zylinder um 90 Grad (aufrecht stellen)
        basis.setzeFarbe(0, 100, 0); // Setzt die Farbe auf Grün (RGB: 0, 100, 0)

        // Erstellt den oberen Teil des Pins (Kugel)
        oberteil = new GLKugel(pX, 11, pZ, 3); // Position (x, y, z), Radius 3
        oberteil.setzeFarbe(0, 100, 0); // Setzt die Farbe auf Grün (RGB: 0, 100, 0)

        umgeworfen = false; // Pin ist anfangs nicht umgeworfen
        aktuellerWinkel = 0; // Startwinkel ist 0 Grad
        fallGeschwindigkeit = 5; // Geschwindigkeit des Umfallens (höher = schneller)
    }

    // Startet die Umwerf-Animation des Pins (mit Zufälligkeit)
    public void starteUmwerfen(Scoreboard scoreboard, int aktuellerSpieler) {
        if (!umgeworfen) { // Nur ausführen, wenn der Pin noch nicht umgeworfen ist
            if (aktuellerSpieler == 1) { // Wenn Spieler 1 dran ist
                scoreboard.erhoeheSpieler1Punkte(); // Erhöht die Punkte von Spieler 1
            } else { // Wenn Spieler 2 dran ist
                scoreboard.erhoeheSpieler2Punkte(); // Erhöht die Punkte von Spieler 2
            }
            umgeworfen = true; // Markiert den Pin als umgeworfen
            // Berechnet die Richtung, in die der Pin fallen soll
            double ballX = 0; // Annahme: Ball kommt von der Mittellinie (x = 0)
            double ballZ = -200; // Startposition des Balls auf der Bahn (z = -200)
            vekX = basis.gibX() - ballX; // X-Richtung relativ zur Ballposition
            vekZ = basis.gibZ() - ballZ; // Z-Richtung relativ zur Ballposition

            // Normalisiert den Vektor für gleichmäßige Bewegung
            double betrag = Math.sqrt(vekX * vekX + vekZ * vekZ); // Berechnet die Länge des Vektors
            if (betrag > 0) { // Verhindert Division durch 0
                vekX /= betrag; // Normalisiert die X-Komponente
                vekZ /= betrag; // Normalisiert die Z-Komponente
            }

            // Fügt Zufälligkeit zur Fallrichtung hinzu
            double zufallsFaktor = 0.5; // Stärke der Zufälligkeit (normalerweise 0.25)
            vekX += Math.random() * zufallsFaktor * 2 - zufallsFaktor; // Zufällige X-Abweichung
            vekZ += Math.random() * zufallsFaktor * 2 - zufallsFaktor; // Zufällige Z-Abweichung

            // Normalisiert den Vektor erneut, um die Länge konstant zu halten
            betrag = Math.sqrt(vekX * vekX + vekZ * vekZ); // Berechnet die neue Länge des Vektors
            if (betrag > 0) { // Verhindert Division durch 0
                vekX /= betrag; // Normalisiert die X-Komponente
                vekZ /= betrag; // Normalisiert die Z-Komponente
            }
        }
    }

    // Aktualisiert die Drehung und Verschiebung des Pins beim Umfallen
    public void aktualisiere() {
        if (umgeworfen && aktuellerWinkel < 90) { // Nur ausführen, wenn der Pin umgeworfen ist und noch nicht 90 Grad erreicht hat
            // Drehpunkt: Unterer Rand der Basis
            double drehpunktY = 0; // Drehpunkt liegt am Boden (y = 0)
            double drehpunktZ = basis.gibZ(); // Aktuelle Z-Position als Basis für den Drehpunkt

            // Erhöht den Winkel schrittweise mit der Fallgeschwindigkeit
            aktuellerWinkel += fallGeschwindigkeit; // Addiert die Fallgeschwindigkeit zum Winkel
            if (aktuellerWinkel > 90) aktuellerWinkel = 90; // Begrenzt den Winkel auf maximal 90 Grad

            // Dreht den Pin um die X-Achse für einen realistischen Kippeffekt
            basis.drehe(-fallGeschwindigkeit, 0, 0, basis.gibX(), drehpunktY, drehpunktZ); // Dreht die Basis
            oberteil.drehe(-fallGeschwindigkeit, 0, 0, basis.gibX(), drehpunktY, drehpunktZ); // Dreht das Oberteil

            // Berechnet die Verschiebung des Pins in die Fallrichtung
            double verschiebungX = vekX * fallGeschwindigkeit * 0.5; // Horizontale X-Bewegung
            double verschiebungZ = vekZ * fallGeschwindigkeit * 0.5; // Horizontale Z-Bewegung
            double verschiebungY = -Math.sin(Math.toRadians(aktuellerWinkel)) * 0.5; // Vertikale Absenkung basierend auf dem Winkel

            // Verschiebt Basis und Oberteil des Pins
            basis.verschiebe(verschiebungX, verschiebungY, verschiebungZ); // Bewegt die Basis
            oberteil.verschiebe(verschiebungX, verschiebungY, verschiebungZ); // Bewegt das Oberteil
        }
    }

    // Setzt den Pin zurück in seine Ausgangsposition
    public void zuruecksetzen() {
        basis.setzeDrehung(90, 90, 0); // Setzt die Drehung der Basis zurück (aufrecht)
        oberteil.setzeDrehung(0, 0, 0); // Setzt die Drehung des Oberteils zurück
        basis.setzePosition(startX, 5, startZ); // Setzt die Basis auf die Startposition
        oberteil.setzePosition(startX, 11, startZ); // Setzt das Oberteil auf die Startposition
        umgeworfen = false; // Markiert den Pin als nicht umgeworfen
        aktuellerWinkel = 0; // Setzt den Winkel zurück auf 0
    }

    // Gibt zurück, ob der Pin umgeworfen ist
    public boolean istUmgeworfen() {
        return umgeworfen; // Rückgabewert true, wenn umgeworfen, sonst false
    }

    // Gibt die X-Position des Pins zurück
    public double gibX() {
        return basis.gibX(); // Liefert die aktuelle X-Position der Basis
    }

    // Gibt die Z-Position des Pins zurück
    public double gibZ() {
        return basis.gibZ(); // Liefert die aktuelle Z-Position der Basis
    }
}