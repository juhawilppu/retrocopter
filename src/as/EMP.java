package as;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class EMP extends Weapon {

	private static String kuva = "bullet.png";
    private static MP3 bulletSound = new MP3("as/EMP.mp3");
    private int initialX;
    private int initialY;
    
    public EMP(double x, double y, double crosshairDY) {
    	super(x, y, 0, 9, 0, crosshairDY, kuva);
    	initialX=(int) x;
    	initialY=(int) y;
    	bulletSound.play();
    }

    @Override
	public void paint(Graphics2D g2d, Game board) {
	    g2d.setColor(Color.BLACK);
    	g2d.drawOval((int)(initialX -(x-initialX)/2), (int)(initialY -(x-initialX)/2), (int)(x-initialX), (int)(x-initialX));
	}

    public Ellipse2D getBounds() {
        return new Ellipse2D.Double(initialX -(x-initialX)/2, initialY -(x-initialX)/2, x-initialX, x-initialX);
    }
    
    @Override
	public void hit() {
		//visible=true;
	}

    public void move() {
        x += MISSILE_SPEED;
        
        if ( getBounds().contains(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT) && getBounds().contains(0, 0) )
            visible = false;
    }
}