import GLOOP.*;

/**
 * Verwaltet die Kamera und ihre Steuerung im Spiel.
 */
public class KameraSteuerung {
    private GLSchwenkkamera kamera;
    int option = 0; // Variable zur Auswahl der Kameraperspektive (0 = hinten, 1 = vorne)

    /**
     * Konstruktor für die KameraSteuerung. Erstellt eine Schwenkkamera und setzt ihre Anfangsposition.
     */
    public KameraSteuerung() {
        kamera = new GLSchwenkkamera(); // Erstellt eine neue Schwenkkamera
        kamera.setzePosition(0, 100, -325); // Anfangsposition
        kamera.setzeBlickpunkt(0, 0, 0); // Blickpunkt (Mitte der Bahn)
    }

    /**
     * Steuert die Kamera basierend auf Tastatureingaben.
     * @param tastatur Das Tastatur-Objekt für die Eingabe
     */
    public void steuerungsschleife(GLTastatur tastatur) {
        if (tastatur.istGedrueckt('a')) {
            kamera.verschiebe(5, 0, 0); // links
        }
        if (tastatur.istGedrueckt('d')) {
            kamera.verschiebe(-5, 0, 0); // rechts
        }
        if (tastatur.istGedrueckt('w')) {
            kamera.verschiebe(0, 0, 5); // vorwärts
        }
        if (tastatur.istGedrueckt('s')) {
            kamera.verschiebe(0, 0, -5); // rückwärts
        }
        if (tastatur.istGedrueckt('q')) {
            kamera.verschiebe(0, 5, 0); // hoch
        }
        if (tastatur.istGedrueckt('e')) {
            kamera.verschiebe(0, -5, 0); // runter
        }
        if (tastatur.links()) {
            kamera.schwenkeHorizontal(-5); // Schwenkt nach links
        }
        if (tastatur.rechts()) {
            kamera.schwenkeHorizontal(5); // Schwenkt nach rechts
        }
        if (tastatur.oben()) {
            kamera.schwenkeVertikal(5); // Schwenkt nach oben
        }
        if (tastatur.unten()) {
            kamera.schwenkeVertikal(-5); // Schwenkt nach unten
        }
        if (tastatur.tab()) {
            option++; // Erhöht die Perspektive
            if (option == 2) { // Wenn die Option 2 erreicht ist
                option = 0; // Setzt die Option zurück auf 0
            }
            switch (option) { // Wählt die Kameraperspektive basierend auf der Option
                case 0: // Perspektive 0: Hinten auf die ganze Bahn
                    kamera.setzePosition(0, 100, -325); // Setzt Kamera weit hinten über der Bahn
                    kamera.setzeBlickpunkt(0, 0, 0); // Blickpunkt auf die Mitte der Bahn
                    break;
                case 1: // Perspektive 1: Vorne direkt auf die Kegel
                    kamera.setzePosition(0, 100, 10); // Setzt Kamera näher an die Kegel
                    kamera.setzeBlickpunkt(0, 5, 175); // Blickpunkt auf die Kegel gerichtet
                    break;
                case 2:
                    //kamera.setzePosition(); // Platzhalter
                    //kamera.setzeBlickpunkt(0, 5, 175);
                    break;
            }
            Sys.warte(100);
        }
        Sys.warte(5);
    }

    /**
     * Setzt die Position der Kamera.
     * @param pX X-Position
     * @param pY Y-Position
     * @param pZ Z-Position
     */
    public void setzePosition(int pX, int pY, int pZ) {
        kamera.setzePosition(pX, pY, pZ);
    }

    /**
     * Setzt den Blickpunkt der Kamera.
     * @param pX X-Position des Blickpunkts
     * @param pY Y-Position des Blickpunkts
     * @param pZ Z-Position des Blickpunkts
     */
    public void setzeBlickpunkt(int pX, int pY, int pZ) {
        kamera.setzeBlickpunkt(pX, pY, pZ);
    }
}