import GLOOP.*;

public class Ball {
    private GLKugel kugel;
    private GLZylinder winkelZeiger; // Dünner langer Zylinder als Winkelanzeiger
    private double radius; // Radius der Kugel
    private double winkel; // Winkel in Grad für die schräge Bewegung

    public Ball(double x, double z) {
        radius = 5;
        kugel = new GLKugel(x, 5, z, radius);
        kugel.setzeFarbe(100, 0, 0); // Rot

        // Erstelle den Winkelzeiger durch den Ball
        winkelZeiger = new GLZylinder(x, 5, z, 0.5, 30); // Höhe 5(Auf der Bahn) Radius 0.5, Höhe 30
        winkelZeiger.setzeFarbe(0, 0, 100); // Blau

        winkel = 0; // Standardwinkel geradeaus
        aktualisiereWinkelZeiger(); // Winkelzeiger ausrichten
    }

    // Methode zum Setzen des Winkels
    public void setzeWinkel(double pWinkel) {
        winkel = Math.max(-45, Math.min(45, pWinkel)); // Winkel maximum auf -45 und 45
        aktualisiereWinkelZeiger(); // Winkelzeiger  ausrichten
    }

    // Methode zur Bewegung der Kugel
    public void bewege(double deltaX, double deltaZ) {
        // Konvertiere Winkel in Radiant für trigonometrische Berechnungen
        double radian = Math.toRadians(winkel);
        
        // Berechne die Bewegungskomponenten unter Berücksichtigung des Winkels
        double verschiebungX = deltaX * Math.cos(radian) + deltaZ * Math.sin(radian);
        double verschiebungZ = deltaZ * Math.cos(radian) - deltaX * Math.sin(radian);
        
        kugel.verschiebe(verschiebungX, 0, verschiebungZ);
        winkelZeiger.verschiebe(verschiebungX, 0, verschiebungZ); // Winkelzeiger mitbewegen
    }

    // Methode zum Aktualisieren der Ausrichtung des Winkelzeigers
    private void aktualisiereWinkelZeiger() {
        // Position des Zeigers auf die Kugel setzen
        winkelZeiger.setzePosition(kugel.gibX(), kugel.gibY(), kugel.gibZ());
        // Drehung zurücksetzen und dann nach dem Winkel ausrichten
        winkelZeiger.setzeDrehung(0, 0, 0); // geradeaus ausrichten
        winkelZeiger.drehe(0, winkel, 0);
    }

    // Getter-Methoden für Position und Radius
    public double gibX() {
        return kugel.gibX();
    }

    public double gibY() {
        return kugel.gibY();
    }

    public double gibZ() {
        return kugel.gibZ();
    }

    public double gibRadius() {
        return radius;
    }
    
    public void setzePosition(int pX, int pY, int pZ) {
        kugel.setzePosition(pX, pY, pZ);
        winkelZeiger.setzePosition(pX, pY, pZ); // Winkelzeiger mitbewegen
        aktualisiereWinkelZeiger(); // Sicherstellen, dass der Zeiger korrekt ausgerichtet ist
    }

    // Getter für den aktuellen Winkel
    public double gibWinkel() {
        return winkel;
    }
}