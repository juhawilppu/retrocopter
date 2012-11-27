package as;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import as.Weapon;

public class Missile extends Weapon {

	private static String kuva = "missile.png";
	private double pudonnutY;
	private double g = 3;
	
	final private double acc = 0.2;
	private double putoamisKorkeus = 30;
    
	private MP3 missileSound = new MP3("as/missile.mp3");
	private MissileClouds missileClouds;
	
	public Missile(double x, double y, double crosshairDY) {
		super(x, y, 100, 30, 2, 0, kuva);
		this.dx=0;
		pudonnutY=0;
		missileClouds = new MissileClouds();
    	missileSound.play();
	}

	@Override
    public void move() {
    	
    	if (pudonnutY < putoamisKorkeus) {
    		pudonnutY += g;
    		y += g;
    		y += dy;
    	}

    	dx += acc;
    	x += dx;
    	y += dy;
    	
    	if (dx > MISSILE_SPEED)
    		dx = MISSILE_SPEED;
   		        
        if (x > Global.SCREEN_WIDTH)
            visible = false;
    
        missileClouds.update();
    }
    
    @Override
	public void paint(Graphics2D g2d, Game board) {
		super.paint(g2d, board);
    	missileClouds.paint(g2d, board);
	}

	private class MissileClouds {

    	private String kuva = "missile_cloud.png";
    	private Cloud[] clouds;
        private Image missileCloud;
        
        final private int MAX_CLOUDS = 5;
        final private int APPEARING_INTERVAL = 15;

        private int cloudsAmount;
        private int cloudWidth;
        private int cloudHeight;
        
        private int intervals;
        
    	public MissileClouds() {
    		ImageIcon imageIcon = new ImageIcon( this.getClass().getResource(kuva) );
    		missileCloud = imageIcon.getImage();
    		cloudWidth = missileCloud.getWidth(null);
    		cloudHeight = missileCloud.getHeight(null);
    		
    		//cloudTTL=200;
    		intervals = 0;
    		
    		cloudsAmount=0;
    		clouds = new Cloud[MAX_CLOUDS];
    		for (int i=0; i<MAX_CLOUDS; i++)
    			clouds[i] = new Cloud();
    	}
    	
    	public void update() {
    		// Jos kaikkia pilviä ei ole vielä, tehdään lisää
    		if (cloudsAmount < MAX_CLOUDS) {
    			intervals++;
    			if (intervals == APPEARING_INTERVAL) {
    				intervals=0;
    				clouds[cloudsAmount].x=x;
    				clouds[cloudsAmount].dx=dx;
    				cloudsAmount++;
    			}
    		}
    		
    		// Siirretään pilviä
    		for (int i=0; i<MAX_CLOUDS; i++) {
    			clouds[i].x += clouds[i].dx;
    		}
    		
    	}
    	
    	public void paint(Graphics2D g2d, Game board) {
    		for (int i=0; i<MAX_CLOUDS; i++) {
    			// Älä piirrä, jos x=0
    			if ( Math.abs(clouds[i].x - 0.01 ) < 0)
    				continue;
    			else
    				g2d.drawImage(missileCloud, (int) (clouds[i].x-cloudWidth), (int) (y+0.5*height-0.5*cloudHeight), board);
    		}
    		
    	}

        private class Cloud {
    		public double x;
    		public double dx;
    	}
    	
	}
    

}
