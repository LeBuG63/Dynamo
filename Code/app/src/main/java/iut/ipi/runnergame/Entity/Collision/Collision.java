package iut.ipi.runnergame.Entity.Collision;

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
    CollisionOccuredSide getCollisionSide();
}
