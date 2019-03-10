package iut.ipi.runnergame.Entity.Player;

import android.graphics.Bitmap;

import java.io.IOException;

import iut.ipi.runnergame.Engine.Graphics.Animation.Animable;
import iut.ipi.runnergame.Engine.Graphics.Animation.AnimationManager;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.Graphics.Point.PointRelative;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Movable;

public class Player extends AbstractEntity implements Collidable, Movable, Animable {
    public static final int DEFAULT_FRAME_DURATION = 1000;
    public static final long FREQ_JUMP_MILLIS = 200;

    public static AbstractPoint DEFAULT_POS = new PointRelative(50,0);

    public static final float IMPULSE_MOVEMENT = 500.0f;
    public static final float IMPULSE_JUMP = 20.0f;

    public static final int ANIMATION_IDLE = 0;
    public static final int ANIMATION_RUNNING_RIGHT = 1;
    public static final int ANIMATION_RUNNING_LEFT = 2;
    public static final int ANIMATION_JUMP_RIGHT = 3;
    public static final int ANIMATION_JUMP_LEFT = 4;
    public static final int ANIMATION_FALLING = 5;

    public boolean hasAnotherJump = true;

    private Point impulse = new Point(0.0f,0.0f);
    private AnimationManager animationManager;
    private Collision collision;

    private boolean onGround = false;
    private boolean isDead = false;

    private int score = 0;

    public Player(AbstractPoint pos, AnimationManager animationManager) throws IOException {
        super(pos);

        setAnimationManager(animationManager);
        setCollision(new BaseCollisionBox(pos.x, pos.y, getAnimationManager().getFrame().getWidth(), getAnimationManager().getFrame().getHeight()));

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
        setImpulse(new Point(0.0f, force));
    }

    @Override
    public void moveDown(float force) {
        setImpulse(new Point(0.0f, -force));
    }

    @Override
    public void moveLeft(float force) {
        setImpulse(new Point(-force, 0f));
    }

    @Override
    public void moveRight(float force) {
        setImpulse(new Point(force, 0f));
    }

    @Override
    public void stopY() {
        this.impulse = new Point(this.impulse.x, 0);
    }

    @Override
    public void stopX() {
        this.impulse = new Point(0, this.impulse.y);
    }

    private long timeEllapsedJump = System.currentTimeMillis();
    @Override
    public void jump(float force) {
        if (System.currentTimeMillis() - timeEllapsedJump >= FREQ_JUMP_MILLIS && (onGround || hasAnotherJump)) {
            if(!onGround && hasAnotherJump)
                hasAnotherJump = false;

            onGround = false;

            timeEllapsedJump = System.currentTimeMillis();

            setImpulse(new Point(0.0f, -force));
            setPosition(new Point(getPosition().x, getPosition().y - force));
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
        Point save = this.impulse;

        this.impulse.x = (impulse.x == 0f) ? save.x : impulse.x;
        this.impulse.y = (impulse.y == 0f) ? save.y : impulse.y;
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
}
