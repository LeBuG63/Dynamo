package iut.ipi.runnergame.Entity.Collision;

import iut.ipi.runnergame.Util.Point.AbstractPoint;

public interface Collision {
    void setLeft(float x);
    void setTop(float x);

    float getLeft();
    float getTop();

    float getWidth();
    float getHeight();

    void setWidth(float width);
    void setHeight(float height);

    boolean isInCollision(Collision other);
    boolean isInCollisionWithPoint(AbstractPoint point);
    CollisionOccuredSide getCollisionSide();
}
