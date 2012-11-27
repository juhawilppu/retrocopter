package as;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
    private Craft craft;
    
    private ArrayList<EnemyCopter> enemies;
    private ArrayList<Cloud> clouds;
    private ArrayList<Explosion> explosions;
    private InfoBar infoBar;
    
    private boolean ingame;
    private int kills;
    private MP3 musiikki;

    private int[][] posEnemyCopters = { {800, 290}, {1200, 129}, {480, 300} };
    private int[][] posClouds = { {200, 30}, {730, 59}, {1380, 19}, {1600, 25} };
    private RepeatingBackground backgrounds;
    private int menuDelay;
    private RetroCopter copterGame;
    
    private KAdapter kadapter;
    private MAdapter madapter;
    private MListener mlistener;
    private MWListener mwlistener;
    
    public Game(RetroCopter copterGame2) {
    	this.copterGame = copterGame2;
		kadapter = new KAdapter();
		madapter = new MAdapter();
	    mlistener = new MListener();
	    mwlistener = new MWListener();
		copterGame2.addKeyListener(kadapter);
		copterGame2.addMouseListener(madapter);
		copterGame2.addMouseMotionListener(mlistener);
        copterGame2.addMouseWheelListener(mwlistener);
        setFocusable(true);
        setBackground( new Color(95,167,255) );
        setDoubleBuffered(true);
        ingame = true;

        setSize(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
        
        if ( (int)(Math.random()*2) > 0 ) 
        	musiikki = new MP3("as/seekingSomething.mp3");
        else
        	musiikki = new MP3("as/go_freddie_go.mp3");
        
        craft = new Craft();
        
        Global.nopeusKerroin = 2;

        infoBar = new InfoBar(20, 550, 5);
        initLevel();
        kills=0;

        timer = new Timer(5, this);
    }

    public void start() {
    	musiikki.play();
    	timer.start();
    }
    
    public void stop() {
    	musiikki.close();
    	timer.stop();
		copterGame.removeKeyListener(kadapter);
		copterGame.removeMouseListener(madapter);
		copterGame.removeMouseMotionListener(mlistener);
        copterGame.removeMouseWheelListener(mwlistener);
    }
    
    public void addNotify() {
        super.addNotify();
    }

   
    public void initLevel() {
    	
        String[] kuvat = {"skyline.png", "near_city.png"};
        int[] y = {280, 370};
        double[] nopeus = {0.5, 0.9};
        boolean[] collidable = {false, true};
        
        backgrounds = new RepeatingBackground(y, kuvat, nopeus, collidable);
    	
    	enemies = new ArrayList<EnemyCopter>();
        clouds = new ArrayList<Cloud>();
        explosions = new ArrayList<Explosion>();
        
        for (int i=0; i<posEnemyCopters.length; i++ )
        	enemies.add(new EnemyCopter(posEnemyCopters[i][0], posEnemyCopters[i][1]));

        for (int i=0; i<posClouds.length; i++ )
            clouds.add( new Cloud(posClouds[i][0], posClouds[i][1], "cloud.png", (float)0.8) );
   
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;

        // Draw clouds
        for (Cloud c : clouds) {
        	c.paint(g2d, this);
        } 

        backgrounds.paint(g2d, this);
        infoBar.paint(g2d, this);
            
        // Draw craft
        if (craft.isVisible())
         	craft.paint(g2d, this);
                     
        // Draw enemies
        for (EnemyCopter a : enemies) {
        	if (a.isVisible())
        		a.paint(g2d, this);
        }
            
        // Draw explosions
        for (Explosion e : explosions) {
        	if (!e.isOver())
                    e.paint(g2d, this);
        }

        g2d.setColor(Color.WHITE);
        g2d.drawString("Enemies killed: " + kills, 5, 15);
        //g2d.drawString(craft.getTelemetriaAx(), 5, 29);

        if (craft.hasCrashed() ) {
        	menuDelay++;
        	String msg = "GAME OVER";
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = this.getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (Global.SCREEN_WIDTH - metr.stringWidth(msg)) / 2, Global.SCREEN_HEIGHT / 2);
        
            if (menuDelay > 500) {
            	timer.stop();
            	copterGame.next();
            }
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
    	
        if (enemies.size()==0) {
        	int uusienVihollistenM‰‰r‰ = (int)(Math.random()*4+1);
            for (int i=0; i < uusienVihollistenM‰‰r‰; i++ )
            	enemies.add(new EnemyCopter( (int)( (Math.random()*300)+Global.SCREEN_WIDTH), (int)( (Math.random()*Global.SCREEN_HEIGHT/2) + 35)) );
        }

        backgrounds.update();

        if (ingame)
        	infoBar.update(craft.getData(), craft.getSelWeapons());
        
        // Move enemies, or remove if out of screen
        for (int i=0; i < enemies.size(); i++) {
        	EnemyCopter a = enemies.get(i);
            if (a.isVisible()) 
                a.move();
            else enemies.remove(i);
        }

        // Move clouds, or remove if out of screen
        for (int i=0; i < clouds.size(); i++) {
        	Cloud c = clouds.get(i);
            if (c.isVisible()) 
                c.move();
            else clouds.remove(i);
        }
 
        // Move explosions, or remove if out of screen
        for (int i=0; i < explosions.size(); i++) {
        	Explosion exp = explosions.get(i);
            if (!exp.isOver()) 
                exp.update();
            else explosions.remove(i);
        }

        craft.update();
        checkCollisions();
        repaint(); 
    }

    public void checkCollisions() {

    	if (!ingame)
    		return;
    	
        Rectangle craftRectangle = craft.getBounds();

        // Check if player-craft has crashed
        if (craft.hasCrashed() & ingame) {
    		ingame=false;
    		craft.freeze();
    		explosions.add( craft.hitExplode() );
    		Global.nopeusKerroin=0;
        } else {
            // Check if player-craft has hit an enemy
	        for (int j = 0; j<enemies.size(); j++) {
	            EnemyCopter a = (EnemyCopter) enemies.get(j);
	            Rectangle r2 = a.getBounds();
	            
	            if (r2.intersects(craftRectangle)) {
	            	if ( Kirjasto.isPixelCollide(craft.getX(), craft.getY(), craft.getBufImage(), a.getX(), a.getY(), a.getBufImage() )) {
	            		explosions.add( craft.hitExplode() );
	            		craft.crash();
	            		a.setVisible(false);
	            	}
	            }
	        }
        }
        
        // Check if player-craft has crashed into building
        if (!craft.bouncing() && craft.isAlive() && backgrounds.isCollide(craftRectangle, craft.getX(), craft.getY(), craft.getBufImage())) {
    		explosions.add( craft.hitExplode() );
    		craft.crashToBuilding();
        }
        
        ArrayList<Weapon> projectiles = craft.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Weapon p = projectiles.get(i);
            
            Shape r1 = p.getBounds();

            for (int j = 0; j<enemies.size(); j++) {
            	EnemyCopter a = (EnemyCopter) enemies.get(j);

            	if (a.hitByEMP())
            		continue;
            	
                Rectangle r2 = a.getBounds();

                if (r1.intersects(r2)) {
                	a.getHit(p.getFirepower());
                	p.hit();
            		if (!a.isAlive() || a.hitByEMP()) {
                		if (!a.hitByEMP())
                			a.setVisible(false);
                		kills++;
                		explosions.add( p.killExplosion() );
                	} else
                		explosions.add( new Explosion(p.getX(),p.getY(), 3, 4, 5) );
                }
            }
        }
        
    }
    
    private class KAdapter extends KeyAdapter {
        @Override
    	public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }
        @Override
        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
        }
    }
    
    private class MAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            craft.mousePressed(e);
        }
        public void mouseReleased(MouseEvent e) {
            craft.mouseReleased(e);
        }
    }

	private class MListener implements MouseMotionListener {
		public void mouseMoved(MouseEvent e) {
    		craft.mouseMoved(e);
    	}
		public void mouseDragged(MouseEvent e) {
			craft.mouseMoved(e);
		}
	}
	
	private class MWListener implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent e) {
			craft.mouseWheelMoved(e);
		}
	}
}