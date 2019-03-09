package iut.ipi.runnergame.Game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.StateSet;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import iut.ipi.runnergame.Activity.GameActivity;
import iut.ipi.runnergame.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Entity.Gameplay.PieceManager;
import iut.ipi.runnergame.Entity.Gameplay.PieceType;
import iut.ipi.runnergame.Entity.Plateform.PlateformManager;
import iut.ipi.runnergame.Entity.Plateform.PlateformType;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Entity.Shadow.ShadowManager;
import iut.ipi.runnergame.Entity.Gameplay.Piece;
import iut.ipi.runnergame.Hud.AbstractCross;
import iut.ipi.runnergame.Hud.Input.BaseCrossClickable;
import iut.ipi.runnergame.Physics.CollisionManager;
import iut.ipi.runnergame.Physics.PhysicsManager;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Sound.AbstractPlayer;
import iut.ipi.runnergame.Sound.SoundEffectPlayer;
import iut.ipi.runnergame.Util.Point.AbstractPoint;
import iut.ipi.runnergame.Util.Point.PointAdjusted;
import iut.ipi.runnergame.Util.Point.PointRelative;
import iut.ipi.runnergame.Util.TranslateUtil;
import iut.ipi.runnergame.Util.WindowDefinitions;
import iut.ipi.runnergame.Util.WindowUtil;

public class GameMaster extends Thread {
    private final long FPS = 60L;
    private final long FRAME_PERIOD = 1000L / FPS;

    private final AbstractPoint defaultPointCross = new PointRelative(10, 50);
    private final AbstractPoint defaultPointCrossAB = new PointRelative(90, 50);
    private final AbstractPoint defaultPointPlayer = new PointRelative(50, 0);

    private List<AbstractPoint> pointFingerPressed = new ArrayList<>();

    private SurfaceHolder holder = null;

    private Player player;
    private AbstractCross cross;
    private AbstractCross crossAB;

    private AbstractPlayer sfxPlayer;

    private ShadowManager shadowManager;
    private PlateformManager plateformManager;
    private PieceManager pieceManager;

    private TranslateUtil translateUtil = new TranslateUtil();

    private boolean isRunning = true;
    private boolean paused = false;
    private Object pauseKey = new Object();

    public GameMaster(Context context, SurfaceHolder surfaceHolder) {
        plateformManager = new PlateformManager(context);
        cross = new BaseCrossClickable(context, R.drawable.sprite_cross_1, BaseCrossClickable.DEFAULT_SCALE, 4, defaultPointCross);
        crossAB = new BaseCrossClickable(context, R.drawable.sprite_cross_ab, BaseCrossClickable.DEFAULT_SCALE, 2, defaultPointCrossAB);

        sfxPlayer = new SoundEffectPlayer(context);
        sfxPlayer.add("footsteps", R.raw.sfx_jump);

        try {
            player = new Player(defaultPointPlayer, new BaseSpriteSheetAnimation(context, R.drawable.sprite_player_1, Player.DEFAULT_SCALE, 4, Player.DEFAULT_FRAME_DURATION, 3, 4));
            shadowManager = new ShadowManager(context, player, WindowUtil.convertPixelsToDp(5), WindowUtil.convertPixelsToDp(15), Color.WHITE);
            pieceManager = new PieceManager(context);

            pieceManager.add(PieceType.LOW, new PointAdjusted(150, 200));
            pieceManager.add(PieceType.NORMAL, new PointAdjusted(200, 200));
            pieceManager.add(PieceType.HIGH, new PointAdjusted(250, 200));

            plateformManager.add(PlateformType.SIMPLE, new PointAdjusted(0, 300 ), 6);
            plateformManager.add(PlateformType.SIMPLE, new PointAdjusted(-100, 200 ), 6);
            plateformManager.add(PlateformType.SIMPLE, new PointAdjusted(200, 250 ), 10);
            plateformManager.add(PlateformType.FROZEN, new PointAdjusted(600, 200 ), 10);

            player.getAnimationManager().setDurationFrame(Player.ANIMATION_RUNNING_LEFT, 100);
            player.getAnimationManager().setDurationFrame(Player.ANIMATION_RUNNING_RIGHT, 100);
        }
        catch(IOException ignored) {

        }

        holder = surfaceHolder;
    }

