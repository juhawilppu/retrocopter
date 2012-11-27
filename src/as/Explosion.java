package as;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Explosion {

	private int x;
	private int y;
	private double expSize;
    private static MP3 sound0 = new MP3("as/hitByEMP.mp3");
    private static MP3 sound1 = new MP3("as/explosion.mp3");
    private static MP3 sound2 = new MP3("as/explosion_metal.mp3");
    private static MP3 sound3 = new MP3("as/explosion_junk_crash.mp3");
    private static MP3 sound4 = new MP3("as/explosion_metal_strike.mp3");
    private MP3 sound;
    
    private static String[] kuvat = {"explosion0.png", "explosion1.png", "explosion2.png", "explosion3.png", "explosion4.png",};
    private static Kirjasto kirjasto = new Kirjasto();
    private static Image[] images = kirjasto.loadImages(kuvat);
    private static BufferedImage[] bufImages = kirjasto.loadBufferedImages(images);
    private Animation animaatio;
    
	public Explosion(int x, int y, int width, int height, int soundNumber) {
		animaatio = new Animation(images, bufImages, false, 10, false);
		
		if (soundNumber==0) {
			sound = sound0;
			expSize = 0;
		} else if (soundNumber==1) {
	    	sound = sound1;
	    	expSize = 0.3;
		} else if (soundNumber==2) {
			sound = sound2;
			expSize = 0.4;
		} else if (soundNumber==3) {
			sound = sound3;
			expSize = 0.6;
		} else if (soundNumber==4) {
			sound = sound4;
			expSize = 0.3;
		} else if (soundNumber==5) {
			sound = sound4;
			expSize = 0.1;
		}
			
		this.x = (int) (x+width/2-animaatio.width()/2*expSize);
		this.y = (int) (y+height/2-animaatio.height()/2*expSize);
		sound.play();
	}

	public void update() {
		animaatio.update();
	}
	
	public void paint(Graphics2D g2d, Game game) {
		
		if (expSize<0.01)
			return;
		
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToTranslation(x,y); 
		affineTransform.scale(expSize, expSize);

		g2d.drawImage(animaatio.getImage(), affineTransform, game);
	}

	public boolean isOver() {
		return animaatio.isOver();
	}
	
}
