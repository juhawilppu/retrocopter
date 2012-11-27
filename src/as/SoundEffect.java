package as;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect {

    private URL url;
    private AudioInputStream audioIn;
    private Clip clip;
	
    public SoundEffect(String tiedosto) {
    	try {
    		url = this.getClass().getResource(tiedosto);
    		audioIn = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
	    	clip.open(audioIn);
    	} catch (UnsupportedAudioFileException e) {
	          e.printStackTrace();
	     } catch (IOException e) {
	          e.printStackTrace();
	     } catch (LineUnavailableException e) {
	          e.printStackTrace();
	     }
    }
    
    /*public void play() {
        if (clip.isRunning())
            clip.stop();   // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning

         clip.start();
    }*/
}
