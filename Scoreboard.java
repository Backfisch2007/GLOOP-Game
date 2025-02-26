import GLOOP.*;

public class Scoreboard {
    private GLTafel spieler1Tafel; // Tafel zur Anzeige von "Spieler 1"
    private GLTafel spieler2Tafel; // Tafel zur Anzeige von "Spieler 2"
    private GLTafel spieler1PunkteTafel; // Tafel zur Anzeige der Punkte von Spieler 1
    private GLTafel spieler2PunkteTafel; // Tafel zur Anzeige der Punkte von Spieler 2
    private GLTafel spieler1VersuchTafel; // Tafel für den Versuch von Spieler 1
    private GLTafel spieler2VersuchTafel; // Tafel für den Versuch von Spieler 2

    private int spieler1Punkte; // Speichert die Punktzahl von Spieler 1
    private int spieler2Punkte; // Speichert die Punktzahl von Spieler 2

    public Scoreboard() {
        // Position des Quaders hinter der Bahn (als Referenzpunkt für die Tafeln)
        double quaderX = 0; // X-Position (Mitte der Bahn)
        double quaderY = 40; // Y-Position (Höhe über dem Boden)
        double quaderZ = 250; // Z-Position (hinter der Bahn)

        // Erstellt die Tafel für Spieler 1
        spieler1Tafel = new GLTafel(quaderX + 100, quaderY, quaderZ + 3, 10, 10); // Position rechts vom Quader
        spieler1Tafel.setzeText("Spieler 1:", 16); // Setzt den Text "Spieler 1:" mit Schriftgröße 16
        spieler1Tafel.setzeTextfarbe(0, 0, 0); // Setzt die Textfarbe auf Schwarz (RGB: 0, 0, 0)
        spieler1Tafel.setzeDrehung(180, 0, 180); // Dreht die Tafel um 180 Grad um X- und Z-Achse für Lesbarkeit

        // Erstellt die Tafel für Spieler 2
        spieler2Tafel = new GLTafel(quaderX - 100, quaderY, quaderZ + 3, 10, 10); // Position links vom Quader
        spieler2Tafel.setzeText("Spieler 2:", 16); // Setzt den Text "Spieler 2:" mit Schriftgröße 16
        spieler2Tafel.setzeTextfarbe(0, 0, 0); // Setzt die Textfarbe auf Schwarz (RGB: 0, 0, 0)
        spieler2Tafel.setzeDrehung(180, 0, 180); // Dreht die Tafel um 180 Grad um X- und Z-Achse für Lesbarkeit

        // Position für die Punktetafeln unterhalb der Spieler-Tafeln
        double punkteTafelY = quaderY - 20; // Y-Position unterhalb der Spieler-Tafeln

        // Erstellt die Punktetafel für Spieler 1
        spieler1PunkteTafel = new GLTafel(quaderX + 100, punkteTafelY, quaderZ + 3, 10, 10); // Position rechts
        spieler1PunkteTafel.setzeText("Punkte: 0", 16); // Setzt den Anfangstext "Punkte: 0" mit Schriftgröße 16
        spieler1PunkteTafel.setzeTextfarbe(0, 0, 0); // Setzt die Textfarbe auf Schwarz (RGB: 0, 0, 0)
        spieler1PunkteTafel.setzeDrehung(180, 0, 180); // Dreht die Tafel um 180 Grad um X- und Z-Achse

        // Erstellt die Punktetafel für Spieler 2
        spieler2PunkteTafel = new GLTafel(quaderX - 100, punkteTafelY, quaderZ + 3, 10, 10); // Position links
        spieler2PunkteTafel.setzeText("Punkte: 0", 16); // Setzt den Anfangstext "Punkte: 0" mit Schriftgröße 16
        spieler2PunkteTafel.setzeTextfarbe(0, 0, 0); // Setzt die Textfarbe auf Schwarz (RGB: 0, 0, 0)
        spieler2PunkteTafel.setzeDrehung(180, 0, 180); // Dreht die Tafel um 180 Grad um X- und Z-Achse

        // Initialisiert die Punktzahlen auf 0
        spieler1Punkte = 0; // Startwert für Spieler 1
        spieler2Punkte = 0; // Startwert für Spieler 2
    }

    // Methode zur Erhöhung der Punkte für Spieler 1
    public void erhoeheSpieler1Punkte() {
        spieler1Punkte++; // Erhöht die Punktzahl von Spieler 1 um 1
        spieler1PunkteTafel.setzeText(Integer.toString(spieler1Punkte), 16); // Aktualisiert die Anzeige mit der neuen Punktzahl
    }

    // Methode zur Erhöhung der Punkte für Spieler 2
    public void erhoeheSpieler2Punkte() {
        spieler2Punkte++; // Erhöht die Punktzahl von Spieler 2 um 1
        spieler2PunkteTafel.setzeText(Integer.toString(spieler2Punkte), 16); // Aktualisiert die Anzeige mit der neuen Punktzahl
    }

    // Methode zum Zurücksetzen der Punkte beider Spieler
    public void zuruecksetzenPunkte() {
        spieler1Punkte = 0; // Setzt die Punkte von Spieler 1 auf 0
        spieler2Punkte = 0; // Setzt die Punkte von Spieler 2 auf 0
        spieler1PunkteTafel.setzeText("0", 16); // Aktualisiert die Anzeige für Spieler 1 auf "0"
        spieler2PunkteTafel.setzeText("0", 16); // Aktualisiert die Anzeige für Spieler 2 auf "0"
    }
    
    // Methode zum Anzeigen des aktuellen Spielers mit "(aktuell)" im Text
    public void setzeAktuellerSpieler(int spieler) {
        if (spieler == 1) { // Wenn Spieler 1 aktuell ist
            spieler1Tafel.setzeText("Spieler 1 (aktuell)", 16); // Fügt "(aktuell)" zum Text von Spieler 1 hinzu
            spieler2Tafel.setzeText("Spieler 2", 16); // Entfernt "(aktuell)" bei Spieler 2
        } else { // Wenn Spieler 2 aktuell ist
            spieler1Tafel.setzeText("Spieler 1", 16); // Entfernt "(aktuell)" bei Spieler 1
            spieler2Tafel.setzeText("Spieler 2 (aktuell)", 16); // Fügt "(aktuell)" zum Text von Spieler 2 hinzu
        }
    }

    // Getter für die Punkte von Spieler 1
    public int getSpieler1Punkte() {
        return spieler1Punkte; // Liefert die aktuelle Punktzahl von Spieler 1
    }

    // Getter für die Punkte von Spieler 2
    public int getSpieler2Punkte() {
        return spieler2Punkte; // Liefert die aktuelle Punktzahl von Spieler 2
    }

    // Setter für die Punkte von Spieler 1
    public void setSpieler1Punkte(int punkte) {
        spieler1Punkte = punkte; // Setzt die Punktzahl von Spieler 1
        spieler1PunkteTafel.setzeText(Integer.toString(spieler1Punkte), 16); // Aktualisiert die Anzeige
    }

    // Setter für die Punkte von Spieler 2
    public void setSpieler2Punkte(int punkte) {
        spieler2Punkte = punkte; // Setzt die Punktzahl von Spieler 2
        spieler2PunkteTafel.setzeText(Integer.toString(spieler2Punkte), 16); // Aktualisiert die Anzeige
    }
}