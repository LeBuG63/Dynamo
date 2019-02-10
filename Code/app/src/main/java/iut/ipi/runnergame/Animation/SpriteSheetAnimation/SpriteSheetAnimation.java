package iut.ipi.runnergame.Animation.SpriteSheetAnimation;

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Animation.IAnimationManager;

public class SpriteSheetAnimation implements IAnimationManager {
    private List<Bitmap> bitmapList = new ArrayList<>();

    private int frameWidth;
    private int frameHeight;
    private int totalFrames;
    private double frameDuration;

    private boolean isPlaying = false;

    public SpriteSheetAnimation(String animationPath, int frameWidth, int frameHeight, int totalFrames, double frameDuration) throws IOException {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.totalFrames = totalFrames;
        this.frameDuration = frameDuration;
    }


    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public void pause() {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getFrameId() {
        return 0;
    }

    @Override
    public int getCountFrames() {
        return 0;
    }

    @Override
    public Bitmap getNextFrame() {
        return null;
    }

    @Override
    public Bitmap getPrevFrame() {
        return null;
    }

    @Override
    public int getFrameWidth() {
        return 0;
    }

    @Override
    public int getFrameHeight() {
        return 0;
    }

    @Override
    public int getTotalFrames() {
        return 0;
    }

    @Override
    public double getFrameDuration() {
        return 0;
    }

}
