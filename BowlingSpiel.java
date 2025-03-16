import GLOOP.*;

/**
 * Die Hauptklasse des Bowling-Spiels. Verwaltet Spielumgebung, Pins, Ball und die Spielregeln.
 */
public class BowlingSpiel {
    private GLKamera kamera; // Kamera
    private GLQuader bahn; // Bowlingbahn als Quader
    private GLLicht licht; // Erste Lichtquelle
    private GLLicht licht2; // Zweite Lichtquelle
    private GLHimmel himmel; // Himmel
    private GLTastatur input; // Tastatureingabe
    private KameraSteuerung steuerung; // Kamerasteuerung
    private Ball ball; // Bowlingball
    private GLQuader[] kollisionsQuader; // Unsichtbare Quader für die Kollisionserkennung der Pins
    private BowlingPin[] pins; // Array mit den 10 Bowling-Pins
    private Scoreboard scoreboard; // Anzeigetafel für Punkte
    private int aktuellerSpieler; // Speichert welcher Spieler gerade dran ist (1 oder 2)
    private int versucheSpieler1; // Zählt die Versuche von Spieler 1 in der aktuellen Runde
    private int versucheSpieler2; // Zählt die Versuche von Spieler 2 in der aktuellen Runde
    private int rundeSpieler1; // Runde für Spieler 1
    private int rundeSpieler2; // Runde für Spieler 2
    private boolean spielBeendet; // Guckt, ob das Spiel vorbei ist

    private double quaderBreite = 5; // Breite der Kollisionsquader
    private double quaderHoehe = 25; // Höhe der Kollisionsquader
    private double quaderTiefe = 5; // Tiefe der Kollisionsquader

