package iut.ipi.runnergame.Game;

import android.content.Context;
import android.view.SurfaceHolder;

public class GameOverMaster extends Thread {
    public GameOverMaster(Context context, SurfaceHolder surfaceHolder) {

    }

    @Override
    public void run() {
        while(true) {
            update();
            draw();
        }
    }

    public void update() {

    }

    public void draw() {

    }
}
