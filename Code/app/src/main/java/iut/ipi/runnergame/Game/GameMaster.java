package iut.ipi.runnergame.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import iut.ipi.runnergame.Activity.GameActivity;
import iut.ipi.runnergame.Engine.Graphics.Hud.AbstractCross;
import iut.ipi.runnergame.Engine.Graphics.Hud.Hint.AbstractHint;
import iut.ipi.runnergame.Engine.Graphics.Hud.Hint.ShakeHint;
import iut.ipi.runnergame.Engine.Graphics.Hud.Input.BaseCrossClickable;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.PointRelative;
import iut.ipi.runnergame.Engine.Physics.PhysicsManager;
import iut.ipi.runnergame.Engine.Sfx.Sound.AbstractAudioPlayer;
import iut.ipi.runnergame.Engine.Sfx.Sound.MusicAudioPlayer;
import iut.ipi.runnergame.Engine.Sfx.Sound.SoundEffectAudioPlayer;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Entity.Boss.Boss;
import iut.ipi.runnergame.Entity.Boss.BossDragon;
import iut.ipi.runnergame.Entity.Player.BasePlayer;
import iut.ipi.runnergame.Entity.Player.AbstractPlayer;
import iut.ipi.runnergame.Game.Level.LevelCreator;
import iut.ipi.runnergame.Game.Level.Loader.LevelLoaderText;
import iut.ipi.runnergame.R;

public class GameMaster extends Thread {
    private final long FPS = 60L;
    private final long FRAME_PERIOD = 1000L / FPS;

    private final float SHAKE_HINT_OFFSET = WindowUtil.convertPixelsToDp(50);
    private final int BOSS_APPEAR_SEC = 5;

    private final AbstractPoint defaultPointCross = new PointRelative(10, 70);
    private final AbstractPoint defaultPointCrossAB = new PointRelative(90, 70);
    private final AbstractPoint defaultPointPlayer = new PointRelative(50, 0);

    private List<AbstractPoint> pointFingerPressed = new ArrayList<>();

    private SurfaceHolder holder = null;

    private AbstractPlayer player;
    private Boss boss;

    private AbstractCross cross;
    private AbstractCross crossAB;

    private LevelCreator levelCreator;

    private AbstractAudioPlayer sfxPlayer;
    private AbstractAudioPlayer musicPlayer;

    private AbstractHint shakeHint;
    private AbstractPoint posHideHud = new PointRelative(0, 10);

    private boolean isRunning = true;
    private boolean paused = false;
    private Object pauseKey = new Object();

    private int secondsEllapsed = 0;

    public GameMaster(Context context, SurfaceHolder surfaceHolder) {
        AbstractPoint.clearPoolPoints();

        cross = new BaseCrossClickable(context, R.drawable.sprite_cross_1, BaseCrossClickable.DEFAULT_SCALE, 4, defaultPointCross);
        crossAB = new BaseCrossClickable(context, R.drawable.sprite_cross_ab, BaseCrossClickable.DEFAULT_SCALE, 1, defaultPointCrossAB);

        sfxPlayer = new SoundEffectAudioPlayer(context);
        musicPlayer = new MusicAudioPlayer(context);

        musicPlayer.add("mercury", R.raw.mercury, true);
        sfxPlayer.add("footsteps", R.raw.sfx_jump);


        try {
            player = new BasePlayer(context, defaultPointPlayer, AbstractPlayer.DEFAULT_SCALE);
            boss = new BossDragon(context, new PointRelative(80, 50), 2000, player);
            levelCreator = new LevelCreator(context, player, new LevelLoaderText(context, player, R.raw.level));

            shakeHint = new ShakeHint(context, 0.5f, 4, 200, new PointRelative(45, 20));
            shakeHint.show();

            player.getAnimationManager().setDurationFrame(AbstractPlayer.ANIMATION_RUNNING_LEFT, 100);
            player.getAnimationManager().setDurationFrame(AbstractPlayer.ANIMATION_RUNNING_RIGHT, 100);

        }
        catch(IOException ignored) {

        }

        updatePoolPoints();

        surfaceHolder.setFixedSize((int)WindowDefinitions.WIDTH, (int)WindowDefinitions.HEIGHT);
        holder = surfaceHolder;
    }

    public void updatePoolPoints() {
        AbstractPoint.resizePointsInPool();
    }

    public void pauseUpdate() {
        paused = true;
        musicPlayer.stop();
    }

