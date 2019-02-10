package iut.ipi.runnergame.Animation;

import android.graphics.Bitmap;

public interface IAnimationManager {
    int getFrameWidth();
    int getFrameHeight();
    int getTotalFrames();
    int getFrameDuration();

    void start(int i);
    void end();
    void pause();

    boolean isPlaying();

    int getFrameId();
    int getCountFrames();

    Bitmap getNextFrame();
    Bitmap getPrevFrame();
    Bitmap getFrame();
}
