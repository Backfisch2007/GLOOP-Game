import GLOOP.*;

/**
 * Repräsentiert den Bowlingball im Spiel.
 */
public class Ball {
    private GLKugel kugel;
    private GLZylinder winkelZeiger; // Dünner Zylinder als Winkelanzeiger für die Wurfrichtung
    private double radius; // Radius des Balls
    private double winkel; // Winkel in Grad für die schräge Bewegung des Balls

    /**
     * Konstruktor für den Ball. Erstellt eine Kugel und einen Winkelzeiger an der angegebenen Position
     * @param x X-Position des Balls
     * @param z Z-Position des Balls
     */
    public Ball(double x, double z) {
        radius = 5; // Radius Ball
        kugel = new GLKugel(x, 5, z, radius); // Erstellt die Kugel an Position (x, y=5, z) mit Radius 5
        kugel.setzeFarbe(255, 0, 0); // Farbe 
        //kugel.setzeTextur(""); !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // Erstellt den Winkelzeiger als Zylinder
        winkelZeiger = new GLZylinder(x, 5, z, 0.5, 30);
        winkelZeiger.setzeFarbe(0, 0, 100); // Farbe Blau

        winkel = 0; // Setzt den Standardwinkel auf 0 Grad (geradeaus)
        aktualisiereWinkelZeiger(); // Richtet den Winkelzeiger aus
    }

    /**
     * Setzt den Winkel für die Wurfrichtung des Balls. Der Winkel wird auf einen Bereich zwischen -45 und 45 Grad begrenzt.
     * @param pWinkel Der gewünschte Winkel in Grad
     */
    public void setzeWinkel(double pWinkel) {
        winkel = Math.max(-45, Math.min(45, pWinkel)); // Begrenzt den Winkel zwischen -45 und 45 Grad
        aktualisiereWinkelZeiger(); // Aktualisiert die Ausrichtung des Winkelzeigers
    }

    /**
     * Bewegt den Ball basierend auf dem aktuellen Winkel und den übergebenen Delta-Werten.
     * @param deltaX Bewegung in X-Richtung
     * @param deltaZ Bewegung in Z-Richtung
     */
    public void bewege(double deltaX, double deltaZ) {
        // Konvertiert den Winkel in Radiant für Sinus und Kosinus 
        double radian = Math.toRadians(winkel); // Umrechnung von Grad in Radiant

        // Berechnet die Bewegungskomponenten basierend auf dem Winkel
        double verschiebungX = deltaX * Math.cos(radian) + deltaZ * Math.sin(radian); // X-Komponente der Bewegung
        double verschiebungZ = deltaZ * Math.cos(radian) - deltaX * Math.sin(radian); // Z-Komponente der Bewegung

        kugel.verschiebe(verschiebungX, 0, verschiebungZ); // Bewegt die Kugel in die berechnete Richtung
        winkelZeiger.verschiebe(verschiebungX, 0, verschiebungZ); // Bewegt den Winkelzeiger mit der Kugel
    }

    /**
     * Aktualisiert die Ausrichtung des Winkelzeigers basierend auf dem aktuellen Winkel.
     */
    private void aktualisiereWinkelZeiger() {
        // Setzt die Position des Winkelzeigers auf die Mitte der Kugel
        winkelZeiger.setzePosition(kugel.gibX(), kugel.gibY(), kugel.gibZ()); // Position an Kugel anpassen
        // Drehung zurücksetzen und dann nach dem Winkel ausrichten
        winkelZeiger.setzeDrehung(0, 0, 0); // Setzt die Drehung zurück (geradeaus)
        winkelZeiger.drehe(0, winkel, 0); // Dreht den Zeiger um den aktuellen Winkel (Y-Achse)
    }

    /**
     * Gibt die X-Position des Balls zurück.
     * @return X-Position des Balls
     */
    public double gibX() {
        return kugel.gibX(); // Liefert die aktuelle X-Position der Kugel
    }

    /**
     * Gibt die Y-Position des Balls zurück.
     * @return Y-Position des Balls
     */
    public double gibY() {
        return kugel.gibY(); // Liefert die aktuelle Y-Position der Kugel
    }

    /**
     * Gibt die Z-Position des Balls zurück.
     * @return Z-Position des Balls
     */
    public double gibZ() {
        return kugel.gibZ(); // Liefert die aktuelle Z-Position der Kugel
    }

    /**
     * Gibt den Radius des Balls zurück.
     * @return Radius des Balls
     */
    public double gibRadius() {
        return radius; // Liefert den Radius der Kugel
    }

    /**
     * Setzt die Position des Balls manuell auf die angegebenen Koordinaten.
     * @param pX X-Position
     * @param pY Y-Position
     * @param pZ Z-Position
     */
    public void setzePosition(int pX, int pY, int pZ) {
        kugel.setzePosition(pX, pY, pZ); // Setzt die Kugel auf die angegebenen Koordinaten (x, y, z)
        winkelZeiger.setzePosition(pX, pY, pZ); // Setzt den Winkelzeiger auf dieselben Koordinaten
        aktualisiereWinkelZeiger(); // Stellt sicher, dass der Winkelzeiger korrekt ausgerichtet bleibt
    }

    /**
     * Gibt den aktuellen Winkel des Balls zurück.
     * @return Aktueller Winkel in Grad
     */
    public double gibWinkel() {
        return winkel; // Liefert den aktuellen Winkel in Grad
    }
}