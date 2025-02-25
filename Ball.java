import GLOOP.*;

public class Ball {
    private GLKugel kugel;
    private GLZylinder winkelZeiger; // Dünner Zylinder als Winkelanzeiger für die Wurfrichtung
    private double radius; // Radius des Balls
    private double winkel; // Winkel in Grad für die schräge Bewegung des Balls

    // Konstruktor für den Ball
    public Ball(double x, double z) {
        radius = 5; // Radius Ball
        kugel = new GLKugel(x, 5, z, radius); // Erstellt die Kugel an Position (x, y=5, z) mit Radius 5
        kugel.setzeFarbe(100, 0, 0); // Farbe Rot

        // Erstellt den Winkelzeiger als Zylinder
        winkelZeiger = new GLZylinder(x, 5, z, 0.5, 30);
        winkelZeiger.setzeFarbe(0, 0, 100); // Farbe Blau

        winkel = 0; // Setzt den Standardwinkel auf 0 Grad (geradeaus)
        aktualisiereWinkelZeiger(); // Richtet den Winkelzeiger aus
    }

    // Methode zum Setzen des Winkels für die Wurfrichtung
    public void setzeWinkel(double pWinkel) {
        winkel = Math.max(-45, Math.min(45, pWinkel)); // Begrenzt den Winkel zwischen -45 und 45 Grad
        aktualisiereWinkelZeiger(); // Aktualisiert die Ausrichtung des Winkelzeigers
    }

    // Methode zur Bewegung des Balls
    public void bewege(double deltaX, double deltaZ) {
        // Konvertiert den Winkel in Radiant für Sinus und Kosinus 
        double radian = Math.toRadians(winkel); // Umrechnung von Grad in Radiant
        
        // Berechnet die Bewegungskomponenten basierend auf dem Winkel
        double verschiebungX = deltaX * Math.cos(radian) + deltaZ * Math.sin(radian); // X-Komponente der Bewegung
        double verschiebungZ = deltaZ * Math.cos(radian) - deltaX * Math.sin(radian); // Z-Komponente der Bewegung
        
        kugel.verschiebe(verschiebungX, 0, verschiebungZ); // Bewegt die Kugel in die berechnete Richtung
        winkelZeiger.verschiebe(verschiebungX, 0, verschiebungZ); // Bewegt den Winkelzeiger mit der Kugel
    }

    // Methode zum Aktualisieren der Ausrichtung des Winkelzeigers
    private void aktualisiereWinkelZeiger() {
        // Setzt die Position des Winkelzeigers auf die Mitte der Kugel
        winkelZeiger.setzePosition(kugel.gibX(), kugel.gibY(), kugel.gibZ()); // Position an Kugel anpassen
        // Drehung zurücksetzen und dann nach dem Winkel ausrichten
        winkelZeiger.setzeDrehung(0, 0, 0); // Setzt die Drehung zurück (geradeaus)
        winkelZeiger.drehe(0, winkel, 0); // Dreht den Zeiger um den aktuellen Winkel (Y-Achse)
    }

    // Getter-Methode für die X-Position des Balls
    public double gibX() {
        return kugel.gibX(); // Liefert die aktuelle X-Position der Kugel
    }

    // Getter-Methode für die Y-Position des Balls
    public double gibY() {
        return kugel.gibY(); // Liefert die aktuelle Y-Position der Kugel
    }

    // Getter-Methode für die Z-Position des Balls
    public double gibZ() {
        return kugel.gibZ(); // Liefert die aktuelle Z-Position der Kugel
    }

    // Getter-Methode für den Radius des Balls
    public double gibRadius() {
        return radius; // Liefert den Radius der Kugel
    }
    
    // Methode zum manuellen Setzen der Position des Balls
    public void setzePosition(int pX, int pY, int pZ) {
        kugel.setzePosition(pX, pY, pZ); // Setzt die Kugel auf die angegebenen Koordinaten (x, y, z)
        winkelZeiger.setzePosition(pX, pY, pZ); // Setzt den Winkelzeiger auf dieselben Koordinaten
        aktualisiereWinkelZeiger(); // Stellt sicher, dass der Winkelzeiger korrekt ausgerichtet bleibt
    }

    // Getter-Methode für den aktuellen Winkel des Balls
    public double gibWinkel() {
        return winkel; // Liefert den aktuellen Winkel in Grad
    }
}