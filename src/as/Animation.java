package as;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Animation {
    
	private int anim_tila;
	private Image[] images;
	private Image[] destrImages;
	private BufferedImage[] bufferedImages;
	private int width;
	private int height;
	private int spritejenMaara;
	private int destrImagesMaara;
	private double changeSpeed;
	private int changes;
    private boolean repeating;
    private boolean isOver;
	
	public Animation(Image[] images, BufferedImage[] bufferedImages, boolean repeating, int changeSpeed, boolean bufImages) {
		this.repeating = repeating;
		spritejenMaara = images.length;
		isOver = false;
		this.changeSpeed=changeSpeed;
		changes=0;

		this.images = images;
		this.bufferedImages = bufferedImages;
        	
        width = images[0].getWidth(null);
        height = images[0].getHeight(null);

        anim_tila=1;
	}

	public Animation(Image[] images, BufferedImage[] bufferedImages,
			Image[] destrImages, boolean repeating, int changeSpeed, boolean bufImages) {
		this(images, bufferedImages, repeating, changeSpeed, bufImages);
		this.destrImages=destrImages;
	}

	public void update() {
		changes++;
		
		if (changes >= changeSpeed) {
			changes=0;
			anim_tila++;
		
			if (anim_tila >= spritejenMaara) {
				if (repeating)
					anim_tila = 0;	
				else
					isOver=true;
			}
		}
	}

	public BufferedImage getBufImage() {
		return bufferedImages[anim_tila];
	}

	public Image getImage() {
		return images[anim_tila];
	}

	public void multiplyChangeSpeed(double factor) {
		changeSpeed *= factor;
	}
	
	public void changeToDestrImages() {
		images=destrImages;
		spritejenMaara=destrImagesMaara;
		anim_tila=0;
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}

	public boolean isOver() {
		return isOver;
	}
	
}
