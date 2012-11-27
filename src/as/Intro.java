package as;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class Intro extends IntroScreen {

	private static final long serialVersionUID = 1L;
    
    private static String[] kuvat = { "intro/borderline.png", "intro/borderlake.png", "intro/genius.png", "intro/map.png"};
    private static Kirjasto kirjasto = new Kirjasto();
    private static Image[] images = kirjasto.loadImages(kuvat);
    
    private int downShift=150;
    private double[] imagesX = {Global.SCREEN_WIDTH/2 - images[0].getWidth(null)/2, -images[1].getWidth(null), Global.SCREEN_WIDTH + (images[1].getWidth(null)-images[2].getWidth(null))/2, Global.SCREEN_WIDTH/2-images[3].getWidth(this)/2 };
    private double[] imagesY = {Global.SCREEN_HEIGHT/2-images[0].getHeight(this)/2 +downShift, Global.SCREEN_HEIGHT/2-images[1].getHeight(null) + downShift -10, Global.SCREEN_HEIGHT/2 + downShift - 60, Global.SCREEN_HEIGHT/2 - images[3].getHeight(null)/2 - 143 };
    private double[] imagesDX = {0, 5, -5, 0 };
    private double[] imagesDY = {0, 0, 0, 0};
    private double[] imagesAX = {0, 0.05, -0.05, 0};
    private int waitTime=250, waitTimeWent;
    
    public Intro(RetroCopter retroCopter) {
    	super(retroCopter, true);
        mp3 = new MP3("as/robosound.mp3");
    }
    
    public void actionPerformed(ActionEvent e) {
        update();
    	repaint(); 
    }
    
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
    
        for (int i=0; i<images.length; i++) {
       		g2d.drawImage(images[i], (int)(imagesX[i]), (int)imagesY[i], this); 
        }
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void update() {
    	if (waitTimeWent < waitTime && ( Math.abs( (imagesX[1]+images[1].getWidth(null)/2) - (imagesX[2]+images[2].getWidth(null)/2) ) < 10) ) {
    		waitTimeWent++;
    		imagesDX[1] = 5;
    		imagesDX[2] = -5;
    		imagesAX[1] = 0.15;
    		imagesAX[2] = -0.15;
    		return;
    	}

    	for (int i=0; i<images.length; i++) {
    		imagesDX[i] += imagesAX[i];
    		imagesY[i] += imagesDY[i];
    		imagesX[i] += imagesDX[i];
    	}
      	
      	if (imagesX[1] > Global.SCREEN_WIDTH ) {
      		if (waitTimeWent > 50)
      			waitTimeWent--;
      		else
      			retroCopter.next();
      	}
    }
 }