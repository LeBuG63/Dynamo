package iut.ipi.runnergame.Entity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import iut.ipi.runnergame.Animation.IAnimationManager;
import iut.ipi.runnergame.Entity.Collision.CollisionBox;
import iut.ipi.runnergame.Entity.Collision.ICollision;

public abstract class AEntity {
    protected Point position = new Point();

    protected ICollision collision;
    protected IAnimationManager animationManager;

    protected Rect rectangle;
    protected Bitmap image;

    public AEntity(Point pos) {
        this(pos, null, null);
    }

    public AEntity(Point pos, Bitmap bitmap) {
        this(pos, new CollisionBox(pos.x, pos.y, bitmap.getWidth(), bitmap.getHeight()));

        this.image = bitmap;
    }

    public AEntity(Point pos, int width, int height) {
        this(pos, new CollisionBox(pos.x, pos.y, width, height));

        this.rectangle = new Rect(pos.x, pos.y, pos.x + width, pos.y + height);
    }

    public AEntity(Point pos, ICollision collision) {
        this(pos, collision, null);
    }

    public AEntity(Point pos, ICollision collision, IAnimationManager animationManager) {
        this.position = pos;
        this.collision = collision;
        this.animationManager = animationManager;
    }

    public ICollision getCollision() {
        return collision;
    }

    public Bitmap getSprite() {
        return image;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Bitmap getImage() {
        return image;
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
