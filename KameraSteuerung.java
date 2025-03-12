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
        kamera.setzePosition(0, 100, -325); // Setzt die Anfangsposition der Kamera
        kamera.setzeBlickpunkt(0, 0, 0); // Setzt den Punkt, auf den die Kamera schaut (Mitte der Bahn)
    }

    /**
     * Steuert die Kamera basierend auf Tastatureingaben.
     * @param tastatur Das Tastatur-Objekt für die Eingabe
     */
    public void steuerungsschleife(GLTastatur tastatur) {
        if (tastatur.istGedrueckt('a')) { // Wenn Taste a gedrückt ist
            kamera.verschiebe(5, 0, 0); // Bewegt die Kamera 5 Einheiten nach links
        }
        if (tastatur.istGedrueckt('d')) { // Wenn Taste d gedrückt ist
            kamera.verschiebe(-5, 0, 0); // Bewegt die Kamera 5 Einheiten nach rechts
        }
        if (tastatur.istGedrueckt('w')) { // Wenn Taste w gedrückt ist
            kamera.verschiebe(0, 0, 5); // Bewegt die Kamera 5 Einheiten vorwärts
        }
        if (tastatur.istGedrueckt('s')) { // Wenn Taste s gedrückt ist
            kamera.verschiebe(0, 0, -5); // Bewegt die Kamera 5 Einheiten rückwärts
        }
        if (tastatur.istGedrueckt('q')) { // Wenn Taste q gedrückt ist
            kamera.verschiebe(0, 5, 0); // Bewegt die Kamera 5 Einheiten nach oben
        }
        if (tastatur.istGedrueckt('e')) { // Wenn Taste e gedrückt ist
            kamera.verschiebe(0, -5, 0); // Bewegt die Kamera 5 Einheiten nach unten
        }
        if (tastatur.links()) { // Wenn die linke Pfeiltaste gedrückt ist
            kamera.schwenkeHorizontal(-5); // Schwenkt die Kamera 5 Grad nach links
        }
        if (tastatur.rechts()) { // Wenn die rechte Pfeiltaste gedrückt ist
            kamera.schwenkeHorizontal(5); // Schwenkt die Kamera 5 Grad nach rechts
        }
        if (tastatur.oben()) { // Wenn die obere Pfeiltaste gedrückt ist
            kamera.schwenkeVertikal(5); // Schwenkt die Kamera 5 Grad nach oben
        }
        if (tastatur.unten()) { // Wenn die untere Pfeiltaste gedrückt ist
            kamera.schwenkeVertikal(-5); // Schwenkt die Kamera 5 Grad nach unten
        }
        if (tastatur.tab()) { // Wenn die Tab-Taste gedrückt ist
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
                case 2: // Möglichkeit für eine dritte Perspektive
                    //kamera.setzePosition(); // Platzhalter für eine weitere Kameraposition
                    break;
            }
            Sys.warte(100); // Macht eine kurze Pause von 100 Millisekunden um schnelles Wechseln zu verhindern
        }
        Sys.warte(5); // 5 Millisekunden Pause
    }

    /**
     * Setzt die Position der Kamera manuell.
     * @param pX X-Position
     * @param pY Y-Position
     * @param pZ Z-Position
     */
    public void setzePosition(int pX, int pY, int pZ) {
        kamera.setzePosition(pX, pY, pZ); // Setzt die Kamera auf die angegebenen Koordinaten
    }

    /**
     * Setzt den Blickpunkt der Kamera manuell.
     * @param pX X-Position des Blickpunkts
     * @param pY Y-Position des Blickpunkts
     * @param pZ Z-Position des Blickpunkts
     */
    public void setzeBlickpunkt(int pX, int pY, int pZ) {
        kamera.setzeBlickpunkt(pX, pY, pZ); // Setzt den Blickpunkt
    }
}