package iut.ipi.runnergame.Game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

import iut.ipi.runnergame.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Entity.Plateform.PlateformManager;
import iut.ipi.runnergame.Entity.Plateform.PlateformType;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Entity.Shadow.ShadowManager;
import iut.ipi.runnergame.Hud.Cross;
import iut.ipi.runnergame.Hud.Input.BaseCrossClickable;
import iut.ipi.runnergame.Physics.PhysicsManager;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Util.Point.AbstractPoint;
import iut.ipi.runnergame.Util.Point.Point;
import iut.ipi.runnergame.Util.Point.PointAdjusted;
import iut.ipi.runnergame.Util.Point.PointRelative;
import iut.ipi.runnergame.Util.WindowDefinitions;
import iut.ipi.runnergame.Util.WindowUtil;

public class GameManager extends Thread {
    private final AbstractPoint defaultPointCross = new PointRelative(10, 50);

    private AbstractPoint pointFingerPressed = new Point();

    private SurfaceHolder holder = null;

    private Player player;
    private Cross cross;

    private ShadowManager shadowManager;
    private PlateformManager plateformManager;

    // volatile as it'll get modified in different thread
    private volatile boolean gamePlaying = true;

    public GameManager(Context context, SurfaceHolder surfaceHolder) {
        shadowManager = new ShadowManager(context,  WindowUtil.convertPixelsToDp(5), WindowUtil.convertPixelsToDp(15), Color.WHITE);
        plateformManager = new PlateformManager(context);
        cross = new BaseCrossClickable(context, R.drawable.sprite_cross_1, BaseCrossClickable.DEFAULT_SCALE, defaultPointCross);

        try {
            player = new Player(new PointRelative(50, 0), new BaseSpriteSheetAnimation(context, R.drawable.sprite_player_1, Player.DEFAULT_SCALE, 4, Player.DEFAULT_FRAME_DURATION, 3, 4));

            plateformManager.add(PlateformType.SIMPLE, new PointAdjusted(0, 300 ), 200);
            plateformManager.add(PlateformType.SIMPLE, new PointAdjusted(200, 250 ), 20);
            plateformManager.add(PlateformType.FROZEN, new PointAdjusted(600, 200 ), 20);

            player.getAnimationManager().setDurationFrame(Player.ANIMATION_RUNNING_LEFT, 100);
            player.getAnimationManager().setDurationFrame(Player.ANIMATION_RUNNING_RIGHT, 100);
        }
        catch(IOException e) {

        }

        holder = surfaceHolder;
    }

    public void updatePosition() {
        AbstractPoint.resizePointsInPool();
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

        cross.updateArrowPressed(pointFingerPressed);

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

        plateformManager.translate(player.getPosition().x - Player.DEFAULT_X_POS, 0);
        shadowManager.update();

        PhysicsManager.updatePlayerPosition(player, plateformManager.getPlateforms(),(float)res/1000.0f);

        last = now;
    }

    public void draw() {

        if(holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            if(canvas == null) return;

            Paint p = new Paint();
            Paint p2 = new Paint();
            Paint circle = new Paint();

            circle.setColor(Color.BLUE);
            p.setColor(Color.GREEN);
            p2.setColor(Color.RED);

            Paint paint = new Paint();

            paint.setDither(false);
            paint.setAntiAlias(false);
            paint.setFilterBitmap(false);

            canvas.drawColor(Color.DKGRAY);

            plateformManager.drawPlateformOnCanvas(canvas);

            canvas.drawBitmap(player.getSprite(), Player.DEFAULT_X_POS, player.getPosition().y, new Paint());

            cross.drawRectOnCanvas(canvas, p, p2);
            cross.drawOnCanvas(canvas);
            shadowManager.drawShadowToCanvas(canvas, player);

            Log.d("canvas", "h "  + String.valueOf(canvas.getHeight()));
            Log.d("canvas", "w "+ String.valueOf(canvas.getWidth()));

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setPointFingerPressed(float x, float y) {
        pointFingerPressed.x = x;
        pointFingerPressed.y = y;
    }

}
