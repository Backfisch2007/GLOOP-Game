import GLOOP.*;

public class Ball{
    private GLKugel kugel;

    public Ball(double x, double z) {
        kugel = new GLKugel(x, 5, z, 7); // Kugel auf der Bahn
        kugel.setzeFarbe(100, 0, 0); // Rot
    }
}
