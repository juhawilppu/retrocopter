package as;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class GameLogo extends IntroScreen {

	private static final long serialVersionUID = 1L;
    
    private static String[] kuvat = { "intro/retrocopter_logo.png", "intro/apache.png"};
    private static Kirjasto kirjasto = new Kirjasto();
    private static Image[] images = kirjasto.loadImages(kuvat);
    
    private double[] imagesX = {Global.SCREEN_WIDTH/2 - images[0].getWidth(null)/2, Global.SCREEN_WIDTH/2 - images[1].getWidth(null)/2};
    private double[] imagesY = {Global.SCREEN_HEIGHT-images[0].getHeight(this)-30, 0 };
    private double[] imagesDX = {0, -1};
    
    public GameLogo(RetroCopter copterGame) {
    	super(copterGame, true);
    	mp3 = new MP3("as/walking_on_the_mood.mp3");
    }
       
    public void actionPerformed(ActionEvent e) {
        update();
    	repaint();
    }
    
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
    
       	for (int i=0; i<images.length; i++)
       		g2d.drawImage(images[i], (int)(imagesX[i]), (int)imagesY[i], this); 
       	
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void update() {
    	imagesX[1] += imagesDX[1];
    	if (imagesX[1] < 0 || imagesX[1] > Global.SCREEN_WIDTH - images[1].getWidth(null) )
    		imagesDX[1]*=(-1);
    }
}