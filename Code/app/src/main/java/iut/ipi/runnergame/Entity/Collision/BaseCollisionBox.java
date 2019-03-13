package iut.ipi.runnergame.Entity.Collision;

import android.content.Context;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Engine.WindowUtil;

public class BaseCollisionBox implements Collision {
    private final float COLLISION_OFFSET;

    private float left;
    private float top;
    private float width;
    private float height;

    private final Context context;

    public List<CollisionOccuredSide> collisionOccuredSide = new ArrayList<>();

    public BaseCollisionBox(Context context, float left, float top, float width, float height) {
        this.left = left;
        this.top = top;
        this.width = width ;
        this.height = height;

        this.context = context;

        COLLISION_OFFSET = WindowUtil.convertPixelsToDp(context, 9.5f);
    }

    public BaseCollisionBox(Context context, RectF rectangle) {
        this(context, rectangle.left, rectangle.top, rectangle.width(), rectangle.height());
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

    // retourner collisionoccuredside
    @Override
    public boolean isInCollision(Collision other) {
        collisionOccuredSide.clear();

        if (collision(other)) {
            if(collision(new BaseCollisionBox(context, other.getLeft(), other.getTop() - COLLISION_OFFSET, other.getWidth(), -COLLISION_OFFSET))) {
                collisionOccuredSide.add(CollisionOccuredSide.TOP);
            }
            if(collision(new BaseCollisionBox(context,other.getLeft() - COLLISION_OFFSET, other.getTop(), -COLLISION_OFFSET, other.getHeight()))) {
                collisionOccuredSide.add(CollisionOccuredSide.LEFT);
            }
            if(collision(new BaseCollisionBox(context,other.getLeft() + other.getWidth() + COLLISION_OFFSET, other.getTop() - COLLISION_OFFSET, COLLISION_OFFSET, other.getHeight()))) {
                collisionOccuredSide.add(CollisionOccuredSide.RIGHT);
            }
            if(collision(new BaseCollisionBox(context, other.getLeft(), other.getTop() + other.getHeight() + COLLISION_OFFSET, other.getWidth(), COLLISION_OFFSET))) {
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
