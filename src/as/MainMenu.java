package as;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainMenu extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
    
    private static String[] kuvat = {  "intro/menu_background.png", "intro/retrocopter_logo.png"};
    private static Kirjasto kirjasto = new Kirjasto();
    private static Image[] images = kirjasto.loadImages(kuvat);
    
    private double[] imagesX = {0, 500 };
    private double[] imagesY = {0, 50};
    private RetroCopter copterGame;
    private MP3 mp3;
    private JButton newGame, quitGame;
    private Timer timer;
    private ActionListener newGameAction, quitGameAction;
    
    public MainMenu(RetroCopter cg) {
    	this.copterGame = cg;
        mp3 = new MP3("as/ab_ovo.mp3");

        setLayout(null);
        newGame = new JButton("NEW GAME");
        quitGame = new JButton("QUIT GAME");
 
        newGame.setBounds(Global.SCREEN_WIDTH/2-newGame.getWidth()/2, Global.SCREEN_HEIGHT/2+100, 150, 40);
        quitGame.setBounds(Global.SCREEN_WIDTH/2-quitGame.getWidth()/2, Global.SCREEN_HEIGHT/2+60+100, 150, 40);
        
        newGameAction = new NewGameAction();
        quitGameAction = new QuitGameAction();
        newGame.addActionListener(newGameAction);
		quitGame.addActionListener(quitGameAction);
        setBackground( new Color(0,0,0,0) );
		this.add(newGame);
        this.add(quitGame);
        timer = new Timer(5, this);
    }

    public void start() {
    	mp3.play();
    	timer.start();
        repaint();
    }
    
    public void stop() {
		//newGame.removeActionListener(newGameAction);
		//quitGame.removeActionListener(quitGameAction);
    	if (mp3 != null)
    		mp3.close();
    	timer.stop();
    }
    
    public void actionPerformed(ActionEvent e) {
        update();
    }
    
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D)g;
    
       for (int i=0; i<images.length; i++) {
       		g2d.drawImage(images[i], (int)(imagesX[i]), (int)imagesY[i], this); 
        }
        
        super.paint(g);
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void update() {
    	repaint();
    }
    
    private class NewGameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			copterGame.newGame();
		}
	}
    
    private class QuitGameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			copterGame.quitGame();
		}
	}
   
 }