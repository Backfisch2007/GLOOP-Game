import GLOOP.*;

public class BowlingPin {
    private GLZylinder basis1;
    private GLKugel oberteil1;

    private GLZylinder basis2;
    private GLKugel oberteil2;

    private GLZylinder basis3;
    private GLKugel oberteil3;

    private GLZylinder basis4;
    private GLKugel oberteil4;

    private GLZylinder basis5;
    private GLKugel oberteil5;

    private GLZylinder basis6;
    private GLKugel oberteil6;

    private GLZylinder basis7;
    private GLKugel oberteil7;

    private GLZylinder basis8;
    private GLKugel oberteil8;

    private GLZylinder basis9;
    private GLKugel oberteil9;

    private GLZylinder basis10;
    private GLKugel oberteil10;

    public BowlingPin(double x, double z) {
        // 1. Reihe
        basis1 = new GLZylinder(-6.4, 0, 185, 2.5, 10); //Zylinder unten P1
        basis1.drehe(90, 90, 0); // Zylinder 90° gedreht 
        basis1.setzeFarbe(0, 100, 0); // Farbe
        oberteil1 = new GLKugel(-6.4, 6, 185, 2.5); // Kugel als oberer Teil P1
        oberteil1.setzeFarbe(0, 100, 0); // Farbe

        basis2 = new GLZylinder(6.4, 0, 185, 2.5, 10); //Zylinder unten P2
        basis2.drehe(90, 90, 0); // Zylinder 90° gedreht 
        basis2.setzeFarbe(0, 100, 0); // Farbe
        oberteil2 = new GLKugel(6.4, 6, 185, 2.5); // Kugel als oberer Teil P2
        oberteil2.setzeFarbe(0, 100, 0); // Farbe

        basis3 = new GLZylinder(-19.2, 0, 185, 2.5, 10); //Zylinder unten P3
        basis3.drehe(90, 90, 0); // Zylinder 90° gedreht 
        basis3.setzeFarbe(0, 100, 0); // Farbe
        oberteil3 = new GLKugel(-19.3, 6, 185, 2.5); // Kugel als oberer Teil P3
        oberteil3.setzeFarbe(0, 100, 0); // Farbe

        basis4 = new GLZylinder(19.2, 0, 185, 2.5, 10); //Zylinder unten P4
        basis4.drehe(90, 90, 0); // Zylinder 90° gedreht 
        basis4.setzeFarbe(0, 100, 0); // Farbe
        oberteil4 = new GLKugel(19.2, 6, 185, 2.5); // Kugel als oberer Teil P4
        oberteil4.setzeFarbe(0, 100, 0); // Farbe

        // 2. Reihe
        basis5 = new GLZylinder(0, 0, 175, 2.5, 10); //Zylinder unten P5
        basis5.drehe(90, 90, 0); // Zylinder 90° gedreht 
        basis5.setzeFarbe(100, 0, 0); // Farbe
        oberteil5 = new GLKugel(0, 6, 175, 2.5); // Kugel als oberer Teil P5
        oberteil5.setzeFarbe(100, 0, 0); // Farbe

        basis6 = new GLZylinder(-12.8, 0, 175, 2.5, 10); //Zylinder unten P6
        basis6.drehe(90, 90, 0); // Zylinder 90° gedreht 
        basis6.setzeFarbe(100, 0, 0); // Farbe
        oberteil6 = new GLKugel(-12.8, 6, 175, 2.5); // Kugel als oberer Teil P6
        oberteil6.setzeFarbe(100, 0, 0); // Farbe

        basis7 = new GLZylinder(12.8, 0, 175, 2.5, 10); //Zylinder unten P7
        basis7.drehe(90, 90, 0); // Zylinder 90° gedreht
        basis7.setzeFarbe(100, 0, 0); // Farbe  
        oberteil7 = new GLKugel(12.8, 6, 175, 2.5); // Kugel als oberer Teil P7
        oberteil7.setzeFarbe(100, 0, 0); // Farbe

        // 3. Reihe
        basis8 = new GLZylinder(-6.4, 0, 165, 2.5, 10); //Zylinder unten P8
        basis8.drehe(90, 90, 0); // Zylinder 90° gedreht 
        basis8.setzeFarbe(0, 0, 100); // Farbe
        oberteil8 = new GLKugel(-6.4, 6, 165, 2.5); // Kugel als oberer Teil P8
        oberteil8.setzeFarbe(0, 0, 100); // Farbe

        basis9 = new GLZylinder(6.4, 0, 165, 2.5, 10); //Zylinder unten P9
        basis9.drehe(90, 90, 0); // Zylinder 90° gedreht
        basis9.setzeFarbe(0, 0, 100); // Farbe  
        oberteil9 = new GLKugel(6.4, 6, 165, 2.5); // Kugel als oberer Teil P9
        oberteil9.setzeFarbe(0, 0, 100); // Farbe

        // 4. Reihe
        basis10 = new GLZylinder(0, 0, 155, 2.5, 10); //Zylinder unten P10
        basis10.drehe(90, 90, 0); // Zylinder 90° gedreht 
        basis10.setzeFarbe(0, 0, 100); // Farbe
        oberteil10 = new GLKugel(0, 6, 155, 2.5); // Kugel als oberer Teil P10
        oberteil10.setzeFarbe(0, 0, 100); // Farbe
    }

    /**
    
    
    // Methode zum Umwerfen des Pins
    public void umwerfen() {
        // Drehe alle Teile des Pins um 90° um die X-Achse
        basis1.drehe(90, "X");
        oberteil1.drehe(90, "X");
    }

    // Optional: Methode zum Zurücksetzen des Pins
    public void zuruecksetzen() {
        // Setze alle Teile des Pins in die Ausgangsposition zurück
        basis1.setzeDrehung(90, 90, 0);
        oberteil1.setzeDrehung(0, 0, 0);

    }
    */
}