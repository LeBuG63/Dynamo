package iut.ipi.runnergame.Entity.Collision;

import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;

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
    List<CollisionOccuredSide> getCollisionSide();
}
