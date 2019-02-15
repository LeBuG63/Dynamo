package iut.ipi.runnergame.Entity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import iut.ipi.runnergame.Animation.AnimationManager;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Physics.PhysicsManager;

public abstract class AbstractEntity implements Movable {
    protected PointF impulse = new PointF(0.0f,0.0f);

    protected Point position = new Point();

    protected Collision collision;
    protected AnimationManager animationManager;

    protected Rect rectangle;
    protected Bitmap image;

    public AbstractEntity(Point pos, Bitmap bitmap) {
        this(pos, new BaseCollisionBox(pos.x, pos.y, bitmap.getWidth(), bitmap.getHeight()));

        this.image = bitmap;
    }

    public AbstractEntity(Point pos, int width, int height) {
        this(pos, new BaseCollisionBox(pos.x, pos.y, width, height));

        this.rectangle = new Rect(pos.x, pos.y, pos.x + width, pos.y + height);
    }

    public AbstractEntity(Point pos, Collision collision) {
        this(pos, collision, null);
    }

    public AbstractEntity(Point pos, Collision collision, AnimationManager animationManager) {
        this.position = pos;
        this.collision = collision;
        this.animationManager = animationManager;
    }

    public Collision getCollision() {
        return collision;
    }

    public Bitmap getSprite() {
        return image;
    }

    public Point getPosition() {
        return position;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public Bitmap getImage() {
        return image;
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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
        setImpulse(new PointF(0.0f, -force));
    }

    @Override
    public PointF getImpule() {
        return impulse;
    }

    @Override
    public void setImpulse(PointF impulse) {
        this.impulse = impulse;
    }

    @Override
    public void updatePoisition() {
        PhysicsManager.mulVecWithWorldsPhysic(getPosition(), impulse);
    }
}
