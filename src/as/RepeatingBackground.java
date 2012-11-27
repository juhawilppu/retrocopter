package as;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class RepeatingBackground {

    private double[] x;
    private int[] y;
    private int[] width;
    private int[] height;
    private double[] nopeus;
    private boolean[] collidable;
    private int[] kuvienMaara;
    private int kuvatTotal;
    private Image[] image;

    private static Kirjasto kirjasto = new Kirjasto();
    private BufferedImage[] bufImages;

    public RepeatingBackground(int[] y, String[] kuvat, double[] nopeus, boolean[] collidable) {
    	
    	this.nopeus = nopeus;
        this.y = y;
        this.collidable = collidable;
        
        kuvatTotal = kuvat.length;

    	image = kirjasto.loadImages(kuvat);
    	bufImages = kirjasto.loadBufferedImages(image);
    	kuvienMaara = new int[kuvat.length];
    	width = new int[kuvat.length];
    	height = new int[kuvat.length];
    	x = new double[kuvat.length];
    	
    	for (int i=0; i<kuvat.length; i++) {
    		width[i] = image[i].getWidth(null);
    		height[i] = image[i].getWidth(null);
    		kuvienMaara[i] = (int) Math.ceil(Global.SCREEN_WIDTH/width[i]) +2;
    		x[i] = 0;
    	}
        
    }

    public void update() {
        for (int i=0; i < kuvatTotal; i++) {
        	if (x[i] < -width[i]) 
        		x[i] = 0;
        	x[i] -= nopeus[i]*Global.nopeusKerroin;
    	}
    }

 /*   public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }
    
    public int getWidth() {
    	return width;
    }
*/
    
    public void paint(Graphics2D g2d, Game game) {
    	for (int i=0; i < kuvatTotal; i++) {
    		for (int j=0; j < kuvienMaara[i]; j++)
    			g2d.drawImage(image[i], (int)x[i]+j*width[i], (int)y[i], game);
    	}
    }

	public boolean isCollide(Rectangle craftRectangle, int x2, int y2, BufferedImage bufImage) {
        for (int i=0; i<kuvatTotal; i++) {
        	if (!collidable[i])
        		continue;
        	
        	for (int j=0; j<kuvienMaara[i]; j++) {
        		Rectangle r = getBounds(i, j);
            
        		if (r.intersects(craftRectangle)) {
        			if ( Kirjasto.isPixelCollide( x2, y2, bufImage, (int)(x[i]+j*width[i]), (int)y[i], bufImages[i] ) ) {
        				return true;
        			}
        		}
        	}
        }
        return false;
	}
    
    public Rectangle getBounds(int i, int j) {
    	return new Rectangle( (int)(x[i]+j*width[i]), (int)y[i], (int) (x[i]+(i+1)*width[i]), (int) (y[i]+height[i]) );
    }
}
