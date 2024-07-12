package main.utilits;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    private Clip clip;
    private final URL[] soundURL = new URL[10];
    private FloatControl fc;

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/music/dabella.wav");
        soundURL[1] = getClass().getResource("/sound/music/Aanguish.wav");
        soundURL[2] = getClass().getResource("/sound/music/DEATH-THE-LEVELLER.wav");
        soundURL[4] = getClass().getResource("/sound/effect/plst00.wav");
        soundURL[5] = getClass().getResource("/sound/effect/damage00.wav");
        soundURL[6] = getClass().getResource("/sound/effect/graze.wav");
        soundURL[7] = getClass().getResource("/sound/effect/tan00.wav");
        soundURL[8] = getClass().getResource("/sound/effect/pldead00.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            //fc.setValue(-10f);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
