package iut.ipi.runnergame.Animation.SpriteSheetAnimation;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import iut.ipi.runnergame.Animation.AnimationManager;
import iut.ipi.runnergame.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Util.BitmapResizer;

public class BaseSpriteSheetAnimation implements AnimationManager {
    private HashMap<Integer, Integer> durationMap = new HashMap<>();

    private final int totalFrames;
    private final int frameDuration;

    private final int row;
    private final int col;
    private final int scale;

    private boolean isPlaying = false;

    private int actualFrame = 0;
    private int actualRow = 0;

    private Timer timer;

    private Spritesheet spritesheet;

    public BaseSpriteSheetAnimation(Context context, int resourceId, int scale, int frameWidth, int frameHeight, int totalFrames, int frameDuration, int row, int col) throws IOException {
        this.totalFrames = totalFrames;
        this.frameDuration = frameDuration;
        this.row = row;
        this.col = col;
        this.scale = scale;

        spritesheet = new Spritesheet(context, resourceId, row, col, frameWidth, frameHeight, scale);
        timer = new Timer();

        for(int i = 0; i < row; ++i) {
            durationMap.put(i, frameDuration);
        }
    }


    @Override
    public void start(int animationIndex) {
        if(animationIndex != actualRow) {
            actualRow = animationIndex;
            actualFrame = 0;

            timer.cancel();
            timer.purge();

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setNextFrameIndex();
                }
            }, 0, durationMap.get(actualFrame));
        }
    }

    @Override
    public void setDurationFrame(int i, int duration) {
        durationMap.put(i, duration);
    }

    public int getFrameIndex() {
        return actualFrame;
    }

    public void setNextFrameIndex() {
        if(actualFrame + 1 >= col) {
            actualFrame = 0;
        }

        actualFrame++;
    }

    public void setPrevFrameIndex() {
        if(actualFrame - 1 < 0) {
            actualFrame = col - 1;
        }

        actualFrame--;
    }


    @Override
    public void end() {
        timer.cancel();
    }

    @Override
    public void pause() {

    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public int getFrameId() {
        return actualFrame;
    }

    @Override
    public int getCountFrames() {
        return getTotalFrames();
    }

    @Override
    public Bitmap getNextFrame() {
        setNextFrameIndex();
        return spritesheet.getSprites().get(actualRow).get(getFrameIndex());
    }

    @Override
    public Bitmap getPrevFrame() {
        setPrevFrameIndex();
        return spritesheet.getSprites().get(actualRow).get(getFrameIndex());
    }

    @Override
    public Bitmap getFrame() {
        return spritesheet.getSprites().get(actualRow).get(getFrameIndex());
    }

    @Override
    public int getTotalFrames() {
        return totalFrames;
    }

    @Override
    public int getFrameDuration() {
        return frameDuration;
    }
}