    public void updatePoolPoints() {
        AbstractPoint.resizePointsInPool();
    }

    public void pauseUpdate() {
        paused = true;
    }

    public void resumeUpdate() {
        synchronized (pauseKey) {
            paused = false;
            pauseKey.notifyAll();
        }
    }

    public void stopUpdate() {
        isRunning = false;
    }

    private long time = System.currentTimeMillis();
    public void waitUntilNeededUpdate(long start) {
        long now = System.currentTimeMillis();

        if(now - time >= 1000L) // 1 seconde
            time = now;

        long sleepTime = FRAME_PERIOD - (now - start); // il faut endormir le thread si il ne fait rien

        if(sleepTime >= 0L) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {

            }
        }
    }

    @Override
    public void run() {
        while(isRunning) {
            synchronized (pauseKey) {
                if(paused) {
                    try {
                        pauseKey.wait();
                    } catch (InterruptedException e) {
                        break;
                    }

                    // en attendant, isRunning peut avoir changé
                    if(!isRunning) {
                        break;
                    }
                }

                long start = System.currentTimeMillis();

                update(1.0f/FPS);
                draw();

                waitUntilNeededUpdate(start);
            }
        }
    }

    public void kill() {
        isRunning = false;
    }

    public void update(float dt) {
        boolean idle = true;

        cross.updateArrowPressed(pointFingerPressed);
        crossAB.updateArrowPressed(pointFingerPressed);

        if(cross.getArrowTop().getIsClicked() || crossAB.getArrowTop().getIsClicked()) {
            idle = false;

            if(player.isOnGround()) {
                sfxPlayer.playUntilFinished("footsteps");
                player.setHasAnotherJump(true);
            }

            player.jump(Player.IMPULSE_JUMP);
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

        translateUtil.translateListObject(plateformManager.getPlateforms(), player.getPosition().x - Player.DEFAULT_POS.x, 0);
        translateUtil.translateListObject(pieceManager.getPieces(), player.getPosition().x - Player.DEFAULT_POS.x, 0);

        pieceManager.update(player);
        shadowManager.update();

        PhysicsManager.updatePlayerPosition(player, plateformManager.getPlateforms(),dt);

        if(player.isDead()) {
            int distance = (int)(player.getPosition().x - Player.DEFAULT_POS.x);
            player.setDeath(false);
            player.setPosition(defaultPointPlayer);
            GameActivity.launchLoseActivity(new GameOverDataBundle(GameActivity.strTimer, distance, plateformManager.getLevelLength(), player.getScore()));
        }
    }

    public void draw() {
        if(holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            if(canvas == null) return;

            canvas.drawColor(Color.DKGRAY);

            plateformManager.drawPlateformOnCanvas(canvas);
            pieceManager.drawPiecesOnCanvas(canvas);

            canvas.drawBitmap(player.getSprite(), Player.DEFAULT_POS.x, player.getPosition().y, new Paint());

            // oblige de les afficher 2x, car le calcul de l ombre empeche de mettre des elements soit au dessus soit en dessous
            // les mettres au dessus et en dessous corrige le probleme
            cross.drawOnCanvas(canvas);
            crossAB.drawOnCanvas(canvas);

            shadowManager.drawOnCanvas(canvas);

            cross.drawOnCanvas(canvas);
            crossAB.drawOnCanvas(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setPointsFingerPressed(AbstractPoint[] points) {
        pointFingerPressed.clear();

        if(points == null) return;

        pointFingerPressed.addAll(Arrays.asList(points));
    }
}
