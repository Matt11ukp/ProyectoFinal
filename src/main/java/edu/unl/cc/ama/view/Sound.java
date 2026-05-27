package edu.unl.cc.ama.view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    private Clip clip;
    private URL soundURL[] = new URL[20]; // para guardar la direccion de los sonidos

    public Sound(){
        soundURL[0] = getClass().getResource("/sound/juego.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[6] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[7] = getClass().getResource("/sound/swing.wav");
        soundURL[8] = getClass().getResource("/sound/dash.wav");
        soundURL[9] = getClass().getResource("/sound/portal.wav");
        soundURL[10] = getClass().getResource("/sound/door.wav");
        soundURL[11] = getClass().getResource("/sound/menu.wav");
        soundURL[12] = getClass().getResource("/sound/muerte.wav");
        soundURL[13] = getClass().getResource("/sound/seleccionar.wav");
        soundURL[14] = getClass().getResource("/sound/deslizar.wav");
        soundURL[15] = getClass().getResource("/sound/muerteefectodesonido.wav");
    }
    public void setFile(SoundName name){
        try {
            int i = name.ordinal(); // convierte el enum a indices
            // Abrir un audio en java
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        if(clip != null){
            clip.stop();
        }
    }
}
