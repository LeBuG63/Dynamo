package iut.ipi.runnergame.Engine.Sfx.Sound;

import android.content.Context;
import android.media.MediaPlayer;

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

    public void release() {
        for(MediaPlayer mediaPlayer : mediaPlayers.values()) {
            mediaPlayer.release();
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
            mediaPlayers.get(soundNamePlaying).stop();
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
}
