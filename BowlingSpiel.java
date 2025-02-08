/**
import GLOOP.*;
public class BowlingSpiel {
    private GLKamera kamera;
    private GLQuader bahn;
    private GLLicht licht;
    private GLLicht licht2;
    private GLHimmel himmel;
    private GLTastatur input;
    private KameraSteuerung steuerung;
    private Ball ball;
    private GLQuader[] kollisionsQuader; // Array von Quadern für Kollisionserkennung

    // Abmessungen Kollisionsquader
    private double quaderBreite = 5;  // Breite Quader
    private double quaderHoehe = 16;  // Höhe Quader
    private double quaderTiefe = 5;   // Tiefe Quader

    //private BowlingPin[] pins; // Array für alle 10 Pins !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public BowlingSpiel() {
        himmel = new GLHimmel("rosendal_plains_2.jpg"); // Himmel
        input = new GLTastatur();

        licht = new GLLicht(0, 100, -325); // Lichtquelle 1
        licht2 = new GLLicht(0, 100, 125); // Lichtquelle 2

        licht.setzeAbschwaechung(0.5); // Lichtquelle 1
        licht2.setzeAbschwaechung(0.5); // Lichtquelle 2

        ball = new Ball(0, -200); // Ball

        steuerung = new KameraSteuerung(); //Kamera2
        steuerung.setzePosition(0, 100, 10); // Kamera2
        steuerung.setzeBlickpunkt(0, 5, 175); //Kamera2

        bahn = new GLQuader(0, -5, 0, 50, 5, 400); // Flacher Quader als Bahn
        bahn.setzeFarbe(255, 255, 255); // Weiße Farbe damit Textur hell genug 
        bahn.setzeTextur("wood_floor_diff_4k.jpg");

        BowlingPin pin = new BowlingPin(0, -100);

        // Array von Quadern für die Kollisionserkennung um alle 10 Pins
        kollisionsQuader = new GLQuader[10];
        double[] pinXPositionen = {-19.2, -12.8, -6.4, 0, 6.4, 12.8, 19.2, -6.4, 6.4, 0}; // X-Positionen der Pins zur Erstellung des Unsichtbaren Quaders
        double[] pinZPositionen = {185, 175, 185, 175, 185, 175, 185, 165, 165, 155}; // Z-Positionen der Pins zur Erstellung des Unsichtbaren Quaders

        for (int i = 0; i < 10; i++) {
            kollisionsQuader[i] = new GLQuader(pinXPositionen[i], 0, pinZPositionen[i], quaderBreite, quaderHoehe, quaderTiefe); // Erstellt Quader
            //kollisionsQuader[i].setzeSichtbarkeit(false); // Quader unsichtbar machen
        }

        while (!input.esc()) {
            steuerung.steuerungsschleife(input);

            // Bewegung der Kugel
            if (input.istGedrueckt('i')) {
                ball.bewege(0, 2.5); // Vorwärts
            }
            if (input.istGedrueckt('k')) {
                ball.bewege(0, -2.5); // Rückwärts
            }
            if (input.istGedrueckt('j')) {
                ball.bewege(2.5, 0); // Links
            }
            if (input.istGedrueckt('l')) {
                ball.bewege(-2.5, 0); // Rechts
            }

            // Kollisionserkennung für alle Pins
            for (int i = 0; i < 10; i++) {
                if (kollisionErkannt(ball, kollisionsQuader[i])) {
                    System.out.println("Kollision mit Pin " + (i + 1) + " erkannt!");
                    // Hier kannst du weitere Aktionen ausführen, z.B. den Pin umwerfen
                }
            }

            Sys.warte(10);
        }

        // Kollisionserkennung
        public boolean kollisionErkannt(Ball ball, GLQuader quader) {
            double ballX = ball.gibX();
            double ballY = ball.gibY();
            double ballZ = ball.gibZ();
            double radius = ball.gibRadius();

            double quaderX = quader.gibX();
            double quaderY = quader.gibY();
            double quaderZ = quader.gibZ();

            // Halbe Abmessungen des Quaders
            double halbeBreite = quaderBreite / 2;
            double halbeHoehe = quaderHoehe / 2;
            double halbeTiefe = quaderTiefe / 2;

            // Nächstgelegener Punkt auf dem Quader zur Kugel
            double naechsterX = Math.max(quaderX - halbeBreite, Math.min(ballX, quaderX + halbeBreite));
            double naechsterY = Math.max(quaderY - halbeHoehe, Math.min(ballY, quaderY + halbeHoehe));
            double naechsterZ = Math.max(quaderZ - halbeTiefe, Math.min(ballZ, quaderZ + halbeTiefe));

            // Entfernung zwischen Kugelmittelpunkt und nächstgelegenem Punkt
            double deltaX = ballX - naechsterX;
            ouble deltaY = ballY - naechsterY;
            double deltaZ = ballZ - naechsterZ;
            double entfernung = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

            // Kollision, wenn Entfernung <= Radius
            return entfernung <= radius;
        }
    }
}

*/


