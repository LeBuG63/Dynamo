package iut.ipi.runnergame.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

import iut.ipi.runnergame.Engine.Graphics.Hud.StatisticDistanceFlag;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.PointRelative;
import iut.ipi.runnergame.Game.Level.Background.Background;
import iut.ipi.runnergame.Game.Level.Background.StarsBackground;
import iut.ipi.runnergame.R;

public class GameOverMaster extends Thread {
    private boolean isRunning = true;

    private SurfaceHolder holder;

    private Background background;
    private StatisticDistanceFlag distanceFlag;

    public GameOverMaster(Context context, SurfaceHolder surfaceHolder, int playerDistance, int levelLength, float amplitude) {
        this.holder = surfaceHolder;

        AbstractPoint.clearPoolPoints();

        background = new StarsBackground(context, 30);
        distanceFlag = new StatisticDistanceFlag(context, R.drawable.sprite_flags_1, 3,8, playerDistance, levelLength, amplitude, new PointRelative(25, 50));
    }

    @Override
    public void run() {
        while(isRunning) {
            update(1.0f/60.0f);
            draw();
        }
    }

    public void kill() {
        isRunning = false;
        AbstractPoint.clearPoolPoints();
    }

    public void update(float dt) {
        background.update(dt);
    }

    public void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            if (canvas == null) return;

            canvas.drawColor(Color.BLACK);

            background.drawOnCanvas(canvas);
            distanceFlag.drawOnCanvas(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }
}
