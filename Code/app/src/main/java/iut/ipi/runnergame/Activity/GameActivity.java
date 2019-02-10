package iut.ipi.runnergame.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameActivity extends SurfaceView implements Runnable {
    Rect rect = new Rect(10, 100, 100, 100);

    private SurfaceHolder   holder = null;
    private Canvas          canvas = null;

    // volatile as it'll get modified in different thread
    private volatile boolean gamePlaying = true;

    public GameActivity(Context context) {
        super(context);

        holder = getHolder();
    }

    @Override
    public void run() {
        while(gamePlaying) {
            update();
            draw();
        }
    }

    public void update() {

    }

    public void draw() {
        if(holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            canvas.drawColor(Color.BLUE);

            holder.unlockCanvasAndPost(canvas);
        }
    }
}
