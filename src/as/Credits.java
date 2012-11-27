package as;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class Credits extends IntroScreen {

    private static String[] logoKuvat = { "intro/borderline.png", "intro/borderlake.png", "intro/genius.png", "intro/credits.png"};
    private static Kirjasto kirjasto = new Kirjasto();
    private static Image[] logoImages = kirjasto.loadImages(logoKuvat);
    
    private double[] logoImagesX = {Global.SCREEN_WIDTH/2 - logoImages[0].getWidth(null)/2, Global.SCREEN_WIDTH/2 - logoImages[1].getWidth(null)/2, Global.SCREEN_WIDTH/2 - logoImages[2].getWidth(null)/2, Global.SCREEN_WIDTH/2 - logoImages[3].getWidth(null)/2 };
    private double[] logoImagesY = {Global.SCREEN_HEIGHT/2-logoImages[0].getHeight(this)/2, Global.SCREEN_HEIGHT/2-logoImages[1].getHeight(null) -10, Global.SCREEN_HEIGHT/2 - 60, 450 };
    private double[] logoImagesDX = {0, 1.5, -1.5, 0};
    private int sinceChange;
	
    private static final long serialVersionUID = 1L;

	public Credits(RetroCopter retroCopter) {
		super(retroCopter, true);
        mp3 = new MP3("as/paivystaja.mp3");
        setBackground( new Color(0,0,0) );
	}

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
    
        for (int i=0; i<logoImages.length; i++) {
       		g2d.drawImage(logoImages[i], (int)(logoImagesX[i]), (int)logoImagesY[i], this); 
        }
        
        g2d.setColor(Color.WHITE);
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void update() {
    	sinceChange++;
    	for (int i=0; i<logoImages.length; i++) {
    		logoImagesY[i] -= 0.1;
    		logoImagesX[i] += logoImagesDX[i];
    	}
   		if ( (sinceChange > 200) && ( (logoImagesX[1] + logoImages[1].getWidth(null)/2) < Global.SCREEN_WIDTH*(1.0/3) ) || ((logoImagesX[1] + logoImages[1].getWidth(null)/2) > Global.SCREEN_WIDTH*(2.0/3) ) ) {
  			logoImagesDX[1] *= -1;
   			logoImagesDX[2] *= -1;
   			sinceChange=0;
   		}
    }

	public void actionPerformed(ActionEvent e) {
		update();
		repaint();
		
	}
	
	public void next() {
		retroCopter.quitImmediately();
	}

}
