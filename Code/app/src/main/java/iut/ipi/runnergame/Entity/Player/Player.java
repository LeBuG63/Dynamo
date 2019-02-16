package iut.ipi.runnergame.Entity.Player;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;

import iut.ipi.runnergame.Animation.AnimationManager;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Movable;
import iut.ipi.runnergame.Physics.PhysicsManager;
import iut.ipi.runnergame.Util.WindowDefinitions;

public class Player extends AbstractEntity implements Collidable, Movable {
    public static final float IMPULSE_MOVEMENT = 1000.0f;
    public static final float IMPULSE_JUMP = 18.0f;

    public static final int ANIMATION_IDLE = 0;
    public static final int ANIMATION_RUNNING_RIGHT = 1;
    public static final int ANIMATION_RUNNING_LEFT = 2;

    private PointF impulse = new PointF(0.0f,0.0f);
    private boolean onGround = true;

    public Player(PointF pos, Collision collision, AnimationManager animationManager) {
        super(pos, collision, animationManager);

        animationManager.start(0);
    }

    public Bitmap getSprite() {
        return animationManager.getFrame();
    }


    @Override
    public void moveUp(float force) {
        setImpulse(new PointF(0.0f, force));
    }

    @Override
    public void moveDown(float force) {
        setImpulse(new PointF(0.0f, -force));
    }

    @Override
    public void moveLeft(float force) {
        setImpulse(new PointF(-force, 0f));
    }

    @Override
    public void moveRight(float force) {
        setImpulse(new PointF(force, 0f));
    }

    @Override
    public void jump(float force) {
        if (onGround) {
            onGround = false;
            setImpulse(new PointF(0.0f, -force));
            setPosition(new PointF(getPosition().x, getPosition().y - force));
        }
    }

    @Override
    public PointF getImpulse() {
        return impulse;
    }

    @Override
    public void setImpulse(PointF impulse) {
        PointF save = this.impulse;

        this.impulse.x = (impulse.x == 0) ? save.x : impulse.x;
        this.impulse.y = (impulse.y == 0) ? save.y : impulse.y;
    }

    @Override
    public void updatePoisition(float dt) {
        PhysicsManager.mulVecWithFriction(getPosition(), impulse, dt);

        if(getPosition().y < WindowDefinitions.heightPixels - 200) {
            PhysicsManager.mulVecWithGravity(getPosition(), impulse, dt);
        }
        else {
            onGround = true;
        }
    }
}
