package iut.ipi.runnergame.Engine.Gfx.Animation.SpriteSheetAnimation;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import iut.ipi.runnergame.Engine.Gfx.Animation.AnimationManager;
import iut.ipi.runnergame.Engine.Gfx.Spritesheet.Spritesheet;

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
    private boolean firstTimeLaunched = true;

    public BaseSpriteSheetAnimation(Context context, int resourceId, int scale, int totalFrames, int frameDuration, int row, int col) throws IOException {
        this(context, resourceId, scale, Spritesheet.DEFAULT_SPRITE_SIZE, Spritesheet.DEFAULT_SPRITE_SIZE, totalFrames, frameDuration, row, col);
    }

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

    public void start(int animationIndex) {
        if(animationIndex != actualRow || firstTimeLaunched) {
            firstTimeLaunched = false;

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
            }, 0, durationMap.get(actualRow));
        }
    }

    public void setDurationFrame(int i, int duration) {
        durationMap.put(i, duration);
    }

    public int getFrameIndex() {
        return actualFrame;
    }

    public void setNextFrameIndex() {
        if(actualFrame + 1 >= col) {
            actualFrame = -1;
        }

        actualFrame++;
    }

    public void setPrevFrameIndex() {
        if(actualFrame - 1 < 0) {
            actualFrame = col;
        }

        actualFrame--;
    }


    public void end() {
        timer.cancel();
    }

    public void pause() {

    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public int getFrameId() {
        return actualFrame;
    }

    public int getCountFrames() {
        return getTotalFrames();
    }

    public Bitmap getNextFrame() {
        setNextFrameIndex();
        return getFrame();
    }

    public Bitmap getPrevFrame() {
        setPrevFrameIndex();
        return getFrame();
    }

    public Bitmap getFrame() {
        return spritesheet.getSprite(actualRow, getFrameIndex());
    }

    public int getTotalFrames() {
        return totalFrames;
    }

    public int getFrameDuration() {
        return frameDuration;
    }
}