    /**
     * Konstruktor für das Bowling-Spiel. Initialisiert die Bahn, den Ball, die Pins und das Scoreboard.
     */
    public BowlingSpiel() {
        himmel = new GLHimmel("Hintergrund Bowling.png"); // Hintergrund
        input = new GLTastatur();

        licht = new GLLicht(0, 100, -325); // Licht 1: Über dem Start der Bahn
        licht2 = new GLLicht(0, 100, 125); // Licht 2: etwas weiter hinten für gute Beleuchtung
        licht.setzeAbschwaechung(0.5); // dimmt das Licht etwas
        licht2.setzeAbschwaechung(0.5);

        ball = new Ball(0, -200); // Erstellt Ball

        steuerung = new KameraSteuerung(); // Erstellt Kamerasteuerung
        steuerung.setzePosition(0, 100, 10); // Setzt Kamera über Bahn
        steuerung.setzeBlickpunkt(0, 5, 175); // Blickpunkt auf die Pins

        bahn = new GLQuader(0, -5, 0, 50, 5, 400); // Erstellt die Bahn
        bahn.setzeFarbe(255, 255, 255); // Setzt die Farbe auf Weiß, damit die Textur hell genug ist
        bahn.setzeTextur("wood_floor_diff_4k.jpg"); // Fügt eine Holztextur zur Bahn hinzu

        scoreboard = new Scoreboard(); // Erstellt die Anzeigetafel

        kollisionsQuader = new GLQuader[10]; // Array für 10 Kollisionsquader
        // Positionen der Pins in X- und Z-Richtung
        double[] pinXPositionen = { -6.4, 6.4, -19.2, 19.2, 0, -12.8, 12.8, -6.4, 6.4, 0 }; // X-Positionen der Pins
        double[] pinZPositionen = { 185, 185, 185, 185, 175, 175, 175, 165, 165, 155 }; // Z-Positionen der Pins

        aktuellerSpieler = 1; // Startet mit Spieler 1
        scoreboard.setzeAktuellerSpieler(1); // Markiert Spieler 1 als aktuell auf der Anzeigetafel
        versucheSpieler1 = 0; // Versuche der Spieler auf 0 gesetzt
        versucheSpieler2 = 0;
        rundeSpieler1 = 1; // Runden auf 1 gesetzt
        rundeSpieler2 = 1;
        spielBeendet = false;

        pins = new BowlingPin[10]; // Array für BowlingPins
        for (int i = 0; i < 10; i++) { // Schleife zum Erstellen der Pins und Kollisionsquader
            pins[i] = new BowlingPin(pinXPositionen[i], pinZPositionen[i], i); // Erstellt jeden Pin an seiner Position
            kollisionsQuader[i] = new GLQuader(pinXPositionen[i], 0, pinZPositionen[i], quaderBreite, quaderHoehe, quaderTiefe); // Erstellt Kollisionsquader
            kollisionsQuader[i].setzeSichtbarkeit(false); // Macht die Kollisionsquader unsichtbar
        }

        while (!input.esc() && !spielBeendet) { // Läuft, bis ESC gedrückt wird oder das Spiel beendet ist
            steuerung.steuerungsschleife(input); // Aktualisiert die Kamerasteuerung

            // Winkel einstellen mit Tasten (vor dem Stoß)
            if (input.istGedrueckt('u')) {
                ball.setzeWinkel(ball.gibWinkel() + 2); // Erhöht den Winkel des Balls um 2 Grad nach links 
                System.out.println("Winkel: " + ball.gibWinkel()); // Gibt den aktuellen Winkel in der Konsole aus
            }
            if (input.istGedrueckt('o')) {
                ball.setzeWinkel(ball.gibWinkel() - 2); // Verringert den Winkel des Balls um 2 Grad nach rechts
                System.out.println("Winkel: " + ball.gibWinkel()); // Gibt den aktuellen Winkel in der Konsole aus
            }

            // Bewegung der Kugel vor dem Stoß
            if (input.istGedrueckt('j')) {
                ball.bewege(2.5, 0); // Bewegt den Ball 2.5 Einheiten nach links
            }
            if (input.istGedrueckt('l')) {
                ball.bewege(-2.5, 0); // Bewegt den Ball 2.5 Einheiten nach rechts
            }

            // Stoßen der Kugel
            if (input.enter()) {
                if (aktuellerSpieler == 1) {
                    versucheSpieler1++; // Erhöht die Anzahl der Versuche von Spieler 1
                    System.out.println("Spieler 1, Runde " + rundeSpieler1 + ", Versuch " + versucheSpieler1);
                } else {
                    versucheSpieler2++; // Erhöht die Anzahl der Versuche von Spieler 2
                    System.out.println("Spieler 2, Runde " + rundeSpieler2 + ", Versuch " + versucheSpieler2);
                }

                while (ball.gibZ() < 200) { // Solange der Ball auf der Bahn ist
                    ball.bewege(0, 8);
                    Sys.warte(40);

                    // Kollisionserkennung für alle Pins
                    for (int i = 0; i < 10; i++) { // Schleife über alle 10 Pins
                        if (kollisionErkannt(ball, kollisionsQuader[i])) { // Wenn der Ball den Kollisionsquader trifft
                            System.out.println("Kollision mit Pin " + (i + 1) + " erkannt!");
                            pins[i].starteUmwerfen(scoreboard, aktuellerSpieler); // Startet die Umwerf-Animation des Pins
                        }
                    }

                    kollision(); // Prüft Kollisionen zwischen Pins
                    steuerung.steuerungsschleife(input); // Aktualisiert die Kamerasteuerung während des Stoßes

                    for (BowlingPin pin : pins) { // Schleife über alle Pins
                        pin.aktualisiere(); // Aktualisiert die Animation der umfallenden Pins
                    }
                }

                // Überbrückung der Wartezeit nach dem Stoß
                for (int i = 0; i < 100; i++) { // 100 Durchläufe
                    Sys.warte(10); // Wartet 10 Millisekunden pro Durchlauf
                    for (BowlingPin pin : pins) { // Schleife über alle Pins
                        pin.aktualisiere(); // Führt die Umwerf-Animation weiter aus
                    }
                }

                ball.setzePosition(0, 5, -200); // Setzt den Ball zurück an die Startposition
                ball.setzeWinkel(0); // Setzt den Winkel des Balls zurück auf 0 Grad

                // Spielerwechsel und Rundenverwaltung nach zwei Versuchen
                if (aktuellerSpieler == 1 && versucheSpieler1 >= 2) { // Wenn Spieler 1 zwei Versuche hatte
                    aktuellerSpieler = 2; // Wechselt zu Spieler 2
                    versucheSpieler1 = 0; // Setzt die Versuche von Spieler 1 zurück
                    resetPins(); // Setzt alle Pins zurück in ihre Ausgangsposition
                    rundeSpieler1++; // Erhöht den Runden-Zähler für Spieler 1
                    scoreboard.setzeAktuellerSpieler(2); // Markiert Spieler 2 als aktuell auf der Anzeigetafel
                    System.out.println("Spieler 2 ist jetzt an der Reihe. Runde: " + rundeSpieler2); // Ausgabe in der Konsole
                } else if (aktuellerSpieler == 2 && versucheSpieler2 >= 2) { // Wenn Spieler 2 zwei Versuche hatte
                    aktuellerSpieler = 1; // Wechselt zu Spieler 1
                    versucheSpieler2 = 0; // Setzt die Versuche von Spieler 2 zurück
                    resetPins(); // Setzt alle Pins zurück in ihre Ausgangsposition
                    rundeSpieler2++; // Erhöht den Runden-Zähler für Spieler 2
                    scoreboard.setzeAktuellerSpieler(1); // Markiert Spieler 1 als aktuell auf der Anzeigetafel
                    System.out.println("Spieler 1 ist jetzt an der Reihe. Runde: " + rundeSpieler1); // Ausgabe in der Konsole

                    // Prüft, ob das Spiel beendet ist
                    if (rundeSpieler1 > 10 && rundeSpieler2 > 10) { // Wenn beide Spieler 10 Runden gespielt haben
                        spielBeendet = true; // Markiert das Spiel als beendet
                        zeigeSpielende(); // Zeigt das Spielende mit Gewinner an
                    }
                }
            }

            for (BowlingPin pin : pins) { // Schleife über alle Pins
                pin.aktualisiere(); // Aktualisiert die Animation der Pins (z. B. wenn sie noch fallen)
            }
            reset();
            komplettreset();
            Sys.warte(10);
        }
        Sys.beenden();
    }

