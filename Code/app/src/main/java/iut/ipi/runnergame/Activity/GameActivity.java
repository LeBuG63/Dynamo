package iut.ipi.runnergame.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import iut.ipi.runnergame.Animation.IAnimationManager;
import iut.ipi.runnergame.Animation.SpriteSheetAnimation.SpriteSheetAnimation;
import iut.ipi.runnergame.Entity.AEntity;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.R;

public class GameActivity extends SurfaceView implements Runnable {
    Rect rect = new Rect(10, 100, 100, 100);

    private SurfaceHolder   holder = null;
    private Canvas          canvas = null;

    private AEntity player;
    // volatile as it'll get modified in different thread
    private volatile boolean gamePlaying = true;

    public GameActivity(Context context) {
        super(context);

        try {
            player = new Player(new Point(100, 100), null, new SpriteSheetAnimation(context, R.drawable.sprite_player_1, 8, 32, 32, 4, 1000, 1, 4));;
        }
        catch(IOException e) {

        }

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

            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(player.getSprite(), 0, 0, new Paint());

            holder.unlockCanvasAndPost(canvas);
        }
    }
}