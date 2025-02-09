import GLOOP.*;

public class Ball {
    private GLKugel kugel;
    private double radius; // Radius der Kugel

    public Ball(double x, double z) {
        radius = 5; // Radius entspricht der Größe der Kugel
        kugel = new GLKugel(x, 5, z, radius); // Kugel auf der Bahn
        kugel.setzeFarbe(100, 0, 0); // Rot
    }

    // Methode zur Bewegung der Kugel
    public void bewege(double deltaX, double deltaZ) {
        kugel.verschiebe(deltaX, 0, deltaZ);
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
    }
}