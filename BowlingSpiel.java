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
    private Scoreboard scoreboard; // Scoreboard
    private int aktuellerSpieler; // Spieler 
    private int versucheSpieler1; // Anzahl der Versuche für Spieler 1
    private int versucheSpieler2; // Anzahl der Versuche für Spieler 2

    // Abmessungen Kollisionsquader
    private double quaderBreite = 5; // Breite Quader
    private double quaderHoehe = 25; // Höhe Quader
    private double quaderTiefe = 5; // Tiefe Quader

    public BowlingSpiel() {
        //himmel = new GLHimmel("rosendal_plains_2.jpg"); // Himmel
        input = new GLTastatur();

        licht = new GLLicht(0, 100, -325); // Lichtquelle 1
        licht2 = new GLLicht(0, 100, 125); // Lichtquelle 2

        licht.setzeAbschwaechung(0.5); // Lichtquelle 1 dunkler machen
        licht2.setzeAbschwaechung(0.5); // Lichtquelle 2 dunkler machen

        ball = new Ball(0, -200); // Ball

        steuerung = new KameraSteuerung(); // Kamera2
        steuerung.setzePosition(0, 100, 10); // Kamera2
        steuerung.setzeBlickpunkt(0, 5, 175); // Kamera2

        bahn = new GLQuader(0, -5, 0, 50, 5, 400); // Flacher Quader als Bahn
        bahn.setzeFarbe(255, 255, 255); // Weiße Farbe damit Textur hell genug
        bahn.setzeTextur("wood_floor_diff_4k.jpg"); // Holztextur der Bahn

        scoreboard = new Scoreboard(); // Scoreboard erstellen

        // Array von Quadern für die Kollisionserkennung um alle 10 Pins
        kollisionsQuader = new GLQuader[10];
        double[] pinXPositionen = { -6.4, 6.4, -19.2, 19.2, 0, -12.8, 12.8, -6.4, 6.4, 0 }; // X-Positionen der Pins
        double[] pinZPositionen = { 185, 185, 185, 185, 175, 175, 175, 165, 165, 155 }; // Z-Position der Pins

        aktuellerSpieler = 1; // Startet mit Spieler 1
        scoreboard.setzeAktuellerSpieler(1);
        versucheSpieler1 = 0;
        versucheSpieler2 = 0;

        pins = new BowlingPin[10]; // Array für alle 10 Pins
        for (int i = 0; i < 10; i++) {
            pins[i] = new BowlingPin(pinXPositionen[i], pinZPositionen[i], i);
            kollisionsQuader[i] = new GLQuader(pinXPositionen[i], 0, pinZPositionen[i], quaderBreite, quaderHoehe,
                quaderTiefe);
            kollisionsQuader[i].setzeSichtbarkeit(false); // Quader unsichtbar machen
        }

        while (!input.esc()) {
            steuerung.steuerungsschleife(input);

            // Bewegung der Kugel
            if (input.istGedrueckt('i')) {
                ball.bewege(0, 4.5); // Vorwärts
            }
            if (input.istGedrueckt('k')) {
                ball.bewege(0, -4.5); // Rückwärts
            }
            if (input.istGedrueckt('j')) {
                ball.bewege(2.5, 0); // Links
            }
            if (input.istGedrueckt('l')) {
                ball.bewege(-2.5, 0); // Rechts
            }

            // Stoßen der Kugel
            if (input.enter()){
                // Beim stoßen wird ein Versuch draufgerechnet
                if (aktuellerSpieler == 1) { 
                    versucheSpieler1++;
                } else {
                    versucheSpieler2++;
                }

                while (ball.gibZ() < 200) { // Solange der Ball auf der Bahn ist
                    ball.bewege(0, 8); // Bewegt den Ball in einer Stoßbewegung nach vorne
                    Sys.warte(40);

                    // Kollisionserkennung für alle Pins
                    for (int i = 0; i < 10; i++) {
                        if (kollisionErkannt(ball, kollisionsQuader[i])) {
                            System.out.println("Kollision mit Pin " + (i + 1) + " erkannt!");
                            pins[i].starteUmwerfen(scoreboard, aktuellerSpieler); // Pin umwerfen
                        }
                    }

                    kollision();
                    steuerung.steuerungsschleife(input);

                    // Aktualisiere alle Pins (Animation)
                    for (BowlingPin pin : pins) {
                        pin.aktualisiere();
                    }
                    reset();
                }

                // Überbrückung der Wartezeit von zurücksetzen der Kugel damit Pins trotzdem umfallen
                for (int i = 0; i < 200; i++) {
                    Sys.warte(10); // Warten
                    // Aktualisiere alle Pins (Animation)
                    for (BowlingPin pin : pins) {
                        pin.aktualisiere();
                    }
                }

                ball.setzePosition(0, 5, -200); // Ball zurücksetzen

                // Wechsel des Spielers nach zwei Versuchen
                if (aktuellerSpieler == 1 && versucheSpieler1 >= 2) {
                    aktuellerSpieler = 2;
                    versucheSpieler1 = 0;
                    scoreboard.setzeAktuellerSpieler(2);
                    System.out.println("Spieler 2 ist jetzt an der Reihe.");
                } else if (aktuellerSpieler == 2 && versucheSpieler2 >= 2) {
                    aktuellerSpieler = 1;
                    versucheSpieler2 = 0;
                    scoreboard.setzeAktuellerSpieler(1);
                    System.out.println("Spieler 1 ist jetzt an der Reihe.");
                }
            }

            // Aktualisiere alle Pins (Animation)
            for (BowlingPin pin : pins) {
                pin.aktualisiere();
            }
            reset();
            Sys.warte(10);
        }
        Sys.beenden();
    }

    // Methode zur Kollisionserkennung zwischen Ball und Quader
    private boolean kollisionErkannt(Ball ball, GLQuader quader) {
        // Position und Radius des Balles
        double ballX = ball.gibX();
        double ballY = ball.gibY();
        double ballZ = ball.gibZ();
        double radius = ball.gibRadius();

        // Position des Quaders
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

    // Methode zur Kollisionserkennung zwischen zwei Pins
    private boolean kollisionErkannt(BowlingPin pin1, BowlingPin pin2) {
        // Positionen der Pins
        double pin1X = pin1.gibX();
        double pin1Z = pin1.gibZ();
        double pin2X = pin2.gibX();
        double pin2Z = pin2.gibZ();

        // Abstand zwischen den Pins
        double deltaX = pin1X - pin2X;
        double deltaZ = pin1Z - pin2Z;
        double entfernung = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        // Kollision, wenn der Abstand kleiner als ein bestimmter Wert ist (z.B. 5
        // Einheiten)
        return entfernung < 5;
    }

    private void reset(){
        // Pins, Ball, Kamera und (Scoreboard) zurücksetzen mit der Taste r
        if (input.istGedrueckt('r')) {
            for (BowlingPin pin : pins) {
                pin.zuruecksetzen(); // Pins zurücksetzen
            }
            ball.setzePosition(0, 5, -200); // Ball zurücksetzen
            steuerung.setzePosition(0, 100, -325); // Kamera zurücksetzen
            steuerung.setzeBlickpunkt(0, 0, 0); // Blickpunkt zurücksetzen
            //scoreboard.zuruecksetzenPunkte(); // Scoreboard zurücksetzen
            System.out.println("Alle Pins, Ball, Kamera und Scoreboard wurden zurückgesetzt.");
            Sys.warte(2000); // Kurze Pause
        }
    }

    private void kollision(){
        // Kollisionserkennung zwischen Pins
        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                if (kollisionErkannt(pins[i], pins[j])) {
                    if (pins[i].istUmgeworfen() && !pins[j].istUmgeworfen()) {
                        pins[j].starteUmwerfen(scoreboard, aktuellerSpieler);
                    } else if (pins[j].istUmgeworfen() && !pins[i].istUmgeworfen()) {
                        pins[i].starteUmwerfen(scoreboard, aktuellerSpieler);
                    }
                }
            }
        }
    }
}