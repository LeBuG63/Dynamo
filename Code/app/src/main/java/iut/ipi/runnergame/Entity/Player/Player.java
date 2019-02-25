package iut.ipi.runnergame.Entity.Player;

import android.graphics.Bitmap;

import java.io.IOException;

import iut.ipi.runnergame.Animation.Animable;
import iut.ipi.runnergame.Animation.AnimationManager;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Movable;
import iut.ipi.runnergame.Util.Point.AbstractPoint;
import iut.ipi.runnergame.Util.Point.Point;
import iut.ipi.runnergame.Util.Point.PointRelative;
import iut.ipi.runnergame.Util.WindowDefinitions;

public class Player extends AbstractEntity implements Collidable, Movable, Animable {
    public static final int DEFAULT_FRAME_DURATION = 1000;

    public static AbstractPoint DEFAULT_POS = new PointRelative(50,0);

    public static final float IMPULSE_MOVEMENT = 800.0f;
    public static final float IMPULSE_JUMP = 18.0f;

    public static final int ANIMATION_IDLE = 0;
    public static final int ANIMATION_RUNNING_RIGHT = 1;
    public static final int ANIMATION_RUNNING_LEFT = 2;

    private Point impulse = new Point(0.0f,0.0f);
    private AnimationManager animationManager;
    private Collision collision;

    private boolean onGround = false;
    private boolean isDead = false;

    public Player(AbstractPoint pos, AnimationManager animationManager) throws IOException {
        super(pos);

        setAnimationManager(animationManager);
        setCollision(new BaseCollisionBox(pos.x, pos.y, getAnimationManager().getFrame().getWidth(), getAnimationManager().getFrame().getHeight()));

        animationManager.start(0);
    }

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

    @Override
    public void jump(float force) {
        if (onGround) {
            onGround = false;
            setImpulse(new Point(0.0f, -force));
            setPosition(new Point(getPosition().x, getPosition().y - force));
        }
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

    @Override
    public void setAnimationManager(AnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    @Override
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

    public void setDeath(boolean b) {
        isDead = b;
    }

    public boolean isDead() {
        return isDead;
    }
}
