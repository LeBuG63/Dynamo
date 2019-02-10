package iut.ipi.runnergame.Animation.SpriteSheetAnimation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Animation.IAnimationManager;

public class SpriteSheetAnimation implements IAnimationManager {
    private List<List<Bitmap>> bitmapList = new ArrayList<>();

    private final String animationPath;
    private int frameWidth;
    private int frameHeight;
    private int totalFrames;
    private double frameDuration;

    private int row;
    private int col;
    private int scale;

    private boolean isPlaying = false;

    private int actualFrame = 0;

    public SpriteSheetAnimation(String animationPath, int scale, int frameWidth, int frameHeight, int totalFrames, double frameDuration, int row, int col) throws IOException {
        this.animationPath = animationPath;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.totalFrames = totalFrames;
        this.frameDuration = frameDuration;
        this.row = row;
        this.col = col;
        this.scale = scale;
    }

    private Bitmap openSpritesheet(String path) throws IOException {
        Bitmap spritesheet = null;

        spritesheet = BitmapFactory.decodeFile(path);

        if(spritesheet == null) {
            throw new IOException("spritesheet not found: " + path);
        }

        return spritesheet;
    }

    public void cutSpritesheet(Bitmap spritesheet) {
        for(int y = 0; y < row; ++y){
            bitmapList.add(new ArrayList<Bitmap>());

            for (int x = 0; x < col; ++x) {
                Bitmap frame = null;

                frame = Bitmap.createBitmap(spritesheet, getFrameWidth() * x, getFrameHeight() * y, getFrameWidth(), getFrameHeight());
                frame = Bitmap.createScaledBitmap(frame, getFrameWidth() * scale, getFrameHeight() * scale, false);

                bitmapList.get(y).add(frame);
            }
        }
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
        return null;
    }

    @Override
    public Bitmap getPrevFrame() {
        return null;
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
    public double getFrameDuration() {
        return frameDuration;
    }
}
