package iut.ipi.runnergame.Engine.Sfx.Sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.HashMap;

public abstract class AbstractPlayer {
    protected HashMap<String, MediaPlayer> mediaPlayers = new HashMap<>();
    protected String soundNamePlaying;

    private Context context;

    protected void setContext(Context context) {
        this.context = context;
    }

    public void add(String soundName, int resourcesId) {
        mediaPlayers.put(soundName, MediaPlayer.create(context, resourcesId));
    }

    public void add(String soundName, int resourcesId, boolean looping) {
        add(soundName, resourcesId);
        if(mediaPlayers.containsKey(soundName)) {
            mediaPlayers.get(soundName).setLooping(looping);
        }
    }

    public void release() {
        for(MediaPlayer mediaPlayer : mediaPlayers.values()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public void setLooping(String soundName, boolean value) {
        if(mediaPlayers.containsKey(soundName)) {
            mediaPlayers.get(soundName).setLooping(value);
        }
    }

    public void play(String soundName) {
        if(mediaPlayers.containsKey(soundName)) {
            soundNamePlaying = soundName;
            mediaPlayers.get(soundName).start();
        }
    }

    public void stop() {
        if(!soundNamePlaying.isEmpty()) {
            mediaPlayers.get(soundNamePlaying).release();
        }
    }

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