    /**
     * Setzt alle Pins zurück in ihre Ausgangsposition.
     */
    private void resetPins() {
        for (BowlingPin pin : pins) { // Schleife über alle Pins
            pin.zuruecksetzen(); // Setzt jeden Pin auf seine Ausgangsposition zurück
        }
    }

    /**
     * Zeigt das Spielende an und gibt den Gewinner oder ein Unentschieden aus.
     */
    private void zeigeSpielende() {
        GLTafel endTafel = new GLTafel(0, 50, 0, 100, 50); // Erstellt eine Tafel in der Mitte der Szene
        endTafel.setzeTextfarbe(0, 0, 0); // Setzt die Textfarbe auf Schwarz
        endTafel.setzeDrehung(180, 0, 180); // Dreht die Tafel für Lesbarkeit

        // Ermittelt die Punkte von beiden Spielern über Getter
        int punkte1 = scoreboard.getSpieler1Punkte(); // Punkte von Spieler 1
        int punkte2 = scoreboard.getSpieler2Punkte(); // Punkte von Spieler 2

        if (punkte1 > punkte2) { // Wenn Spieler 1 mehr Punkte hat
            endTafel.setzeText("Spieler 1 gewinnt! Punkte: " + punkte1 + " vs " + punkte2, 20); // Zeigt Spieler 1 als Gewinner
        } else if (punkte2 > punkte1) { // Wenn Spieler 2 mehr Punkte hat
            endTafel.setzeText("Spieler 2 gewinnt! Punkte: " + punkte2 + " vs " + punkte1, 20); // Zeigt Spieler 2 als Gewinner
        } else { // Bei Gleichstand
            endTafel.setzeText("Unentschieden! Punkte: " + punkte1 + " vs " + punkte2, 20); // Zeigt Unentschieden
        }

        // Wartet 5 Sekunden, damit der Spieler das Ergebnis sehen kann
        Sys.warte(5000);
    }

