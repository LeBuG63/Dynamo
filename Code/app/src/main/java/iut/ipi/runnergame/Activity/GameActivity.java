package iut.ipi.runnergame.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Entity.Plateform.AbstractPlateform;
import iut.ipi.runnergame.Entity.Plateform.TypesPlateform.SimplePlateform;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Hud.Cross;
import iut.ipi.runnergame.Hud.Input.BaseCrossClickable;
import iut.ipi.runnergame.Physics.PhysicsManager;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Util.WindowDefinitions;

public class GameActivity extends SurfaceView implements Runnable {
    private ConstraintLayout constraintLayout;

    private SurfaceHolder   holder = null;
    private Canvas          canvas = null;

    private Player player;
    private Cross cross;

    private PointF pointClicked = new PointF();

    private List<AbstractPlateform> plateforms = new ArrayList<>();

    // volatile as it'll get modified in different thread
    private volatile boolean gamePlaying = true;

    public GameActivity(Context context) {
        super(context);

        cross = new BaseCrossClickable(context, R.drawable.sprite_cross_1, Spritesheet.DEFAULT_SPRITE_SIZE,Spritesheet.DEFAULT_SPRITE_SIZE, 4);

        try {
            player = new Player(context, new PointF(100, 100), 6);

            plateforms.add(new SimplePlateform(context, R.drawable.sprite_simple_plateform_1, new PointF(850, 200), 5, AbstractPlateform.DEFAULT_SCALE));
            plateforms.add(new SimplePlateform(context, R.drawable.sprite_simple_plateform_1, new PointF(0, WindowDefinitions.heightPixels - 150), 10, AbstractPlateform.DEFAULT_SCALE));
            plateforms.add(new SimplePlateform(context, R.drawable.sprite_simple_plateform_1, new PointF(500, WindowDefinitions.heightPixels - 300), 2, AbstractPlateform.DEFAULT_SCALE));

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

    private long last = System.currentTimeMillis();
    public void update() {

        long now = System.currentTimeMillis();
        long res = now - last;

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

        PhysicsManager.updatePlayerPosition(player, plateforms,(float)res/1000.0f);

        last = now;
    }

    public void draw() {
        if(holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            if(canvas == null) return;

            Paint p = new Paint();
            Paint p2 = new Paint();

            p.setColor(Color.GREEN);
            p2.setColor(Color.RED);

            Paint paint = new Paint();

            paint.setDither(false);
            paint.setAntiAlias(false);
            paint.setFilterBitmap(false);

            canvas.drawColor(Color.DKGRAY);
            canvas.drawBitmap(player.getSprite(), player.getPosition().x, player.getPosition().y, paint);


            for(AbstractPlateform plateform : plateforms) {
                plateform.drawOnCanvas(canvas);
            }

            cross.drawRectOnCanvas(canvas, p, p2);
            cross.drawOnCanvas(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }
}