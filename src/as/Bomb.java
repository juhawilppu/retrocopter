package as;

import as.Weapon;

public class Bomb extends Weapon {

	private static String kuva = "bomb.png";
	private double g = 0.3;
	private boolean hitGround;
	private boolean haveExploded;
	
	public Bomb(double x, double y, double crosshairDY) {
		super(x, y, 100, 0, 1, 0, kuva);
		
		hitGround=false;
		haveExploded=false;
	}

    public void move() {
    	
   		dy += g;
    	x += dx;
    	y += dy;
    	
        if (y > 500) {
        	visible = false;
        	hitGround=true;
        }
    }

    public boolean hitGround() {
    	return hitGround;
    }
    
    public void explode() {
    	haveExploded = true;
    }
    
	public boolean haveExploded() {
		return haveExploded;
	}
	
    public Explosion killExplosion() {
    	return new Explosion((int)x, (int)y, width, height, 2);
    }
}
