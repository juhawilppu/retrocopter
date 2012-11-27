package as;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public abstract class Weapon {

    protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected int firepower;
    private Image image;
    protected boolean visible;
    protected int width, height;

    protected double MISSILE_SPEED;
	protected int killExplosionType;
    
    public Weapon(double x, double y, int firepower, double MISSILE_SPEED, int killExplosionType, double crosshairDY, String kuva) {
    	this.x = x;
    	this.y = y;
    	this.firepower = firepower;
    	this.MISSILE_SPEED = MISSILE_SPEED;
    	this.killExplosionType=killExplosionType;
    	this.dy = MISSILE_SPEED*crosshairDY;
    	this.dx = Math.sqrt( Math.pow(MISSILE_SPEED,2) - Math.pow(this.dy,2) );
    	
    	ImageIcon ii =
            new ImageIcon(this.getClass().getResource(kuva));
        image = ii.getImage();
        visible = true;
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public int getFirepower() {
    	return firepower;
    }
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Shape getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    public void move() {
        x += dx;
        y += dy;
        
        if (x > Global.SCREEN_WIDTH || y > Global.SCREEN_HEIGHT || y < 0)
            visible = false;
    }
    
	public void paint(Graphics2D g2d, Game board) {
		//g2d.drawString("dx " + dx + ", dy " + dy, 100, 100);
		g2d.drawImage(image, (int)x, (int)y, board);
	}


	public Explosion killExplosion() {
    	return new Explosion((int)x, (int)y, width, height, killExplosionType);
	}


	public void hit() {
		visible=false;
	}
    
}