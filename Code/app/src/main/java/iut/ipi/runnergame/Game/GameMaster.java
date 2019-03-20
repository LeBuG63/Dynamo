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
import iut.ipi.runnergame.Engine.Graphics.Hud.BaseHud;
import iut.ipi.runnergame.Engine.Graphics.Hud.Hud;
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
import iut.ipi.runnergame.Entity.Player.AbstractPlayer;
import iut.ipi.runnergame.Entity.Player.BasePlayer;
import iut.ipi.runnergame.Game.Level.LevelCreator;
import iut.ipi.runnergame.Game.Level.Loader.LevelLoaderText;
import iut.ipi.runnergame.R;

public class GameMaster extends Thread {
    public static GameOverDataBundle registerDataBundle = new GameOverDataBundle("0:00", 0,0,0);

    private final long FPS = 30L;
    private final long FRAME_PERIOD = 1000L / FPS;

    private final float SHAKE_HINT_OFFSET;
    private final int BOSS_APPEAR_SEC = 30;

    private final AbstractPoint defaultPointPlayer = new PointRelative(50, 0);

    private List<AbstractPoint> pointFingerPressed = new ArrayList<>();

    private SurfaceHolder holder = null;

    private Hud hud;

    private AbstractPlayer player;
    private Boss boss;

    private LevelCreator levelCreator;

    private AbstractAudioPlayer sfxPlayer;
    private AbstractAudioPlayer musicPlayer;

    private boolean isRunning = true;
    private boolean paused = false;
    private Object pauseKey = new Object();

    private int secondsEllapsed = 0;

    private Context context;
    private int bestScore = 0;
    private int bestDistance = 0;
    private String bestTime;

    public GameMaster(Context context, SurfaceHolder surfaceHolder) {
        AbstractPoint.clearPoolPoints();

        this.context = context;

        SHAKE_HINT_OFFSET = WindowUtil.convertPixelsToDp(context, 50);

        sfxPlayer = new SoundEffectAudioPlayer(context);
        musicPlayer = new MusicAudioPlayer(context);

        musicPlayer.add("mercury", R.raw.mercury, true);
        sfxPlayer.add("footsteps", R.raw.sfx_jump);

        try {
            player = new BasePlayer(context, defaultPointPlayer, AbstractPlayer.DEFAULT_SCALE);
            boss = new BossDragon(context, new PointRelative(80, 50), 2500, player);
            levelCreator = new LevelCreator(context, player, new LevelLoaderText(context, player, R.raw.level));

            player.getAnimationManager().setDurationFrame(AbstractPlayer.ANIMATION_RUNNING_LEFT, 100);
            player.getAnimationManager().setDurationFrame(AbstractPlayer.ANIMATION_RUNNING_RIGHT, 100);

        }
        catch(IOException ignored) {

        }

        hud = new BaseHud(context, player, pointFingerPressed);

        updatePoolPoints();

        surfaceHolder.setFixedSize((int)WindowDefinitions.WIDTH, (int)WindowDefinitions.HEIGHT);
        holder = surfaceHolder;

        PhysicsManager.GRAVITY = WindowUtil.convertPixelsToDp(context,981f*1.5f);
        GameActivity.timerStarted = System.currentTimeMillis();
    }

    private void reset() {
        secondsEllapsed = 0;

        player.setDeath(false);

        player.setPosition(defaultPointPlayer);
        player.stopX();
        player.stopY();

        player.getAnimationManager().start(0);

        levelCreator = new LevelCreator(context, player, new LevelLoaderText(context, player, R.raw.level));
        boss.setAppeared(false);

        GameActivity.timerStarted = System.currentTimeMillis();

        updatePoolPoints();

        System.gc(); // on veut dire au gc de bien vouloir supprimer toutes references inutiles
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
//        musicPlayer.play("mercury");

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

                update(1.0f/(float)FPS);
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

        if(secondsEllapsed > BOSS_APPEAR_SEC) {
            boss.setAppeared(true);
            boss.update(dt);
        }

        hud.update(dt);

        if(hud.getCross().getArrowTop().getIsClicked() || hud.getCrossAB().getArrowLeft().getIsClicked()) {
            idle = false;

            if (player.isOnGround()) {
                sfxPlayer.playUntilFinished("footsteps");
                player.setHasAnotherJump(true);
            }

            player.jump(player.getImpulseJump());

            if (player.getImpulse().x >= 0) {
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_JUMP_RIGHT);
            } else {
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_JUMP_LEFT);
            }
        }
        if(hud.getCross().getArrowBottom().getIsClicked()) {
            player.getAnimationManager().start(AbstractPlayer.ANIMATION_CROUCH);
            idle = false;
        }
        else if(hud.getCross().getArrowLeft().getIsClicked()) {
            player.moveLeft(player.getImpulseMovement());

            if(player.isOnGround())
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_RUNNING_LEFT);
            else
                player.getAnimationManager().start(AbstractPlayer.ANIMATION_JUMP_LEFT);
            idle = false;
        }
        else if(hud.getCross().getArrowRight().getIsClicked()) {
            player.moveRight(player.getImpulseMovement());

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
            hud.getHint().show();
        } else {
            hud.getHint().hide();
        }

        PhysicsManager.updatePlayerPosition(context, player, levelCreator.getLevel().getPlateforms(),dt);

        if(player.isDead()) {
            int distance = (int)(player.getPosition().x - AbstractPlayer.DEFAULT_POS.x);
            int score = ((((distance / levelCreator.getLevel().getLength()) * 100) + 1) * player.getScore()) / (Float.valueOf(GameActivity.strTimer).intValue() + 1);

            if(score > bestScore) {
                bestScore = score;
                bestDistance = distance;
                bestTime = GameActivity.strTimer;
            }

            registerDataBundle = new GameOverDataBundle(bestTime, bestDistance, levelCreator.getLevel().getLength(), bestScore);

            reset();
        }

        if(hud.getButton(BaseHud.BUT_EXIT).getIsClicked()) {
            GameActivity.endActivity();
        }

        if(hud.getButton(BaseHud.BUT_MUTE).getIsClicked()) {
            musicPlayer.pause();
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
            //les mettres au dessus et en dessous corrige le probleme
            hud.drawOnCanvas(canvas);

            levelCreator.getLevel().drawShadowOnCanvas(canvas);

            hud.drawOnCanvas(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setPointsFingerPressed(AbstractPoint[] points) {
        pointFingerPressed.clear();

        if(points == null) return;

        pointFingerPressed.addAll(Arrays.asList(points));
    }
}
