import GLOOP.*;

public class BowlingSpiel{
    private GLKamera kamera;
    private GLQuader bahn;
    GLLicht licht;
    GLLicht licht2;
    GLHimmel himmel;
    GLTastatur input;


    KameraSteuerung steuerung = new KameraSteuerung();

    

    public BowlingSpiel(){
        //kamera = new GLKamera();
        himmel = new GLHimmel("rosendal_plains_2.jpg");
        input = new GLTastatur();
        
        licht = new GLLicht(0, 100, -325);
        licht2 = new GLLicht(0, 100, 125);
        
        licht.setzeAbschwaechung(0.5);
        licht2.setzeAbschwaechung(0.5);
        
        Ball ball = new Ball(0, -200);
        
        
        
        steuerung.setzePosition(0, 100, 10); // Kamera
        //kamera.setzePosition(0, 100, 0); // LÖSCHEN NUR TEST
        //kamera.setzeBlickpunkt(0, 0, 0);   // Auf die Mitte der Bahn ausrichten
        steuerung.setzeBlickpunkt(0, 5, 175);

        bahn = new GLQuader(0, -5, 0, 50, 5, 400); // Flacher Quader als Bahn
        //bahn.setzeFarbe(139, 68, 38); // Holzoptik
        bahn.setzeFarbe(118, 60, 40); // Holzoptik
        
        BowlingPin pin = new BowlingPin(0, -100);
        
        
        while (!input.esc()) {
            steuerung.steuerungsschleife(input);
        }
        
    }
    


    //public static void main(String[] args) {
      //  new BowlingSpiel();
    //}
}