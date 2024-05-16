package Graphics;

import Constants.GameConstants;

import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.sound.sampled.*;

public class SoundManager {
    private HashMap<String, Clip> sounds = new HashMap<>();
    private Clip currentClip;

    public SoundManager() {
        // Initialize sounds with the audio clips
        loadSound("menu", GameConstants.MENU_MUSIC_FILEPATH);
        loadSound("game", GameConstants.GAME_MUSIC_FILEPATH);
        loadSound("endgame", GameConstants.ENDGAME_MUSIC_FILEPATH);
    }

    // Method to load a sound file and put it in the sounds' hashmap
    private void loadSound(String key, String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            sounds.put(key, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to play a specific sound repeatedly
    public void playSoundLooped(String key) {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.setFramePosition(0); // Rewind to the beginning
        }
        currentClip = sounds.get(key);
        if (currentClip != null) {
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            currentClip.start();
        }
    }

    // Method to play a specific sound once
    public void playSoundOnce(String key) {
        Clip clip = sounds.get(key);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    // Method to stop the current sound
    public void stopSound() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }
}
