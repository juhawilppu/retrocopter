package as;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Cloud {

    private float x;
    private float y;
    private int heitto;
    private int width;
    //private int height;
    private float nopeus;
    private boolean visible;
    private Image image;

    public Cloud(int x, int y, String craft, float nopeus) {
    	this.nopeus = nopeus;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
        image = ii.getImage();
        heitto = Global.SCREEN_WIDTH+width;
        width = image.getWidth(null);
        visible = true;
        this.x = x;
        this.y = y;
    }


    public void move() {
        if (x < -width) 
            x = heitto;
        x -= nopeus*Global.nopeusKerroin;
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }
    
    public int getWidth() {
    	return width;
    }

    public boolean isVisible() {
        return visible;
    }

    public void paint(Graphics2D g2d, Game game) {
        g2d.drawImage(image, (int)x, (int)y, game);
    }

    public void paint(Graphics2D g2d, Game b, int objektinX) {
        g2d.drawImage(image, objektinX, (int)y, b);
    }
    
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Image getImage() {
        return image;
    }

/*    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }*/
    
}
