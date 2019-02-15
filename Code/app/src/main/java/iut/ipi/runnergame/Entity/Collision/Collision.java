package iut.ipi.runnergame.Entity.Collision;

public interface Collision {
    void setLeft(int x);
    void setTop(int x);

    int getLeft();
    int getTop();

    int getWidth();
    int getHeight();

    void setWidth(int width);
    void setHeight(int height);

    boolean isInCollision(Collision other);
}
