package as;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Craft {

    private double ax;
    private double ay;
    private double dx;
    private double dy;
    private double x;
    private double y;

    private double maxVel = 2.5;
    private double maxAcc = 0.03;
    private boolean visible;
    private double health;
    private double maxHealth;

    private ArrayList<Integer> keysPressed, buttonsPressed;

    private WeaponSystems weaponSystems;
    
	private Animation animaatio;
	private boolean hasCrashed;
	private double gravitation;
	private int bounceTime;
	private int mouseY;
	
    private static Kirjasto kirjasto = new Kirjasto();
    private static String[] kuvat = { "craft_anim1.png", "craft_anim2.png", "craft_anim3.png", "craft_anim4.png" };
    private static String[] destrKuvat = { "craft_death.png" };
    private static Image[] images = kirjasto.loadImages(kuvat);
    private static Image[] destrImages = kirjasto.loadImages(destrKuvat);
    private static BufferedImage[] bufImages = kirjasto.loadBufferedImages(images);
	
    public Craft() {
    	animaatio = new Animation(images, bufImages, destrImages, true, 6, true);
    	gravitation=0.001;
    	health=100;
    	maxHealth=100;
    	weaponSystems = new WeaponSystems(animaatio.width(), animaatio.height());
    	hasCrashed=false;
    	
    	bounceTime=0;
        visible = true;
        x = 40;
        y = 60;

        keysPressed = new ArrayList<Integer>();
        buttonsPressed = new ArrayList<Integer>();
    }


    public void update() {
       
    	if (isAlive()) {
    		handleKeys();
    		affectHandling();
    	} else {
    		dy += 9*gravitation;
    	}
    	
    	weaponSystems.update(mouseY-Global.SCREEN_HEIGHT/2);
    	dx += ax;
    	dy += ay;
    	
    	bounceTime--;
    	if (bounceTime > 20)
    		dy *= 0.85;
    	else if (bounceTime < 0)
    		bounceTime=0;
    	
    	checkVelocityBoundaries();
    	x += dx;
    	y += dy;    
    	checkBoundaries();
    	    		
   		animaatio.update();
    }

    private void affectHandling() {
    	x += (Math.random()*3-1.5)*Math.pow((maxHealth-health)/maxHealth, 2);
    	y += (Math.random()*3-1.5)*Math.pow((maxHealth-health)/maxHealth, 2);
		dy += (double)((maxHealth-health)/maxHealth)*gravitation;
	}


/*    public void checkAccelerationBoundaries() {
    	
    	if (ax > maxAcc) {
    		ax = maxAcc;
    	}
    	
    	if (ax < -maxAcc) {
    		ax = -maxAcc;
    	}

    	if (ay > maxAcc) {
    		ay = maxAcc;
    	}
    	
    	if (ay < -maxAcc) {
    		ay = -maxAcc;
    	} 	
    }*/
    
    public void checkVelocityBoundaries() {
    	
    	if (dx > maxVel) {
    		dx = maxVel;
    		//ax = 0;
    	}
    	
    	if (dx < -maxVel) {
    		dx = -maxVel;
    		//ax = 0;
    	}

    	if (dy > maxVel) {
    		dy = maxVel;
    		//ay = 0;
    	}
    	
    	if (dy < -maxVel) {
    		dy = -maxVel;
    		//ay = 0;
    	}
    	
    }
    
    public void checkBoundaries() {
    	
        if (x < 1) {
            x = 1;
            dx = 0;
            //ax = 0;
        }

        if (y < -15) {
            y = -15;
            dy = 0;
            ay = 0;
        }
    
        if (x > Global.SCREEN_WIDTH-animaatio.width()) {
        	x = Global.SCREEN_WIDTH-animaatio.width();
        	dx = 0;
        	ax = 0;
        }
        
        if (y > Global.SCREEN_HEIGHT-animaatio.height()-100) {
        	if (health <= 0 ) {
        		hasCrashed=true;
        		animaatio.changeToDestrImages();
        	}
        	y = Global.SCREEN_HEIGHT-animaatio.height()-100;
        	dy = 0;
        	ay = 0;
        }
    }
    
    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public BufferedImage getBufImage() {
        return animaatio.getBufImage();
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, animaatio.width(), animaatio.height());
    }

    public void crash() {
    	health -= 30;
    	animaatio.multiplyChangeSpeed(1.5);
    }
    
	public void crashToBuilding() {
		bounceTime=50;
		dy=-dy*2;
		crash();
	}
    
    public boolean isAlive() {
    	return health > 0;
    }
    
    public boolean bouncing() {
    	return bounceTime > 0;
    }
    
    
    public double da(double ax){
    	//if (abs(ax-0.001>0) ) {
    	return (double)((health)/maxHealth)*maxAcc;

    	//}
    	
    	/*
    	if ( (negative & ax < 0 ) | (!negative & ax > 0) )
    		return ( 100*(-Math.pow(ax-1,2)+maxAcc-ax) );
    		//return (1/(x+1)-0.5);
    	else if (negative)
    		return -0.01;
    	else return 0.01;
  		*/
  
    }


    public void handleKeys() {
        
        if (keysPressed.contains(KeyEvent.VK_3))
        	weaponSystems.setSecondaryWeapon(2);

        if (keysPressed.contains(KeyEvent.VK_4))
        	weaponSystems.setSecondaryWeapon(3);
        
        if (keysPressed.contains(KeyEvent.VK_5))
        	weaponSystems.setSecondaryWeapon(4);
        
        if (keysPressed.contains(KeyEvent.VK_A))
            ax = -da(ax);

        if (keysPressed.contains(KeyEvent.VK_D))
            ax = +da(ax);

        if (keysPressed.contains(KeyEvent.VK_W))
            ay = -da(ay);

        if (keysPressed.contains(KeyEvent.VK_S))
            ay = +da(ay);
        
        if (buttonsPressed.contains(MouseEvent.BUTTON1))
        	weaponSystems.firePrimary(x, y);

        if (buttonsPressed.contains(MouseEvent.BUTTON3))
        	weaponSystems.fireSecondary(x, y);
        
    }
    
    public void keyPressed(KeyEvent e) {
    	int key = e.getKeyCode();
    	
        if (key == KeyEvent.VK_1)
        	weaponSystems.scrollChangePrimary();
        else if (key == KeyEvent.VK_2)
        	weaponSystems.scrollChangeSecondary();
        else if ( !keysPressed.contains(key))
    		keysPressed.add(key);
    }

    public void mousePressed(MouseEvent e) {
    	int key = e.getButton();
    	if ( !buttonsPressed.contains(key) )
    		buttonsPressed.add(key);
    }

    public void mouseReleased(MouseEvent e) {
        int key = e.getButton();
    	buttonsPressed.remove((Integer)key);
	}
  	
	public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
    	keysPressed.remove((Integer)key);
        
    	if (key == KeyEvent.VK_A)
        	ax = 0;
        if (key == KeyEvent.VK_D)
            ax = 0;
        if (key == KeyEvent.VK_W)
            ay = 0;
        if (key == KeyEvent.VK_S)
            ay = 0;
    }
    
	public void paint(Graphics2D g2d, Game game) {
		AffineTransform affineTransform = new AffineTransform(); 
		affineTransform.setToTranslation(x,y);
		affineTransform.rotate(Math.toRadians((ax/maxAcc) * 10.0), animaatio.width()/2, animaatio.height()/2); 

		g2d.drawImage(animaatio.getImage(), affineTransform, game);
	
		weaponSystems.paint(g2d, game, x + animaatio.width()*0.85, y+animaatio.height()*0.8);
	}

	public ArrayList<Weapon> getProjectiles() {
		return weaponSystems.getProjectiles();
	}

	public Explosion hitExplode() {
		return new Explosion((int)x, (int)y, animaatio.width(), animaatio.height(), 3);
	}

	public void freeze() {
		dy=0;
		dx=0;
		ax=0;
		ay=0;
	}

	public int[] getData() {
		int[] data = new int[5];
		int[] shells = weaponSystems.getShells();
		
		if (health <= 0)
			data[0] = 0;
		else
			data[0] = (int) health;
		for (int i=0; i<4; i++)
			data[i+1]=shells[i];
		return data;
	}


	public boolean hasCrashed() {
		return hasCrashed;
	}

	public void mouseMoved(MouseEvent e) {
		mouseY = e.getY();
	}


	public String[] getSelWeapons() {
		return weaponSystems.getSelWeapons();
	}


	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0)
			weaponSystems.scrollChangePrimary();
		else if (notches > 0)
			weaponSystems.scrollChangeSecondary();
	}

}