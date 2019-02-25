package iut.ipi.runnergame.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Activity.GameActivity;
import iut.ipi.runnergame.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Entity.Plateform.PlateformManager;
import iut.ipi.runnergame.Entity.Plateform.PlateformType;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Entity.Shadow.ShadowManager;
import iut.ipi.runnergame.Hud.AbstractCross;
import iut.ipi.runnergame.Hud.Input.BaseCrossClickable;
import iut.ipi.runnergame.Physics.PhysicsManager;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Util.Point.AbstractPoint;
import iut.ipi.runnergame.Util.Point.PointAdjusted;
import iut.ipi.runnergame.Util.Point.PointRelative;
import iut.ipi.runnergame.Util.WindowDefinitions;
import iut.ipi.runnergame.Util.WindowUtil;

public class GameMaster extends Thread {
    private final AbstractPoint defaultPointCross = new PointRelative(10, 50);
    private final AbstractPoint defaultPointCrossAB = new PointRelative(90, 50);
    private final AbstractPoint defaultPointPlayer = new PointRelative(50, 0);

    private List<AbstractPoint> pointFingerPressed = new ArrayList<>();

    private SurfaceHolder holder = null;

    private Player player;
    private AbstractCross cross;
    private AbstractCross crossAB;

    private ShadowManager shadowManager;
    private PlateformManager plateformManager;

    public GameMaster(Context context, SurfaceHolder surfaceHolder) {
        plateformManager = new PlateformManager(context);
        cross = new BaseCrossClickable(context, R.drawable.sprite_cross_1, BaseCrossClickable.DEFAULT_SCALE, 4, defaultPointCross);
        crossAB = new BaseCrossClickable(context, R.drawable.sprite_cross_ab, BaseCrossClickable.DEFAULT_SCALE, 2, defaultPointCrossAB);

        try {
            player = new Player(defaultPointPlayer, new BaseSpriteSheetAnimation(context, R.drawable.sprite_player_1, Player.DEFAULT_SCALE, 4, Player.DEFAULT_FRAME_DURATION, 3, 4));
            shadowManager = new ShadowManager(context, player, WindowUtil.convertPixelsToDp(5), WindowUtil.convertPixelsToDp(15), Color.WHITE);

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
        while(true) {
            update();
            draw();
        }
    }

    private long last = System.currentTimeMillis();
    public void update() {
        long now = System.currentTimeMillis();
        long res = now - last;
        boolean idle = true;

        cross.updateArrowPressed(pointFingerPressed);
        crossAB.updateArrowPressed(pointFingerPressed);

        if(cross.getArrowTop().getIsClicked() || crossAB.getArrowTop().getIsClicked()) {
            player.jump(Player.IMPULSE_JUMP);
            idle = false;
        }
        if(cross.getArrowLeft().getIsClicked()) {
            player.moveLeft(Player.IMPULSE_MOVEMENT);
            player.getAnimationManager().start(Player.ANIMATION_RUNNING_LEFT);
            idle = false;
        }
        if(cross.getArrowRight().getIsClicked()) {
            player.moveRight(Player.IMPULSE_MOVEMENT);
            player.getAnimationManager().start(Player.ANIMATION_RUNNING_RIGHT);
            idle = false;
        }
        if(idle){
            player.getAnimationManager().start(Player.ANIMATION_IDLE);
        }

        plateformManager.translate(player.getPosition().x - Player.DEFAULT_POS.x, 0);
        shadowManager.update();

        PhysicsManager.updatePlayerPosition(player, plateformManager.getPlateforms(),(float)res/1000.0f);

        if(player.isDead()) {
            player.setDeath(false);
            player.setPosition(defaultPointPlayer);
            GameActivity.launchLoseActivity(new GameOverDataBundle(GameActivity.strTimer, 100));
        }

        last = now;
    }

    public void draw() {
        if(holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            if(canvas == null) return;

            if(canvas.getHeight() != WindowDefinitions.HEIGHT) {
                WindowDefinitions.HEIGHT = canvas.getHeight();
                WindowDefinitions.WIDTH = canvas.getWidth();
                updatePosition();
            }

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

            canvas.drawBitmap(player.getSprite(), Player.DEFAULT_POS.x, player.getPosition().y, new Paint());

            cross.drawOnCanvas(canvas);
            crossAB.drawOnCanvas(canvas);

            shadowManager.drawOnCanvas(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setPointsFingerPressed(AbstractPoint[] points) {
        pointFingerPressed.clear();

        if(points == null) return;

        for(int index = 0; index < points.length; ++index) {
            pointFingerPressed.add(points[index]);
        }
    }
}
