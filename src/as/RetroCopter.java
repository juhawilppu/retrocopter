package as;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RetroCopter extends JFrame {

	private static final long serialVersionUID = 1L;

	private CardLayout cl;
	private JPanel cardPanel;
	private int state=0;
	private Intro intro;
	private Game board;
	private KeyInfo controlInfo;
	private GameLogo gameLogo;
	private MainMenu mainMenu;
	private Credits credits;
	
	public RetroCopter() {
        
		setTitle("RetroCopter v0.6.3 by Borderlake Genius");
		setSize(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
        
        cardPanel = new JPanel();
		cl = new CardLayout();
		cardPanel.setLayout(cl);

		intro = new Intro(this);
		
		cardPanel.add(intro, "INTRO");
		
		getContentPane().add(cardPanel);
		intro.start();

	}

    public static void main(String[] args) {
        RetroCopter cg = new RetroCopter();
        cg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cg.setVisible(true);
    }
    
    public void next() {
    	state++;
    	if (state==1) {
    		intro.stop();
    		gameLogo = new GameLogo(this);
    		cardPanel.add(gameLogo, "GAMELOGO");
    		cl.show(cardPanel, "GAMELOGO");
    		cardPanel.remove(intro);
    		intro=null;
    		gameLogo.start();
    	} else if (state==2) {
    		gameLogo.stop();
    		controlInfo = new KeyInfo(this);
    		cardPanel.add(controlInfo, "CONTROLS");
    		cl.show(cardPanel, "CONTROLS");
    		cardPanel.remove(gameLogo);
    		gameLogo=null;
    		controlInfo.start();
    	} else if (state==3) {
    		newGame();
    	} else
    		quitGame();
    		/*controlInfo.stop();
    		mainMenu = new MainMenu(this);
    		cardPanel.add(mainMenu, "MENU");
    		cl.show(cardPanel, "MENU");
    		cardPanel.remove(controlInfo);
    		controlInfo=null;
    		mainMenu.start();
    	} else if (state>=4) {
    		board.stop();
    		cardPanel.add(mainMenu, "MENU");
    		cl.show(cardPanel, "MENU");
    		cardPanel.remove(board);
    		mainMenu.start();
    	}*/
    }

	public void newGame() {
		//mainMenu.stop();
		controlInfo.stop();
		board = new Game(this);
		cardPanel.add(board, "PELI");
		cl.show(cardPanel, "PELI");
		//cardPanel.remove(mainMenu);
		cardPanel.remove(controlInfo);
		board.start();
	}
	
	public void quitGame() {
		//mainMenu.stop();
		board.stop();
		credits = new Credits(this);
		cardPanel.add(credits, "CREDITS");
		cl.show(cardPanel, "CREDITS");
		//cardPanel.remove(mainMenu);
		cardPanel.remove(board);
		//mainMenu=null;
		credits.start();
	}
	
	public void quitImmediately() {
		System.exit(0);
	}
    
}
