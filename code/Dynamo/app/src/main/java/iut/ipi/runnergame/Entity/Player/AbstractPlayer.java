package iut.ipi.runnergame.Entity.Player;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.IOException;

import iut.ipi.runnergame.Engine.Graphics.Animation.Animable;
import iut.ipi.runnergame.Engine.Graphics.Animation.AnimationManager;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.Graphics.Point.PointRelative;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Movable;

public abstract class AbstractPlayer extends AbstractEntity implements Collidable, Movable, Animable {
    public static final int DEFAULT_FRAME_DURATION = 1000;
    public static final long FREQ_JUMP_MILLIS = 200;

    public static AbstractPoint DEFAULT_POS = new PointRelative(50,0);

    private float impulseMovement;
    private float impulseJump;

    public static final int ANIMATION_IDLE = 0;
    public static final int ANIMATION_RUNNING_RIGHT = 1;
    public static final int ANIMATION_RUNNING_LEFT = 2;
    public static final int ANIMATION_JUMP_RIGHT = 3;
    public static final int ANIMATION_JUMP_LEFT = 4;
    public static final int ANIMATION_FALLING = 5;
    public static final int ANIMATION_CROUCH = 6;

    public boolean hasAnotherJump = true;

    private Point impulse = new Point(0.0f,0.0f);
    private AnimationManager animationManager;
    private Collision collision;

    private boolean onGround = false;
    private boolean isDead = false;

    private int score = 0;

    public AbstractPlayer(Context context, AbstractPoint pos, AnimationManager animationManager) throws IOException {
        super(new Point(pos.x, pos.y));

        impulseMovement = WindowUtil.convertPixelsToDp(context,130.0f) * WindowDefinitions.SCREEN_ADJUST;
        impulseJump = WindowUtil.convertPixelsToDp(context, 280.0f) * WindowDefinitions.SCREEN_ADJUST;

        setAnimationManager(animationManager);
        setCollision(new BaseCollisionBox(context, pos.x, pos.y, getAnimationManager().getFrame().getWidth(), getAnimationManager().getFrame().getHeight()));

        animationManager.start(0);
    }

    public void addToScore(int add) {
        score += add;
    }

    public int getScore() {
        return score;
    }

    @Override
    public Bitmap getSprite() {
        return getAnimationManager().getFrame();
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public void moveUp(float force) {
        setImpulse(0.0f, force);
    }

    @Override
    public void moveDown(float force) {
        setImpulse(0.0f, -force);
    }

    @Override
    public void moveLeft(float force) {
        setImpulse(-force, 0f);
    }

    @Override
    public void moveRight(float force) {
        setImpulse(new Point(force, 0f));
    }

    @Override
    public void stopY() {
        this.impulse.y = 0f;
    }

    @Override
    public void stopX() {
        this.impulse.x = 0f;
    }

    private long timeEllapsedJump = System.currentTimeMillis();
    @Override
    public void jump(float force) {
        if (System.currentTimeMillis() - timeEllapsedJump >= FREQ_JUMP_MILLIS && (onGround || hasAnotherJump)) {
            if(!onGround && hasAnotherJump)
                hasAnotherJump = false;

            onGround = false;

            timeEllapsedJump = System.currentTimeMillis();

            setImpulse(0.0f, -force);
        }
    }

    public void setHasAnotherJump(boolean value) {
        hasAnotherJump = value;
    }

    public boolean getHasAnotherJump() {
        return hasAnotherJump;
    }

    @Override
    public AbstractPoint getImpulse() {
        return impulse;
    }

    @Override
    public void setImpulse(AbstractPoint impulse) {
        setImpulse(impulse.x, impulse.y);
    }

    @Override
    public void setImpulse(float x, float y) {
        Point save = this.impulse;

        this.impulse.x = (x == 0f) ? save.x : x;
        this.impulse.y = (y == 0f) ? save.y : y;
    }

    private void setAnimationManager(AnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    @Override
    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    @Override
    public Collision getCollision() {
        return collision;
    }

    @Override
    public void updateBecauseCollision() {
        // on ne fait rien car c est lui l acteur des collisions
    }

    public void setDeath(boolean b) {
        isDead = b;
    }

    public boolean isDead() {
        return isDead;
    }

    public float getImpulseMovement() {
        return impulseMovement;
    }

    public float getImpulseJump() {
        return impulseJump;
    }
}
