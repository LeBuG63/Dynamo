package iut.ipi.runnergame.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

import iut.ipi.runnergame.Animation.SpriteSheetAnimation.SpriteSheetAnimation;
import iut.ipi.runnergame.Entity.AEntity;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Hud.Cross.CrossClickable;
import iut.ipi.runnergame.Hud.ICross;
import iut.ipi.runnergame.R;

public class GameActivity extends SurfaceView implements Runnable {
    private ConstraintLayout constraintLayout;

    private SurfaceHolder   holder = null;
    private Canvas          canvas = null;

    private AEntity player;
    private ICross square;

    private Point pointClicked = new Point();

    // volatile as it'll get modified in different thread
    private volatile boolean gamePlaying = true;

    public GameActivity(Context context) {
        super(context);

        try {
            player = new Player(new Point(100, 100), null, new SpriteSheetAnimation(context, R.drawable.sprite_player_1, 8, 32, 32, 4, 1000, 1, 4));;
            square = new CrossClickable(new Point(300, 600), 100);
        }
        catch(IOException e) {

        }

        holder = getHolder();

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    pointClicked = new Point(x, y);
                }
                return true;
            }
        });
    }

    @Override
    public void run() {
        while(gamePlaying) {
            update();
            draw();
        }
    }

    public void update() {
        square.updateArrowPressed(pointClicked);
    }

    public void draw() {
        if(holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            Paint p = new Paint();
            Paint p2 = new Paint();

            p.setColor(Color.GREEN);
            p2.setColor(Color.RED);

            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(player.getSprite(), 0, 0, new Paint());

            square.drawRectOnCanvas(canvas, p, p2);

            holder.unlockCanvasAndPost(canvas);
        }
    }
}