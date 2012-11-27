package as;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class IntroScreen extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	protected Timer timer;
    protected MP3 mp3;
    protected RetroCopter retroCopter;
    private TAdapter tadapter;
    private MAdapter madapter;
    private boolean skippable;
    
    public IntroScreen(RetroCopter copterGame2, boolean skippable) {
    	this.retroCopter=copterGame2;
    	this.skippable=skippable;
    	if (skippable) {
    		tadapter = new TAdapter();
    		madapter = new MAdapter();
    		copterGame2.addKeyListener(tadapter);
    		copterGame2.addMouseListener(madapter);
    	}
    	setFocusable(true);
        setBackground( new Color(255,255,255) );
        setDoubleBuffered(true);
        setSize(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
        timer = new Timer(5, this);
    }

	public void start() {
    	mp3.play();
    	timer.start();
    }
    
    public void stop() {
    	if (skippable) {
    		retroCopter.removeKeyListener(tadapter);
    		retroCopter.removeMouseListener(madapter);
    	}
    	if (mp3 != null)
    		mp3.close();
    	timer.stop();
    }
    
    public void addNotify() {
        super.addNotify();
    }

    /*
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
    
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        update();
    	repaint(); 
    }
	
    public void update() {

    }
    */
    
    private class TAdapter extends KeyAdapter {
        @Override
    	public void keyReleased(KeyEvent e) {
            next();
        }
    }
    
    private class MAdapter extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            next();
        }
    }

	public void next() {
		retroCopter.next();
	}
}