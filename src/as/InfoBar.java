package as;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class InfoBar {

	private int x;
	private int y;
	private int numberOfGuns;
	private String[] datanNimi = {"Health ", "Cannon ", "Missiles ", "Bombs ", "Laser ", "EMP"};
	private int[] data;
	private String[] selWeapons;
	
	public InfoBar(int x, int y, int numberOfGuns) {
		this.x=x;
		this.y=y;
		this.numberOfGuns = numberOfGuns;
		data = new int[numberOfGuns+1];
		selWeapons = new String[2];
	}
	
	public void paint(Graphics2D g2d, Game game) {
		Font small = new Font("Helvetica", Font.BOLD, 14);
		//FontMetrics metr = board.getFontMetrics(small);
		
	    g2d.setColor(Color.BLUE);
	    g2d.fillRect(0, y-20, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT-y+20);
	    
		g2d.setColor(Color.white);
		g2d.setFont(small);

		for (int i=0; i<numberOfGuns;i++) {
	    	g2d.drawString(datanNimi[i] + Integer.toString(data[i]), x+i*100, y);
    	}
		
	    g2d.setColor(Color.RED);
	    g2d.fillRect(0, y-40, Global.SCREEN_WIDTH, 20);
	    
		g2d.setColor(Color.white);
		g2d.setFont(small);

    	g2d.drawString("Primary Weapon " + selWeapons[0], x, y-25);
    	g2d.drawString("Secondary Weapon " + selWeapons[1], x+250, y-25);
	}

	public void update(int[] data, String[] selWeapons) {
		this.data = data;
		this.selWeapons = selWeapons;
	}
	
}
