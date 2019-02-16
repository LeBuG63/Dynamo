package iut.ipi.runnergame.Entity;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import iut.ipi.runnergame.Animation.AnimationManager;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Physics.PhysicsManager;

public abstract class AbstractEntity {
    protected PointF position = new PointF();

    protected Collision collision;
    protected AnimationManager animationManager;

    protected RectF rectangle;
    protected Bitmap image;

    public AbstractEntity(PointF pos, Bitmap bitmap) {
        this(pos, new BaseCollisionBox(pos.x, pos.y, bitmap.getWidth(), bitmap.getHeight()));

        this.image = bitmap;
    }

    public AbstractEntity(PointF pos, int width, int height) {
        this(pos, new BaseCollisionBox(pos.x, pos.y, width, height));

        this.rectangle = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);
    }

    public AbstractEntity(PointF pos, Collision collision) {
        this(pos, collision, null);
    }

    public AbstractEntity(PointF pos, Collision collision, AnimationManager animationManager) {
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

    public PointF getPosition() {
        return position;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public Bitmap getImage() {
        return image;
    }

    public RectF getRectangle() {
        return rectangle;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        this.rectangle = new RectF(getPosition().x, getPosition().y, getPosition().x + image.getWidth(), getPosition().y + image.getHeight());

    }

}
