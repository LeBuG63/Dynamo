package iut.ipi.runnergame.Entity.Collision;

public interface ICollision {
    void setLeft();
    void setTop();

    int getLeft();
    int getTop();

    boolean isInCollision(ICollision other);
}
