package iut.ipi.runnergame.Entity.Collision;

public interface Collision {
    void setLeft(double x);
    void setTop(double x);

    double getLeft();
    double getTop();

    double getWidth();
    double getHeight();

    void setWidth(double width);
    void setHeight(double height);

    boolean isInCollision(Collision other);
}
