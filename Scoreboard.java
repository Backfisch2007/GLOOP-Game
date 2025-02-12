import GLOOP.*;

public class Scoreboard {
    private GLTafel spieler1Tafel; // Tafel für Spieler 1
    private GLTafel spieler2Tafel; // Tafel für Spieler 2
    private GLTafel spieler1PunkteTafel; // Tafel für die Punkte von Spieler 1
    private GLTafel spieler2PunkteTafel; // Tafel für die Punkte von Spieler 2
    private GLTafel spieler1VersuchTafel; // Tafel für den Versuch von Spieler 1
    private GLTafel spieler2VersuchTafel; // Tafel für den Versuch von Spieler 2
    
    private int spieler1Punkte; // Punktzahl für Spieler 1
    private int spieler2Punkte; // Punktzahl für Spieler 2

    public Scoreboard() {
        // Position des Quaders hinter der Bahn
        double quaderX = 0; // X Position
        double quaderY = 40; // Y Position 
        double quaderZ = 250; // Position hinter der Bahn

        // Tafeln für Spieler 1 und Spieler 2 erstellen
        spieler1Tafel = new GLTafel(quaderX + 100, quaderY, quaderZ + 3, 10, 10); // Position rechts
        spieler1Tafel.setzeText("Spieler 1 Punkte:", 16); // Textgröße 16
        spieler1Tafel.setzeTextfarbe(0, 0, 0); // Schwarzer Text
        spieler1Tafel.setzeDrehung(180, 0, 180); // Text um 180 Grad um die X-Achse drehen

        spieler2Tafel = new GLTafel(quaderX - 100, quaderY, quaderZ + 3, 10, 10); // Position links
        spieler2Tafel.setzeText("Spieler 2:", 16); // Textgröße 16
        spieler2Tafel.setzeTextfarbe(0, 0, 0); // Schwarzer Text
        spieler2Tafel.setzeDrehung(180, 0, 180); // Text um 180 Grad um die X-Achse drehen

        // Tafeln für die Punkte von Spieler 1 und Spieler 2 darunter
        double punkteTafelY = quaderY - 20; // Position unterhalb der Spieler-Tafeln
        spieler1PunkteTafel = new GLTafel(quaderX - 100, punkteTafelY, quaderZ + 3, 10, 10); // Position links
        spieler1PunkteTafel.setzeText("Punkte: 0", 16); // Punktzahl für Spieler 1
        spieler1PunkteTafel.setzeTextfarbe(0, 0, 0); // Schwarzer Text
        spieler1PunkteTafel.setzeDrehung(180, 0, 180); // Text um 180 Grad um die X-Achse drehen

        spieler2PunkteTafel = new GLTafel(quaderX + 100, punkteTafelY, quaderZ + 3, 10, 10); // Position rechts
        spieler2PunkteTafel.setzeText("Punkte: 0", 16); // Punktzahl für Spieler 2
        spieler2PunkteTafel.setzeTextfarbe(0, 0, 0); // Schwarzer Text
        spieler2PunkteTafel.setzeDrehung(180, 0, 180); // Text um 180 Grad um die X-Achse drehen
        
        
        

        // Initialisiere Punktzahlen
        spieler1Punkte = 0;
        spieler2Punkte = 0;
    }

    // Methode zur Erhöhung der Punkte für Spieler 1
    public void erhoeheSpieler1Punkte() {
        spieler1Punkte++;
        spieler1PunkteTafel.setzeText(Integer.toString(spieler1Punkte), 16); // Aktualisiere die Anzeige
    }

    // Methode zur Erhöhung der Punkte für Spieler 2
    public void erhoeheSpieler2Punkte() {
        spieler2Punkte++;
        spieler2PunkteTafel.setzeText(Integer.toString(spieler2Punkte), 16); // Aktualisiere die Anzeige
    }

    // Methode zum Zurücksetzen der Punkte
    public void zuruecksetzenPunkte() {
        spieler1Punkte = 0;
        spieler2Punkte = 0;
        spieler1PunkteTafel.setzeText("0", 16); // Setze die Anzeige zurück
        spieler2PunkteTafel.setzeText("0", 16); // Setze die Anzeige zurück
    }
}