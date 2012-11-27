package as;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class EnemyCopter {

    private double x;
    private double y;
    private int health;
    
    private boolean visible;
    private Animation animaatio;
    private double dX;
	private boolean hitByEMP;
    private static String[] kuvat = { "enemyComanche1.png", "enemyComanche2.png", "enemyComanche3.png", "enemyComanche4.png"};
    private static Kirjasto kirjasto = new Kirjasto();
    private static Image[] images = kirjasto.loadImages(kuvat);
    private static BufferedImage[] bufImages = kirjasto.loadBufferedImages(images);
    
    public EnemyCopter(int x, int y) {
	    animaatio = new Animation(images, bufImages, true, 2, true);
	    
        visible = true;
        this.x = x;
        this.y = y;
        health = 100;
        dX = Math.random()*3+0.6;
    }

    public void move() {
        if (x < -animaatio.width()) {
            x = Global.SCREEN_WIDTH;
            y = (int) (Math.random()*Global.SCREEN_HEIGHT/2+40);
            health=100;
            hitByEMP=false;
        }
        
        if (y > Global.SCREEN_HEIGHT-200)
        	visible=false;
        
        if (hitByEMP)
        	y += 2;
        
        x -= dX;
        
        animaatio.update();
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

	public void paint(Graphics2D g2d, Game game) {
        g2d.drawImage(animaatio.getImage(), (int)x, (int)y, game); 
	}

    
    public Image getImage() {
        return animaatio.getImage();
    }

    public BufferedImage getBufImage() {
        return animaatio.getBufImage();
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, animaatio.width(), animaatio.height());
    }
 
    public void getHit(int firepower) {
    	health -= firepower;
    	if (!isAlive())
    		visible=false;

    	if (firepower==0) {
    		hitByEMP=true;
    	}
    	
    }

	public boolean isAlive() {
		return health>0;
	}

	public boolean hitByEMP() {
		return hitByEMP;
	}

}