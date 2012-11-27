package as;

import java.awt.Graphics2D;
import java.awt.Image;

public class Crosshair {
	private static String[] kuva = {"scope.png"};
    private static Kirjasto kirjasto = new Kirjasto();
    private static Image[] images = kirjasto.loadImages(kuva);
	private double distance;
	private double max;
	private double mouseY;
	private int width;
	private int height;
	
	public Crosshair() {
		distance=90;
		max = 100;
		width = images[0].getWidth(null);
		height = images[0].getHeight(null);
	}
	
	public void update(double mY) {
		mouseY = mY;
		if (mouseY > max)
			mouseY = max;
		else if (mouseY < -max)
			mouseY = -max;
		
		mouseY = (mouseY/max)*0.25*Math.PI;
	}

	public void paint(Graphics2D g2d, Game game, double craftX, double craftY) {
		g2d.drawImage(images[0], (int) (craftX + getDX(distance) - width/2), (int) (craftY + getDY(distance)- height/2), game);
	}

	public double getDY(double distance) {
		return distance*Math.sin(mouseY);
	}

	public double getDX(double distance) {
		return distance*Math.cos(mouseY);
	}
	
	public double getNormDY() {
		return getDY(1);
	}
	
}
