package iut.ipi.runnergame.Entity.Collision;

import android.content.Context;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.WindowUtil;

public class BaseCollisionBox implements Collision {
    private final float COLLISION_OFFSET;

    private final Context context;

    public List<CollisionOccuredSide> collisionOccuredSide = new ArrayList<>();

    private RectCollision rect;
    private RectCollision otherSide;

    public BaseCollisionBox(Context context, float left, float top, float width, float height) {
        rect = new RectCollision(left, top, width, height);
        otherSide = new RectCollision(left, top, width, height);

        this.context = context;

        COLLISION_OFFSET = WindowUtil.convertPixelsToDp(context, 9.5f);
    }

    public BaseCollisionBox(Context context, RectF rectangle) {
        this(context, rectangle.left, rectangle.top, rectangle.width(), rectangle.height());
    }

    public BaseCollisionBox(Context context) {
        this(context, 0,0,0,0);
    }

    @Override
    public float getWidth() {
        return rect.width;
    }

    @Override
    public void setWidth(float width) {
        rect.width = width;
    }

    @Override
    public float getHeight() {
        return rect.height;
    }

    @Override
    public void setHeight(float height) {
        rect.height = height;
    }

    @Override
    public void set(float x, float y, float w, float h) {
        rect.left = x;
        rect.top = y;
        rect.width = w ;
        rect.height = h;
    }

    @Override
    public RectCollision getRect() {
        return rect;
    }

    @Override
    public void setLeft(float x) {
        rect.left = x;
    }

    @Override
    public void setTop(float x) {
        rect.top = x;
    }

    @Override
    public float getLeft() {
        return rect.left;
    }

    @Override
    public float getTop() {
        return rect.top;
    }

    private boolean collision(RectCollision other) {
        return !((other.left >= (rect.left + rect.width))
                || ((other.left + other.width) <= rect.left)
                || (other.top >= (rect.top + rect.height))
                || ((other.top + other.height) <= rect.top));
    }

    @Override
    public boolean isInCollision(Collision other) {
        collisionOccuredSide.clear();

        if (collision(other.getRect())) {
            otherSide.set(other.getLeft(), other.getTop() - COLLISION_OFFSET, other.getWidth(), -COLLISION_OFFSET);
            if(collision(otherSide)) {
                collisionOccuredSide.add(CollisionOccuredSide.TOP);
            }

            otherSide.set(other.getLeft() - COLLISION_OFFSET, other.getTop(), -COLLISION_OFFSET, other.getHeight());
            if(collision(otherSide)) {
                collisionOccuredSide.add(CollisionOccuredSide.LEFT);
            }

            otherSide.set(other.getLeft() + other.getWidth() + COLLISION_OFFSET, other.getTop() - COLLISION_OFFSET, COLLISION_OFFSET, other.getHeight());
            if(collision(otherSide)) {
                collisionOccuredSide.add(CollisionOccuredSide.RIGHT);
            }

            otherSide.set(other.getLeft(), other.getTop() + other.getHeight() + COLLISION_OFFSET, other.getWidth(), COLLISION_OFFSET);
            if(collision(otherSide)) {
                collisionOccuredSide.add(CollisionOccuredSide.DOWN);
            }

            return true;
        }

        collisionOccuredSide.add(CollisionOccuredSide.NONE);

        return false;
    }

    public boolean isInCollisionWithPoint(AbstractPoint point) {
        if(point == null) return false;
        return (point.x > getLeft() && point.x < (getLeft() + getWidth()) && point.y > getTop() && point.y < (getTop() + getHeight()));
    }

    @Override
    public List<CollisionOccuredSide> getCollisionSide() {
        return collisionOccuredSide;
    }
}
