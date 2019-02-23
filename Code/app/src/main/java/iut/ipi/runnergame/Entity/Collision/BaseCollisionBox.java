package iut.ipi.runnergame.Entity.Collision;

import android.graphics.RectF;

import iut.ipi.runnergame.Util.Point.AbstractPoint;

public class BaseCollisionBox implements Collision {
    private static final float COLLISION_OFFSET = 10.0f;

    private float left;
    private float top;
    private float width;
    private float height;

    public CollisionOccuredSide collisionOccuredSide;

    public BaseCollisionBox(float left, float top, float width, float height) {
        this.left = left;
        this.top = top;
        this.width = width ;
        this.height = height;
    }

    public BaseCollisionBox(RectF rectangle) {
        this(rectangle.left, rectangle.top, rectangle.width(), rectangle.height());
    }

        @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void setLeft(float x) {
        left = x;
    }

    @Override
    public void setTop(float x) {
        top = x;
    }

    @Override
    public float getLeft() {
        return left;
    }

    @Override
    public float getTop() {
        return top;
    }

    private boolean collision(Collision other) {
        return !((other.getLeft() >= (this.getLeft() + this.getWidth()))
                || ((other.getLeft() + other.getWidth()) <= this.getLeft())
                || (other.getTop() >= (this.getTop() + this.getHeight()))
                || ((other.getTop() + other.getHeight()) <= this.getTop()));
    }

    @Override
    public boolean isInCollision(Collision other) {
        if (collision(other)) {

            if(collision(new BaseCollisionBox(other.getLeft(), other.getTop(), other.getWidth(), -COLLISION_OFFSET))) {
                collisionOccuredSide = CollisionOccuredSide.TOP;
            }

            else if(collision(new BaseCollisionBox(other.getLeft(), other.getTop() + COLLISION_OFFSET, -COLLISION_OFFSET, other.getHeight()))) {
                collisionOccuredSide = CollisionOccuredSide.LEFT;
            }
            else if(collision(new BaseCollisionBox(other.getLeft() + other.getWidth(), other.getTop() + COLLISION_OFFSET, COLLISION_OFFSET, other.getHeight()))) {
                collisionOccuredSide = CollisionOccuredSide.RIGHT;
            }
            else if(collision(new BaseCollisionBox(other.getLeft() + COLLISION_OFFSET, other.getTop() + other.getHeight(), other.getWidth() - COLLISION_OFFSET*2, COLLISION_OFFSET))) {
                collisionOccuredSide = CollisionOccuredSide.DOWN;
            }
            return true;
        }

        collisionOccuredSide = CollisionOccuredSide.NONE;

        return false;
    }

    public boolean isInCollisionWithPoint(AbstractPoint point) {
        return (point.x > getLeft() && point.x < (getLeft() + getWidth()) && point.y > getTop() && point.y < (getTop() + getHeight()));
    }

    @Override
    public CollisionOccuredSide getCollisionSide() {
        return collisionOccuredSide;
    }
}
