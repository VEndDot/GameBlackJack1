import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicGame {

    public void playMusic(File soundFile, int n){
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
            clip.setFramePosition(0);
            clip.loop(n);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
