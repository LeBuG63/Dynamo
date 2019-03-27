package iut.ipi.runnergame.Engine.Sfx.Sound;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.HashMap;

public abstract class AbstractAudioPlayer {
    protected HashMap<String, MediaPlayer> mediaPlayers = new HashMap<>();
    protected String soundNamePlaying = new String();

    private Context context;

    protected void setContext(Context context) {
        this.context = context;
    }

    /**
     * ajoute une musique
     * @param soundName le nom de la musique
     * @param resourcesId la resource de la musique
     */
    public void add(String soundName, int resourcesId) {
        mediaPlayers.put(soundName, MediaPlayer.create(context, resourcesId));
    }

    /**
     * ajoute une musique qui peut tourner en boucle
     * @param soundName le nom de la musique
     * @param resourcesId la resource de la musique
     * @param looping si elle tourne en boucle ou pas
     */
    public void add(String soundName, int resourcesId, boolean looping) {
        add(soundName, resourcesId);
        if(mediaPlayers.containsKey(soundName)) {
            mediaPlayers.get(soundName).setLooping(looping);
        }
    }

    /**
     * libere la musique
     */
    public void release() {
        for(MediaPlayer mediaPlayer : mediaPlayers.values()) {
//          mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public void setLooping(String soundName, boolean value) {
        if(mediaPlayers.containsKey(soundName)) {
            mediaPlayers.get(soundName).setLooping(value);
        }
    }

    /**
     * joue la musique
     * @param soundName le nom du son a jouer
     */
    public void play(String soundName) {
        if(mediaPlayers.containsKey(soundName)) {
            soundNamePlaying = soundName;
            mediaPlayers.get(soundName).start();
        }
    }

    /**
     * met en pause le son qui joue
     */
    public void pause() {
        MediaPlayer player = mediaPlayers.get(soundNamePlaying);

        if (player.isPlaying()) {
            mediaPlayers.get(soundNamePlaying).pause();
        } else {
            mediaPlayers.get(soundNamePlaying).start();
        }
    }

    /**
     * stop la musique
     */
    public void stop() {
        if(!soundNamePlaying.isEmpty()) {
            mediaPlayers.get(soundNamePlaying).release();
        }
    }

    /**
     * joue jusqu a ce que la musique soit fini, la musique est impossible a arreter
     * @param soundName
     */
    public void playUntilFinished(String soundName) {
        if(mediaPlayers.containsKey(soundName)) {
            MediaPlayer mediaPlayer = mediaPlayers.get(soundName);

            if (!mediaPlayer.isPlaying()) {
                soundNamePlaying = soundName;
                mediaPlayer.start();
            }
        }
    }

    public boolean musicFinished() {
        return mediaPlayers.get(soundNamePlaying).isPlaying();
    }

    public void playLast() {
        play(soundNamePlaying);
    }
}
