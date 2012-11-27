package as;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class KeyInfo extends IntroScreen {

	private static final long serialVersionUID = 1L;
    
    private static String[] kuvat = { "intro/keys.png", "intro/controls.png"};
    private static Kirjasto kirjasto = new Kirjasto();
    private static Image[] images = kirjasto.loadImages(kuvat);
    
    private double[] imagesX = {Global.SCREEN_WIDTH/2 - images[0].getWidth(null)/2, Global.SCREEN_WIDTH/2 - images[1].getWidth(null)/2};
    private double[] imagesY = {Global.SCREEN_HEIGHT/2-images[0].getHeight(this)/2+50, 10 };
    
    public KeyInfo(RetroCopter copterGame) {
    	super(copterGame, true);
    }
    
    public void start() {
    	timer.start();
    }
   
    public void actionPerformed(ActionEvent e) {
        update();
    	repaint(); 
    }
    
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
    
       	g2d.drawImage(images[0], (int)(imagesX[0]), (int)imagesY[0], this); 
       	g2d.drawImage(images[1], (int)(imagesX[1]), (int)imagesY[1], this); 
       	
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void update() {
    }
}