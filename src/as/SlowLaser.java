package as;

public class SlowLaser extends Weapon {

	private static String kuva = "laser.png";
    //private static MP3 bulletSound = new MP3("as/laserGun.mp3");
    
    public SlowLaser(double x, double y, double accX) {
    	super(x, y, 110, 5, 4, 0, kuva);
    	
    	//bulletSound.play();
    }

}