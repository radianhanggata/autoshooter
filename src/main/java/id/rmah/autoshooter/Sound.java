package id.rmah.autoshooter;

import javafx.scene.media.AudioClip;

public class Sound {

    public static final AudioClip SHOOT;
    public static final AudioClip HIT;
        
    static {
        SHOOT = new AudioClip(Sound.class.getResource("/sfx/gunshot1.wav").toExternalForm());
        HIT = new AudioClip(Sound.class.getResource("/sfx/hit.mp3").toExternalForm());
    }

    public static void warmUp() {
        SHOOT.setVolume(0.01);
        SHOOT.play();
        SHOOT.setVolume(0.05);
        HIT.setVolume(0.03);
    }
}