import GLOOP.*;

public class BowlingSpiel {
    private GLKamera kamera;
    private GLQuader bahn;
    private GLLicht licht;
    private GLLicht licht2;
    private GLHimmel himmel;
    private GLTastatur input;
    private KameraSteuerung steuerung;
    private Ball ball;
    private GLQuader[] kollisionsQuader; // Array von Quadern für Kollisionserkennung
    private BowlingPin[] pins; // Array für alle 10 Pins

    // Abmessungen Kollisionsquader
    private double quaderBreite = 5;  // Breite Quader
    private double quaderHoehe = 16;  // Höhe Quader
    private double quaderTiefe = 5;   // Tiefe Quader

    public BowlingSpiel() {
        himmel = new GLHimmel("rosendal_plains_2.jpg"); // Himmel
        input = new GLTastatur();

        licht = new GLLicht(0, 100, -325); // Lichtquelle 1
        licht2 = new GLLicht(0, 100, 125); // Lichtquelle 2

        licht.setzeAbschwaechung(0.5); // Lichtquelle 1
        licht2.setzeAbschwaechung(0.5); // Lichtquelle 2

        ball = new Ball(0, -200); // Ball

        steuerung = new KameraSteuerung(); // Kamera2
        steuerung.setzePosition(0, 100, 10); // Kamera2
        steuerung.setzeBlickpunkt(0, 5, 175); // Kamera2

        bahn = new GLQuader(0, -5, 0, 50, 5, 400); // Flacher Quader als Bahn
        bahn.setzeFarbe(255, 255, 255); // Weiße Farbe damit Textur hell genug
        bahn.setzeTextur("wood_floor_diff_4k.jpg");

        // Array von Quadern für die Kollisionserkennung um alle 10 Pins
        kollisionsQuader = new GLQuader[10];
        double[] pinXPositionen = {-19.2, -12.8, -6.4, 0, 6.4, 12.8, 19.2, -6.4, 6.4, 0}; // X-Positionen der Pins
        double[] pinZPositionen = {185, 175, 185, 175, 185, 175, 185, 165, 165, 155}; // Z-Positionen der Pins

        pins = new BowlingPin[10]; // Array für alle 10 Pins
        for (int i = 0; i < 10; i++) {
            pins[i] = new BowlingPin(pinXPositionen[i], pinZPositionen[i]);
            kollisionsQuader[i] = new GLQuader(pinXPositionen[i], 0, pinZPositionen[i], quaderBreite, quaderHoehe, quaderTiefe);
            //kollisionsQuader[i].setzeSichtbarkeit(false); // Quader unsichtbar machen
        }

        while (!input.esc()) {
            steuerung.steuerungsschleife(input);

            // Bewegung der Kugel
            if (input.istGedrueckt('i')) {
                ball.bewege(0, 2.5); // Vorwärts
            }
            if (input.istGedrueckt('k')) {
                ball.bewege(0, -2.5); // Rückwärts
            }
            if (input.istGedrueckt('j')) {
                ball.bewege(2.5, 0); // Links
            }
            if (input.istGedrueckt('l')) {
                ball.bewege(-2.5, 0); // Rechts
            }

            // Kollisionserkennung für alle Pins
            for (int i = 0; i < 10; i++) {
                if (kollisionErkannt(ball, kollisionsQuader[i])) {
                    System.out.println("Kollision mit Pin " + (i + 1) + " erkannt!");
                    //pins[i].umwerfen(); // Pin umwerfen
                }
            }

            Sys.warte(10);
        }
    }

    // Methode zur Kollisionserkennung
    private boolean kollisionErkannt(Ball ball, GLQuader quader) {
        double ballX = ball.gibX();
        double ballY = ball.gibY();
        double ballZ = ball.gibZ();
        double radius = ball.gibRadius();

        double quaderX = quader.gibX();
        double quaderY = quader.gibY();
        double quaderZ = quader.gibZ();

        // Halbe Abmessungen des Quaders
        double halbeBreite = quaderBreite / 2;
        double halbeHoehe = quaderHoehe / 2;
        double halbeTiefe = quaderTiefe / 2;

        // Nächstgelegener Punkt auf dem Quader zur Kugel
        double naechsterX = Math.max(quaderX - halbeBreite, Math.min(ballX, quaderX + halbeBreite));
        double naechsterY = Math.max(quaderY - halbeHoehe, Math.min(ballY, quaderY + halbeHoehe));
        double naechsterZ = Math.max(quaderZ - halbeTiefe, Math.min(ballZ, quaderZ + halbeTiefe));

        // Entfernung zwischen Kugelmittelpunkt und nächstgelegenem Punkt
        double deltaX = ballX - naechsterX;
        double deltaY = ballY - naechsterY;
        double deltaZ = ballZ - naechsterZ;
        double entfernung = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

        // Kollision, wenn Entfernung <= Radius
        return entfernung <= radius;
        
        
    }
}