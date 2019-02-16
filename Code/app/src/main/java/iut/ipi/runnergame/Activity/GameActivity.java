package iut.ipi.runnergame.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

import iut.ipi.runnergame.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Movable;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Hud.Input.BaseCrossClickable;
import iut.ipi.runnergame.Hud.Cross;
import iut.ipi.runnergame.R;

public class GameActivity extends SurfaceView implements Runnable {
    private ConstraintLayout constraintLayout;

    private SurfaceHolder   holder = null;
    private Canvas          canvas = null;

    private AbstractEntity player;
    private Cross cross;

    private PointF pointClicked = new PointF();

    // volatile as it'll get modified in different thread
    private volatile boolean gamePlaying = true;

    public GameActivity(Context context) {
        super(context);

        cross = new BaseCrossClickable(context, R.drawable.sprite_cross_1, 32,32, 2);

        try {
            player = new Player(new PointF(100, 100), null, new BaseSpriteSheetAnimation(context, R.drawable.sprite_player_1, 4, 32, 32, 4, 1000, 3, 4));;

            player.getAnimationManager().setDurationFrame(Player.ANIMATION_RUNNING_LEFT, 100);
            player.getAnimationManager().setDurationFrame(Player.ANIMATION_RUNNING_RIGHT, 100);
        }
        catch(IOException e) {

        }

        holder = getHolder();

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getRootView().performClick();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pointClicked.x = event.getX();
                    pointClicked.y = event.getY();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    pointClicked.x = -1;
                    pointClicked.y = -1;
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
        cross.updateArrowPressed(pointClicked);

        if(cross.getArrowTop().getIsClicked()) {
            player.jump(Player.IMPULSE_JUMP);
        }
        else if(cross.getArrowLeft().getIsClicked()) {
            player.moveLeft(Player.IMPULSE_MOVEMENT);
            player.getAnimationManager().start(Player.ANIMATION_RUNNING_LEFT);
        }
        else if(cross.getArrowRight().getIsClicked()) {
            player.moveRight(Player.IMPULSE_MOVEMENT);
            player.getAnimationManager().start(Player.ANIMATION_RUNNING_RIGHT);
        }
        else {
            player.getAnimationManager().start(Player.ANIMATION_IDLE);

        }

        player.updatePoisition();
    }

    public void draw() {
        if(holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            if(canvas == null) return;
            
            Paint p = new Paint();
            Paint p2 = new Paint();

            p.setColor(Color.GREEN);
            p2.setColor(Color.RED);

            canvas.drawColor(Color.DKGRAY);
            canvas.drawBitmap(player.getSprite(), player.getPosition().x, player.getPosition().y, new Paint());

            cross.drawRectOnCanvas(canvas, p, p2);
            cross.drawOnCanvas(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }
}