    /**
     * Überprüft, ob eine Kollision zwischen dem Ball und einem Quader stattgefunden hat.
     * @param ball Der Ball
     * @param quader Der Quader
     * @return true, wenn eine Kollision erkannt wurde, sonst false
     */
    private boolean kollisionErkannt(Ball ball, GLQuader quader) {
        // Position und Radius des Balls
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
        double entfernung = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ); // Gesamtentfernung

        // Kollision wenn Entfernung <= Radius
        return entfernung <= radius; // True, wenn der Ball den Quader berührt
    }

    /**
     * Überprüft, ob eine Kollision zwischen zwei Pins stattgefunden hat.
     * @param pin1 Der erste Pin
     * @param pin2 Der zweite Pin
     * @return true, wenn eine Kollision erkannt wurde, sonst false
     */
    private boolean kollisionErkannt(BowlingPin pin1, BowlingPin pin2) {
        // Positionen der Pins
        double pin1X = pin1.gibX();
        double pin1Z = pin1.gibZ();
        double pin2X = pin2.gibX();
        double pin2Z = pin2.gibZ();

        // Abstand zwischen den Pins
        double deltaX = pin1X - pin2X;
        double deltaZ = pin1Z - pin2Z;
        double entfernung = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ); // Gesamtentfernung zwischen den Pins

        // Kollision, wenn der Abstand kleiner als 5 ist
        return entfernung < 5; // True, wenn die Pins sich berühren
    }

    /**
     * Behandelt Kollisionen zwischen Pins und startet gegebenenfalls die Umwerf-Animation.
     */
    private void kollision() {
        // Kollisionserkennung zwischen Pins
        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                if (kollisionErkannt(pins[i], pins[j])) { // Wenn zwei Pins kollidieren
                    if (pins[i].istUmgeworfen() && !pins[j].istUmgeworfen()) { // Wenn Pin i umgeworfen ist, Pin j aber nicht
                        pins[j].starteUmwerfen(scoreboard, aktuellerSpieler); // Startet das Umwerfen von Pin j
                    } else if (pins[j].istUmgeworfen() && !pins[i].istUmgeworfen()) { // Wenn Pin j umgeworfen ist, Pin i aber nicht
                        pins[i].starteUmwerfen(scoreboard, aktuellerSpieler); // Startet das Umwerfen von Pin i
                    }
                }
            }
        }
    }

    /**
     * Setzt das Spielfeld zurück, wenn die Taste 'r' gedrückt wird.
     */
    private void reset() {
        // Pins, Ball und Kamera zurücksetzen mit der Taste r
        if (input.istGedrueckt('r')) {
            for (BowlingPin pin : pins) {
                pin.zuruecksetzen();
            }
            ball.setzePosition(0, 5, -200); // Setzt den Ball auf die Startposition zurück
            ball.setzeWinkel(0); // Setzt den Winkel des Balls auf 0 Grad zurück
            steuerung.setzePosition(0, 100, -325); // Setzt die Kamera auf die Standardposition zurück
            steuerung.setzeBlickpunkt(0, 0, 0); // Setzt den Blickpunkt der Kamera auf die Mitte der Bahn
            System.out.println("Alle Pins, Ball und Kamera wurden zurückgesetzt.");
            Sys.warte(1000);
        }
    }

    /**
     * Setzt das komplette Spiel zurück, wenn die Taste 't' gedrückt wird
     */
    private void komplettreset() {
        // Pins, Ball, Kamera und Scoreboard zurücksetzen mit der Taste t
        if (input.istGedrueckt('t')) {
            for (BowlingPin pin : pins) {
                pin.zuruecksetzen();
            }
            ball.setzePosition(0, 5, -200); // Setzt den Ball auf die Startposition zurück
            ball.setzeWinkel(0); // Setzt den Winkel des Balls auf 0 Grad zurück
            steuerung.setzePosition(0, 100, -325); // Setzt die Kamera auf die Standardposition zurück
            steuerung.setzeBlickpunkt(0, 0, 0); // Setzt den Blickpunkt der Kamera auf die Mitte der Bahn
            scoreboard.zuruecksetzenPunkte(); // Scoreboard zurücksetzen
            System.out.println("Alle Pins, Ball, Kamera und Scoreboard wurden zurückgesetzt.");
            Sys.warte(1000);
        }
    }
}