    public void resumeUpdate() {
        synchronized (pauseKey) {
            paused = false;

            musicPlayer.release();

            musicPlayer.add("mercury", R.raw.mercury, true);
            musicPlayer.playLast();
            pauseKey.notifyAll();
        }
    }

    public void stopUpdate() {
        isRunning = false;
    }

    private long time = System.currentTimeMillis();
    public void waitUntilNeededUpdate(long start) {
        long now = System.currentTimeMillis();

        if(now - time >= 1000L) {// 1 seconde
            secondsEllapsed += 1;
            time = now;
        }

        long sleepTime = (FRAME_PERIOD/2) - (now - start); // il faut endormir le thread si il ne fait rien

        if(sleepTime > 0L) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                isRunning = false;
            }
        }
    }

    private boolean update = true;
    @Override
    public void run() {
        musicPlayer.play("mercury");

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
        sfxPlayer.release();
        musicPlayer.release();
        AbstractPoint.clearPoolPoints();
    }

    public void update(float dt) {
        boolean idle = true;

        if(player.getPosition().x > posHideHud.x) {
            shakeHint.hide();
        }

        if(secondsEllapsed > BOSS_APPEAR_SEC) {
            boss.setAppeared(true);
            boss.update(dt);
        }

        cross.updateArrowPressed(pointFingerPressed);
        crossAB.updateArrowPressed(pointFingerPressed);

        if(cross.getArrowTop().getIsClicked() || crossAB.getArrowLeft().getIsClicked()) {
            idle = false;

            if (player.isOnGround()) {
                sfxPlayer.playUntilFinished("footsteps");
                player.setHasAnotherJump(true);
            }

            player.jump(AbstractPlayer.IMPULSE_JUMP);

            if (player.getImpulse().x >= 0) {
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_JUMP_RIGHT);
            } else {
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_JUMP_LEFT);
            }
        }
        if(cross.getArrowLeft().getIsClicked()) {
            player.moveLeft(AbstractPlayer.IMPULSE_MOVEMENT);

            if(player.isOnGround())
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_RUNNING_LEFT);
            else
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_JUMP_LEFT);
            idle = false;
        }
        if(cross.getArrowRight().getIsClicked()) {
            player.moveRight(AbstractPlayer.IMPULSE_MOVEMENT);

            if(player.isOnGround())
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_RUNNING_RIGHT);
            else
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_JUMP_RIGHT);

            idle = false;
        }
        if(idle){
            if(player.isOnGround())
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_IDLE);
        }

        levelCreator.updateLevel(dt);

        if(levelCreator.getLevel().getShadowManager().getShadowRadius() < levelCreator.getLevel().getShadowManager().getShadowMinRadius() + SHAKE_HINT_OFFSET) {
            shakeHint.show();
        } else {
            shakeHint.hide();
        }

        PhysicsManager.updatePlayerPosition(player, levelCreator.getLevel().getPlateforms(),dt);

        if(player.isDead()) {
            int distance = (int)(player.getPosition().x - AbstractPlayer.DEFAULT_POS.x);

            int score = ((((distance / levelCreator.getLevel().getLength()) * 100) + 1) * player.getScore()) / Float.valueOf(GameActivity.strTimer).intValue();

            GameActivity.launchLoseActivity(new GameOverDataBundle(GameActivity.strTimer, distance, levelCreator.getLevel().getLength(), score));
        }
    }

    private Paint paint = new Paint();
    public void draw() {
        if(holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            if(canvas == null) return;

            canvas.drawColor(Color.BLACK);
            levelCreator.getLevel().drawOnCanvas(canvas);

            if(secondsEllapsed > BOSS_APPEAR_SEC) {
                boss.drawOnCanvas(canvas);
            }

            canvas.drawBitmap(player.getSprite(), AbstractPlayer.DEFAULT_POS.x, player.getPosition().y, paint);

            // oblige de les afficher 2x, car le calcul de l ombre empeche de mettre des elements soit au dessus soit en dessous
            // les mettres au dessus et en dessous corrige le probleme
            shakeHint.drawOnCanvas(canvas);
            cross.drawOnCanvas(canvas);
            crossAB.drawOnCanvas(canvas);

            levelCreator.getLevel().drawShadowOnCanvas(canvas);

            cross.drawOnCanvas(canvas);
            crossAB.drawOnCanvas(canvas);

            shakeHint.drawOnCanvas(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setPointsFingerPressed(AbstractPoint[] points) {
        pointFingerPressed.clear();

        if(points == null) return;

        pointFingerPressed.addAll(Arrays.asList(points));
    }
}
