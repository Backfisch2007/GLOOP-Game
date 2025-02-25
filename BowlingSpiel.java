import GLOOP.*;

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
    private int versucheSpieler1; // Zählt die Versuche von Spieler 1
    private int versucheSpieler2; // Zählt die Versuche von Spieler 2

    private double quaderBreite = 5; // Breite der Kollisionsquader
    private double quaderHoehe = 25; // Höhe der Kollisionsquader
    private double quaderTiefe = 5; // Tiefe der Kollisionsquader

    public BowlingSpiel() {
        //himmel = new GLHimmel("rosendal_plains_2.jpg"); // Himmel mit Textur (Zieht zu viel FPS)
        himmel = new GLHimmel("Hintergrund Bowling.png"); // Runterskaliert für mehr FPS
        input = new GLTastatur(); // Erstellt ein neues Tastatur-Objekt für Eingaben

        licht = new GLLicht(0, 100, -325); // Licht 1: Position über dem Start der Bahn
        licht2 = new GLLicht(0, 100, 125); // Licht 2: Position weiter hinten für bessere Ausleuchtung
        licht.setzeAbschwaechung(0.5); // Macht das Licht etwas dunkler
        licht2.setzeAbschwaechung(0.5); // Macht das Licht etwas dunkler

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
        versucheSpieler1 = 0; // Startet mit 0 Versuchen für Spieler 1
        versucheSpieler2 = 0; // Startet mit 0 Versuchen für Spieler 2

        pins = new BowlingPin[10]; // Array für BowlingPins
        for (int i = 0; i < 10; i++) { // Schleife zum Erstellen der Pins und Kollisionsquader
            pins[i] = new BowlingPin(pinXPositionen[i], pinZPositionen[i], i); // Erstellt jeden Pin an seiner Position
            kollisionsQuader[i] = new GLQuader(pinXPositionen[i], 0, pinZPositionen[i], quaderBreite, quaderHoehe, quaderTiefe); // Erstellt Kollisionsquader
            kollisionsQuader[i].setzeSichtbarkeit(false); // Macht die Kollisionsquader unsichtbar
        }

        
        while (!input.esc()) { // Läuft, bis die ESC-Taste gedrückt wird
            steuerung.steuerungsschleife(input); // Aktualisiert die Kamerasteuerung

            // Winkel einstellen mit Tasten (vor dem Stoß)
            if (input.istGedrueckt('u')) { // Wenn Taste u gedrückt ist
                ball.setzeWinkel(ball.gibWinkel() + 2); // Erhöht den Winkel des Balls um 2 Grad nach links 
                System.out.println("Winkel: " + ball.gibWinkel()); // Gibt den aktuellen Winkel in der Konsole aus
            }
            if (input.istGedrueckt('o')) { // Wenn Taste o gedrückt ist
                ball.setzeWinkel(ball.gibWinkel() - 2); // Verringert den Winkel des Balls um 2 Grad nach rechts
                System.out.println("Winkel: " + ball.gibWinkel()); // Gibt den aktuellen Winkel in der Konsole aus
            }

            // Bewegung der Kugel vor dem Stoß
            if (input.istGedrueckt('j')) { // Wenn Taste j gedrückt ist
                ball.bewege(2.5, 0); // Bewegt den Ball 2.5 Einheiten nach links
            }
            if (input.istGedrueckt('l')) { // Wenn Taste l gedrückt ist
                ball.bewege(-2.5, 0); // Bewegt den Ball 2.5 Einheiten nach rechts
            }

            // Stoßen der Kugel
            if (input.enter()) { // Wenn die Enter-Taste gedrückt wird
                if (aktuellerSpieler == 1) { // Wenn Spieler 1 dran ist
                    versucheSpieler1++; // Erhöht die Anzahl der Versuche von Spieler 1
                } else { // Wenn Spieler 2 dran ist
                    versucheSpieler2++; // Erhöht die Anzahl der Versuche von Spieler 2
                }

                while (ball.gibZ() < 200) { // Solange der Ball auf der Bahn ist
                    ball.bewege(0, 8); // Bewegt den Ball 8 Einheiten vorwärts
                    Sys.warte(40); // Wartet 40 Millisekunden

                    // Kollisionserkennung für alle Pins
                    for (int i = 0; i < 10; i++) { // Schleife über alle 10 Pins
                        if (kollisionErkannt(ball, kollisionsQuader[i])) { // Wenn der Ball den Kollisionsquader trifft
                            System.out.println("Kollision mit Pin " + (i + 1) + " erkannt!"); // Ausgabe in der Konsole
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

                // Spielerwechsel und Pins zurücksetzen nach zwei Versuchen
                if (aktuellerSpieler == 1 && versucheSpieler1 >= 2) { // Wenn Spieler 1 zwei Versuche hatte
                    aktuellerSpieler = 2; // Wechselt zu Spieler 2
                    versucheSpieler1 = 0; // Setzt die Versuche von Spieler 1 zurück
                    scoreboard.setzeAktuellerSpieler(2); // Markiert Spieler 2 als aktuell auf der Anzeigetafel
                    resetPins(); // Setzt alle Pins zurück in ihre Ausgangsposition
                    System.out.println("Spieler 2 ist jetzt an der Reihe. Pins wurden zurückgesetzt."); // Ausgabe in der Konsole
                } else if (aktuellerSpieler == 2 && versucheSpieler2 >= 2) { // Wenn Spieler 2 zwei Versuche hatte
                    aktuellerSpieler = 1; // Wechselt zu Spieler 1
                    versucheSpieler2 = 0; // Setzt die Versuche von Spieler 2 zurück
                    scoreboard.setzeAktuellerSpieler(1); // Markiert Spieler 1 als aktuell auf der Anzeigetafel
                    resetPins(); // Setzt alle Pins zurück in ihre Ausgangsposition
                    System.out.println("Spieler 1 ist jetzt an der Reihe. Pins wurden zurückgesetzt."); // Ausgabe in der Konsole
                }
            }

            for (BowlingPin pin : pins) { // Schleife über alle Pins
                pin.aktualisiere(); // Aktualisiert die Animation der Pins (z. B. wenn sie noch fallen)
            }
            reset();
            Sys.warte(10); // Wartet 10 Millisekunden
        }
        Sys.beenden();
    }

    // Methode zum Zurücksetzen der Pins
    private void resetPins() {
        for (BowlingPin pin : pins) { // Schleife über alle Pins
            pin.zuruecksetzen(); // Setzt jeden Pin auf seine Ausgangsposition zurück
        }
    }
    
    // Methode zur Kollisionserkennung zwischen Ball und Quader
    private boolean kollisionErkannt(Ball ball, GLQuader quader) {
        // Position und Radius des Balls
        double ballX = ball.gibX(); // X-Position des Balls
        double ballY = ball.gibY(); // Y-Position des Balls
        double ballZ = ball.gibZ(); // Z-Position des Balls
        double radius = ball.gibRadius(); // Radius des Balls

        // Position des Quaders
        double quaderX = quader.gibX(); // X-Position des Quaders
        double quaderY = quader.gibY(); // Y-Position des Quaders
        double quaderZ = quader.gibZ(); // Z-Position des Quaders

        // Halbe Abmessungen des Quaders
        double halbeBreite = quaderBreite / 2; // Halbe Breite des Quaders
        double halbeHoehe = quaderHoehe / 2; // Halbe Höhe des Quaders
        double halbeTiefe = quaderTiefe / 2; // Halbe Tiefe des Quaders

        // Nächstgelegener Punkt auf dem Quader zur Kugel
        double naechsterX = Math.max(quaderX - halbeBreite, Math.min(ballX, quaderX + halbeBreite)); // X-Koordinate des nächsten Punkts
        double naechsterY = Math.max(quaderY - halbeHoehe, Math.min(ballY, quaderY + halbeHoehe)); // Y-Koordinate des nächsten Punkts
        double naechsterZ = Math.max(quaderZ - halbeTiefe, Math.min(ballZ, quaderZ + halbeTiefe)); // Z-Koordinate des nächsten Punkts

        // Entfernung zwischen Kugelmittelpunkt und nächstgelegenem Punkt
        double deltaX = ballX - naechsterX; // Abstand in X-Richtung
        double deltaY = ballY - naechsterY; // Abstand in Y-Richtung
        double deltaZ = ballZ - naechsterZ; // Abstand in Z-Richtung
        double entfernung = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ); // Gesamtentfernung

        // Kollision wenn Entfernung <= Radius
        return entfernung <= radius; // True, wenn der Ball den Quader berührt
    }

    // Methode zur Kollisionserkennung zwischen zwei Pins
    private boolean kollisionErkannt(BowlingPin pin1, BowlingPin pin2) {
        // Positionen der Pins
        double pin1X = pin1.gibX(); // X-Position des ersten Pins
        double pin1Z = pin1.gibZ(); // Z-Position des ersten Pins
        double pin2X = pin2.gibX(); // X-Position des zweiten Pins
        double pin2Z = pin2.gibZ(); // Z-Position des zweiten Pins

        // Abstand zwischen den Pins
        double deltaX = pin1X - pin2X; // Abstand in X-Richtung
        double deltaZ = pin1Z - pin2Z; // Abstand in Z-Richtung
        double entfernung = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ); // Gesamtentfernung zwischen den Pins

        // Kollision, wenn der Abstand kleiner als 5 ist
        return entfernung < 5; // True, wenn die Pins sich berühren
    }

    // Methode zur Behandlung von Kollisionen zwischen Pins
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

    // Methode zum manuellen Zurücksetzen des Spiels
    private void reset() {
        // Pins, Ball, Kamera und (Scoreboard) zurücksetzen mit der Taste r
        if (input.istGedrueckt('r')) { // Wenn die Taste r gedrückt wird
            for (BowlingPin pin : pins) { // Schleife über alle Pins
                pin.zuruecksetzen(); // Setzt jeden Pin zurück
            }
            ball.setzePosition(0, 5, -200); // Setzt den Ball auf die Startposition zurück
            ball.setzeWinkel(0); // Setzt den Winkel des Balls auf 0 Grad zurück
            steuerung.setzePosition(0, 100, -325); // Setzt die Kamera auf die Standardposition zurück
            steuerung.setzeBlickpunkt(0, 0, 0); // Setzt den Blickpunkt der Kamera auf die Mitte der Bahn
            //scoreboard.zuruecksetzenPunkte(); // Scoreboard zurücksetzen
            System.out.println("Alle Pins, Ball, Kamera und Scoreboard wurden zurückgesetzt."); // Ausgabe in der Konsole
            Sys.warte(1000); // Wartet 1 Sekunde
        }
    }
}