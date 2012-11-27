package as;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

import javax.swing.ImageIcon;

public class Kirjasto {

    public static boolean isPixelCollide(double x1, double y1, BufferedImage image1, double x2, double y2, BufferedImage image2) {
    	
    	// initialization
		double width1 = x1 + image1.getWidth() -1,
		height1 = y1 + image1.getHeight() -1,
		width2 = x2 + image2.getWidth() -1,
		height2 = y2 + image2.getHeight() -1;
		
		int xstart = (int) Math.max(x1, x2),
		ystart = (int) Math.max(y1, y2),
		xend   = (int) Math.min(width1, width2),
		yend   = (int) Math.min(height1, height2);
		
		// intersection rect
		int toty = Math.abs(yend - ystart);
		int totx = Math.abs(xend - xstart);
		
		for (int y=1;y < toty-1;y++){
			int ny = Math.abs(ystart - (int) y1) + y;
			int ny1 = Math.abs(ystart - (int) y2) + y;
		
			for (int x=1;x < totx-1;x++) {
				int nx = Math.abs(xstart - (int) x1) + x;
				int nx1 = Math.abs(xstart - (int) x2) + x;
				
				try {
					if (((image1.getRGB(nx,ny) & 0xFF000000) != 0x00) && ((image2.getRGB(nx1,ny1) & 0xFF000000) != 0x00)) {
						// collide!!
						return true;
					}
				} catch (Exception e) {
					//System.out.println("s1 = "+nx+","+ny+"  -  s2 = "+nx1+","+ny1);
				}
			}
		}
		
		//no collide
		return false;
    }

 // This method returns a buffered image with the contents of an image
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see Determining If an Image Has Transparent Pixels
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
       
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }

 // This method returns true if the specified image has transparent pixels
    public static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel().hasAlpha();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
         PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }


    public Image[] loadImages(String[] kuvat) {
        ImageIcon[] imageIcon = new ImageIcon[kuvat.length];
        
        for (int i=0; i<kuvat.length; i++)
        	imageIcon[i] = new ImageIcon( this.getClass().getResource( kuvat[i] ) );
        
        Image[] imgKuvat = new Image[kuvat.length];

        for (int i=0; i<kuvat.length; i++)
        	imgKuvat[i] = imageIcon[i].getImage();    	
    	
        return imgKuvat;
    }
    
    public BufferedImage[] loadBufferedImages(Image[] kuvat) {
      	BufferedImage[] bufImage = new BufferedImage[kuvat.length];
       	for (int i=0; i<kuvat.length; i++)
       		bufImage[i] = toBufferedImage(kuvat[i]);
   
         return bufImage;
    }
    
}
