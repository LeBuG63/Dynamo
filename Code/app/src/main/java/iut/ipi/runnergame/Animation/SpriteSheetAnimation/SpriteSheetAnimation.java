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

import iut.ipi.runnergame.Animation.IAnimationManager;

public class SpriteSheetAnimation implements IAnimationManager {
    private HashMap<Integer, List<Bitmap>> bitmapList = new HashMap<>();

    private final int resourceId;
    private final int frameWidth;
    private final int frameHeight;
    private final int totalFrames;
    private final int frameDuration;

    private final int row;
    private final int col;
    private final int scale;

    private AnimatorSet animatorSet;

    private boolean isPlaying = false;

    private int actualFrame = 0;
    private int actualRow = 0;

    private Timer timer;

    public SpriteSheetAnimation(Context context, int resourceId, int scale, int frameWidth, int frameHeight, int totalFrames, int frameDuration, int row, int col) throws IOException {
        this.resourceId = resourceId;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.totalFrames = totalFrames;
        this.frameDuration = frameDuration;
        this.row = row;
        this.col = col;
        this.scale = scale;

        cutSpritesheet(openSpritesheet(context));

        timer = new Timer();
    }

    private Bitmap openSpritesheet(Context context) throws IOException {
        Bitmap spritesheet = null;

        spritesheet = BitmapFactory.decodeResource(context.getResources(), resourceId);

        if(spritesheet == null) {
            throw new IOException("spritesheet not found: " + resourceId);
        }

        return spritesheet;
    }

    public void cutSpritesheet(Bitmap spritesheet) {
        for(int y = 0; y < row; ++y){
            bitmapList.put(y, new ArrayList<Bitmap>());

            for (int x = 0; x < col; ++x) {
                Bitmap frame = null;

                frame = Bitmap.createBitmap(spritesheet, getFrameWidth() * x, getFrameHeight() * y, getFrameWidth(), getFrameHeight());
                frame = Bitmap.createScaledBitmap(frame, getFrameWidth() * scale, getFrameHeight() * scale, false);

                bitmapList.get(y).add(frame);
            }
        }
    }

    @Override
    public void start(int animationIndex) {
        actualRow = animationIndex;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setNextFrameIndex();
            }
        }, 0, frameDuration);
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
        return bitmapList.get(actualRow).get(getFrameIndex());
    }

    @Override
    public Bitmap getPrevFrame() {
        setPrevFrameIndex();
        return bitmapList.get(actualRow).get(getFrameIndex());
    }

    @Override
    public Bitmap getFrame() {
        return bitmapList.get(actualRow).get(getFrameIndex());
    }

    @Override
    public int getFrameWidth() {
        return frameWidth;
    }

    @Override
    public int getFrameHeight() {
        return frameHeight;
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
