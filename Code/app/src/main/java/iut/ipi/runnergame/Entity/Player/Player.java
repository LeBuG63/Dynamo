package iut.ipi.runnergame.Entity.Player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;

import java.io.IOException;

import iut.ipi.runnergame.Animation.Animable;
import iut.ipi.runnergame.Animation.AnimationManager;
import iut.ipi.runnergame.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Movable;
import iut.ipi.runnergame.Physics.PhysicsManager;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Util.WindowDefinitions;

public class Player extends AbstractEntity implements Collidable, Movable, Animable {
    public static final int DEFAULT_FRAME_DURATION = 1000;

    public static final float IMPULSE_MOVEMENT = 1000.0f;
    public static final float IMPULSE_JUMP = 18.0f;

    public static final int ANIMATION_IDLE = 0;
    public static final int ANIMATION_RUNNING_RIGHT = 1;
    public static final int ANIMATION_RUNNING_LEFT = 2;

    private PointF impulse = new PointF(0.0f,0.0f);
    private AnimationManager animationManager;
    private Collision collision;

    private boolean onGround = false;

    public Player(Context context, PointF pos, int scale) throws IOException {
        super(pos);

        setAnimationManager(new BaseSpriteSheetAnimation(context, R.drawable.sprite_player_1, scale, Spritesheet.DEFAULT_SPRITE_SIZE, Spritesheet.DEFAULT_SPRITE_SIZE, 4, DEFAULT_FRAME_DURATION, 3, 4));
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
    public void stopY() {
        this.impulse = new PointF(this.impulse.x, 0);
    }

    @Override
    public void stopX() {
        this.impulse = new PointF(0, this.impulse.y);
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
}
