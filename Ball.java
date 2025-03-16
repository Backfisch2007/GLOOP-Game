import GLOOP.*;

/**
 * Bowlingball im Spiel.
 */
public class Ball {
    private GLKugel kugel;
    private GLZylinder winkelZeiger; // Dünner Zylinder als Winkelanzeiger für Wurfrichtung
    private double radius;
    private double winkel;

    /**
     * Konstruktor für den Ball. Erstellt eine Kugel und einen Winkelzeiger an der angegebenen Position
     * @param x X-Position des Balls
     * @param z Z-Position des Balls
     */
    public Ball(double x, double z) {
        radius = 5;
        kugel = new GLKugel(x, 5, z, radius);
        kugel.setzeFarbe(255, 0, 0);
        //kugel.setzeTextur("");

        // Erstellt den Winkelzeiger als Zylinder
        winkelZeiger = new GLZylinder(x, 5, z, 0.5, 30);
        winkelZeiger.setzeFarbe(0, 0, 100);

        winkel = 0;
        aktualisiereWinkelZeiger();
    }

    /**
     * Setzt den Winkel für die Wurfrichtung des Balls. Der Winkel wird auf einen Bereich zwischen -45 und 45 Grad begrenzt.
     * @param pWinkel Der gewünschte Winkel in Grad
     */
    public void setzeWinkel(double pWinkel) {
        winkel = Math.max(-45, Math.min(45, pWinkel));
        aktualisiereWinkelZeiger();
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
        double verschiebungX = deltaX * Math.cos(radian) + deltaZ * Math.sin(radian);
        double verschiebungZ = deltaZ * Math.cos(radian) - deltaX * Math.sin(radian);

        kugel.verschiebe(verschiebungX, 0, verschiebungZ); // Bewegt die Kugel in die berechnete Richtung
        winkelZeiger.verschiebe(verschiebungX, 0, verschiebungZ); // Bewegt den Winkelzeiger mit der Kugel
    }

    /**
     * Aktualisiert die Ausrichtung des Winkelzeigers basierend auf dem aktuellen Winkel.
     */
    private void aktualisiereWinkelZeiger() {
        // Setzt die Position des Winkelzeigers auf die Mitte der Kugel
        winkelZeiger.setzePosition(kugel.gibX(), kugel.gibY(), kugel.gibZ());
        
        // Drehung zurücksetzen und dann nach dem Winkel ausrichten
        winkelZeiger.setzeDrehung(0, 0, 0); // Setzt die Drehung zurück (geradeaus)
        winkelZeiger.drehe(0, winkel, 0);
    }

    /**
     * Gibt die X-Position des Balls zurück.
     * @return X-Position des Balls
     */
    public double gibX() {
        return kugel.gibX();
    }

    /**
     * Gibt die Y-Position des Balls zurück.
     * @return Y-Position des Balls
     */
    public double gibY() {
        return kugel.gibY();
    }

    /**
     * Gibt die Z-Position des Balls zurück.
     * @return Z-Position des Balls
     */
    public double gibZ() {
        return kugel.gibZ();
    }

    /**
     * Gibt den Radius des Balls zurück.
     * @return Radius des Balls
     */
    public double gibRadius() {
        return radius;
    }

    /**
     * Setzt die Position des Balls manuell auf die angegebenen Koordinaten.
     * @param pX X-Position
     * @param pY Y-Position
     * @param pZ Z-Position
     */
    public void setzePosition(int pX, int pY, int pZ) {
        kugel.setzePosition(pX, pY, pZ);
        winkelZeiger.setzePosition(pX, pY, pZ);
        aktualisiereWinkelZeiger();
    }

    /**
     * Gibt den aktuellen Winkel des Balls zurück.
     * @return Aktueller Winkel in Grad
     */
    public double gibWinkel() {
        return winkel;
    }
}