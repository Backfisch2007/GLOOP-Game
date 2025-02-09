import GLOOP.*;

public class KameraSteuerung {
    private GLSchwenkkamera kamera;
    int option = 0;

    public KameraSteuerung() {
        kamera = new GLSchwenkkamera();
        kamera.setzePosition(0, 100, -325); // Anfangsposition
        kamera.setzeBlickpunkt(0, 0, 0);

    }

    public void steuerungsschleife(GLTastatur tastatur) {
        if (tastatur.istGedrueckt('a')) {
            kamera.verschiebe(5, 0, 0); // Links
        }
        if (tastatur.istGedrueckt('d')) {
            kamera.verschiebe(-5, 0, 0); // Rechts
        }
        if (tastatur.istGedrueckt('w')) {
            kamera.verschiebe(0, 0, 5); // Vorwärts
        }
        if (tastatur.istGedrueckt('s')) {
            kamera.verschiebe(0, 0, -5); // Rückwärts
        }
        if (tastatur.istGedrueckt('q')) {
            kamera.verschiebe(0, 5, 0); // Hoch
        }
        if (tastatur.istGedrueckt('e')) {
            kamera.verschiebe(0, -5, 0); // Runter
        }
        if (tastatur.links()){
            kamera.schwenkeHorizontal(-5); // Links
        }
        if (tastatur.rechts()){
            kamera.schwenkeHorizontal(5); // Rechts
        }
        if (tastatur.oben()){
            kamera.schwenkeVertikal(5); // Oben
        }
        if (tastatur.unten()){
            kamera.schwenkeVertikal(-5); // Unten
        }
        if (tastatur.tab()) {
            option++; // Nächste Perspektive 
            if (option == 2) {
                option = 0;
            }
            switch (option) {
                case 0:
                    kamera.setzePosition(0, 100, -325);
                    kamera.setzeBlickpunkt(0, 0, 0);
                    break;
                case 1:
                    kamera.setzePosition(0, 100, 10);
                    kamera.setzeBlickpunkt(0, 5, 175);
                    break;
                case 2:
                    //kamera.setzePosition();
                    break;
            }
            Sys.warte(100); // Kurze Pause
        }
        Sys.warte(5); // Kurze Pause
    }

    public void setzePosition(int pX, int pY, int pZ){
        kamera.setzePosition(pX, pY, pZ);
    }

    public void setzeBlickpunkt(int pX, int pY, int pZ){
        kamera.setzeBlickpunkt(pX, pY, pZ);
    }
}