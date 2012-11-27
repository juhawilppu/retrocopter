package as;

public class Bullet extends Weapon {

	private static String kuva = "bullet.png";
    private static MP3 bulletSound = new MP3("as/gunshot.mp3");
    
    public Bullet(double x, double y, double crosshairDY) {
    	super(x, y, 30, 9, 4, crosshairDY, kuva);
    	bulletSound.play();
    }
    
}