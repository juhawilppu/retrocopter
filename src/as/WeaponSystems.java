package as;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class WeaponSystems {

    private ArrayList<Weapon> projectiles;
    
    private int[] waitTimes = {10, 40, 100, 0, 100};
    private int[] regenTime = {100, 500, 1000, 0, 200};
    private int[] maxShells = {200, 20, 10, 100, 5};
    private int[] shells = {200, 20, 10, 100, 5};
	private int[] idleTimes;
	private int[] regenTimeWent;
	private String[] weapons = {"Cannon", "Missiles", "Bombs", "Laser", "EMP"};
	private int[] selWeapons = {0, 2};
	private int primaryWeapons=2;
	private int secondaryWeapons=3;
	private int numberOfGuns = 5;
	
	private Crosshair crosshair;

	private int craftWidth;

	private int craftHeight;

	
	public WeaponSystems(int craftWidth, int craftHeight) {
		this.craftWidth=craftWidth;
		this.craftHeight=craftHeight;
        projectiles = new ArrayList<Weapon>();
        crosshair = new Crosshair();

    	idleTimes = new int[numberOfGuns];
    	regenTimeWent = new int[numberOfGuns];
        
	}
	
	public boolean isReady(int i) {
		return (idleTimes[i] > waitTimes[i] && shells[i]>0);
	}
	
    public void fireWeapon(int i, double x, double y) {
    	if (isReady(i)) {
    		
    		if (i==0)
    			projectiles.add( new Bullet(x+craftWidth*0.85, y+craftHeight*0.8, crosshair.getNormDY()) );
    		else if (i==1)
    			projectiles.add( new Missile(x+craftWidth/2, y+craftHeight*0.9, crosshair.getNormDY()) );
    		else if (i==2)
    			projectiles.add( new Bomb(x+craftWidth/2, y+craftHeight*0.9, 0) );
    		else if (i==3)
    			projectiles.add( new SlowLaser(x+craftWidth*0.85, y+craftHeight*0.8, 0) ); 
    		else if (i==4)
    			projectiles.add( new EMP(x+craftWidth*0.85, y+craftHeight*0.8, 0) );
    		
    		idleTimes[i]=0;
    		shells[i]--;
    	}
    }
    
    public void setPrimaryWeapon(int i) {
    	selWeapons[0]=i;
    }

    public void setSecondaryWeapon(int i) {
    	selWeapons[1]=i;
    }
    
	public void update(int mouseY) {

		for (int i=0; i<numberOfGuns; i++) {
			idleTimes[i]++;
			regenTimeWent[i]++;
			if (regenTimeWent[i] > regenTime[i]) {
				regenTimeWent[i]=0;
				shells[i]++;
				if (shells[i] > maxShells[i])
					shells[i] = maxShells[i];
			}
		}
		
        for (int i=0; i<projectiles.size(); i++) {
            Weapon p = projectiles.get(i);
            if (p.isVisible()) 
                p.move();
            else projectiles.remove(i);
        }
        
        crosshair.update(mouseY);
	}
	
	public void paint(Graphics2D g2d, Game game, double x, double y) {
	    for (Weapon p : projectiles)
	    	p.paint(g2d, game);

	    //g2d.drawRect( (int) x, (int) y, 2, 2);
	    crosshair.paint(g2d, game, x, y );
	}

    public ArrayList<Weapon> getProjectiles() {
        return projectiles;
    }

	public int[] getShells() {
		return shells;
	}

	public String[] getSelWeapons() {
		String[] selected = {weapons[selWeapons[0]], weapons[selWeapons[1]]};
		return selected;
	}

	public void firePrimary(double x, double y) {
		fireWeapon(selWeapons[0], x, y);
	}

	public void fireSecondary(double x, double y) {
		fireWeapon(selWeapons[1], x, y);
		
	}

	public void scrollChangePrimary() {
		selWeapons[0]++;
		if (selWeapons[0] > primaryWeapons-1)
			selWeapons[0]=0;
	}

	public void scrollChangeSecondary() {
		selWeapons[1]++;
		if (selWeapons[1] > (primaryWeapons+secondaryWeapons-1) )
			selWeapons[1]=2;
	}

}
