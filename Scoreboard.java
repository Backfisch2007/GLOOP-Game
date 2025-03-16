import GLOOP.*;

/**
 * Scoreboard zur Anzeige von Punkten, Tastenerklärung und des aktuellen Spielers.
 */
public class Scoreboard {
    private GLTafel spieler1Tafel;
    private GLTafel spieler2Tafel;
    private GLTafel spieler1PunkteTafel;
    private GLTafel spieler2PunkteTafel;
    private GLTafel tastenErklaerungTafel;

    private int spieler1Punkte;
    private int spieler2Punkte;

    /**
     * Konstruktor für das Scoreboard. Erstellt Tafeln zur Anzeige der Punkte und des aktuellen Spielers.
     */
    public Scoreboard() {
        double quaderX = 0;
        double quaderY = 40;
        double quaderZ = 250;

        // Erstellt die Tafel für Spieler 1
        spieler1Tafel = new GLTafel(quaderX + 100, quaderY, quaderZ + 3, 10, 10); // Position rechts vom Quader
        spieler1Tafel.setzeText("Spieler 1:", 16);
        spieler1Tafel.setzeTextfarbe(0, 0, 0);
        spieler1Tafel.setzeDrehung(180, 0, 180);

        // Erstellt die Tafel für Spieler 2
        spieler2Tafel = new GLTafel(quaderX - 100, quaderY, quaderZ + 3, 10, 10); // Position links vom Quader
        spieler2Tafel.setzeText("Spieler 2:", 16);
        spieler2Tafel.setzeTextfarbe(0, 0, 0);
        spieler2Tafel.setzeDrehung(180, 0, 180);

        // Position für die Punktetafeln unter den Spieler-Tafeln
        double punkteTafelY = quaderY - 20;

        // Erstellt die Punktetafel für Spieler 1
        spieler1PunkteTafel = new GLTafel(quaderX + 100, punkteTafelY, quaderZ + 3, 10, 10); // Position rechts
        spieler1PunkteTafel.setzeText("Punkte: 0", 16);
        spieler1PunkteTafel.setzeTextfarbe(0, 0, 0);
        spieler1PunkteTafel.setzeDrehung(180, 0, 180);

        // Erstellt die Punktetafel für Spieler 2
        spieler2PunkteTafel = new GLTafel(quaderX - 100, punkteTafelY, quaderZ + 3, 10, 10); // Position links
        spieler2PunkteTafel.setzeText("Punkte: 0", 16);
        spieler2PunkteTafel.setzeTextfarbe(0, 0, 0);
        spieler2PunkteTafel.setzeDrehung(180, 0, 180);

        // Initialisiert die Punktzahlen auf 0
        spieler1Punkte = 0;
        spieler2Punkte = 0;

        // Erstellt die Tafel zur Erklärung der Tastenfunktionen
        tastenErklaerungTafel = new GLTafel(quaderX, quaderY + 50, quaderZ + 3, 200, 30); // Position über dem Scoreboard
        tastenErklaerungTafel.setzeText("Tasten: u/o = Winkel, j/l = Bewegung, Enter = Stoß, Tab = Kameraposition", 12);
        tastenErklaerungTafel.setzeTextfarbe(0, 0, 0);
        tastenErklaerungTafel.setzeDrehung(180, 0, 180);
        
        // Erstellt die Tafel zur Erklärung der Kamera Tastenfunktionen
        tastenErklaerungTafel = new GLTafel(quaderX, quaderY + 100, quaderZ + 3, 200, 30); // Position über dem Scoreboard
        tastenErklaerungTafel.setzeText("Kamerasteuerung: w, a, s, d, q/e für Hoch/Runter (Nur wenn das Spiel nicht gestartet ist)", 12);
        tastenErklaerungTafel.setzeTextfarbe(0, 0, 0);
        tastenErklaerungTafel.setzeDrehung(180, 0, 180);
    }

    /**
     * Erhöht die Punkte von Spieler 1 um 1 und aktualisiert die Anzeige.
     */
    public void erhoeheSpieler1Punkte() {
        spieler1Punkte++; // Erhöht die Punktzahl von Spieler 1
        spieler1PunkteTafel.setzeText("Punkte: " + spieler1Punkte, 16); // Aktualisiert die Anzeige mit der neuen Punktzahl
    }

    /**
     * Erhöht die Punkte von Spieler 2 um 1 und aktualisiert die Anzeige.
     */
    public void erhoeheSpieler2Punkte() {
        spieler2Punkte++; // Erhöht die Punktzahl von Spieler 2
        spieler2PunkteTafel.setzeText("Punkte: " + spieler2Punkte, 16); // Aktualisiert die Anzeige mit der neuen Punktzahl
    }

    /**
     * Setzt die Punkte beider Spieler zurück auf 0 und aktualisiert die Anzeige.
     */
    public void zuruecksetzenPunkte() {
        spieler1Punkte = 0; // Setzt die Punkte von Spieler 1 auf 0
        spieler2Punkte = 0; // Setzt die Punkte von Spieler 2 auf 0
        spieler1PunkteTafel.setzeText("Punkte: 0", 16); // Aktualisiert die Anzeige für Spieler 1 auf "0"
        spieler2PunkteTafel.setzeText("Punkte: 0", 16); // Aktualisiert die Anzeige für Spieler 2 auf "0"
    }

    /**
     * Markiert den aktuellen Spieler auf dem Scoreboard.
     * @param spieler Der aktuelle Spieler (1 oder 2)
     */
    public void setzeAktuellerSpieler(int spieler) {
        if (spieler == 1) {
            spieler1Tafel.setzeText("Spieler 1 (aktuell)", 16); // Fügt "(aktuell)" zum Text von Spieler 1 hinzu
            spieler2Tafel.setzeText("Spieler 2", 16); // Entfernt "(aktuell)" bei Spieler 2
        } else {
            spieler1Tafel.setzeText("Spieler 1", 16); // Entfernt "(aktuell)" bei Spieler 1
            spieler2Tafel.setzeText("Spieler 2 (aktuell)", 16); // Fügt "(aktuell)" zum Text von Spieler 2 hinzu
        }
    }

    /**
     * Gibt die Punkte von Spieler 1 zurück.
     * @return Punkte von Spieler 1
     */
    public int getSpieler1Punkte() {
        return spieler1Punkte;
    }

    /**
     * Gibt die Punkte von Spieler 2 zurück.
     * @return Punkte von Spieler 2
     */
    public int getSpieler2Punkte() {
        return spieler2Punkte;
    }

    /**
     * Setzt die Punkte von Spieler 1 manuell.
     * @param punkte Die neuen Punkte von Spieler 1
     */
    public void setSpieler1Punkte(int punkte) {
        spieler1Punkte = punkte;
        spieler1PunkteTafel.setzeText("Punkte: " + spieler1Punkte, 16);
    }

    /**
     * Setzt die Punkte von Spieler 2 manuell.
     * @param punkte Die neuen Punkte von Spieler 2
     */
    public void setSpieler2Punkte(int punkte) {
        spieler2Punkte = punkte;
        spieler2PunkteTafel.setzeText("Punkte: " + spieler2Punkte, 16);
    }
}