package iut.ipi.runnergame.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

import iut.ipi.runnergame.Engine.Gfx.Hud.StatisticDistanceFlag;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Util.Point.PointRelative;

public class GameOverMaster extends Thread {
    private boolean isRunning = true;

    private SurfaceHolder holder;

    private StatisticDistanceFlag distanceFlag;

    public GameOverMaster(Context context, SurfaceHolder surfaceHolder, int playerDistance, int levelLength, float amplitude) {
        this.holder = surfaceHolder;

        distanceFlag = new StatisticDistanceFlag(context, R.drawable.sprite_flags_1, 3,8, playerDistance, levelLength, amplitude, new PointRelative(25, 50));
    }

    @Override
    public void run() {
        while(isRunning) {
            update();
            draw();
        }
    }

    public void kill() {
        isRunning = false;
    }

    public void update() {

    }

    public void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            if (canvas == null) return;

            canvas.drawColor(Color.DKGRAY);

            distanceFlag.drawOnCanvas(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }
}
