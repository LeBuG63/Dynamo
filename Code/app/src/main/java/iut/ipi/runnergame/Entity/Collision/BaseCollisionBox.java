package iut.ipi.runnergame.Entity.Collision;

public class BaseCollisionBox implements Collision {

    private double left;
    private double top;
    private double width;
    private double height;

    public BaseCollisionBox(double left, double top, double width, double height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void setLeft(double x) {
        left = x;
    }

    @Override
    public void setTop(double x) {
        top = x;
    }

    @Override
    public double getLeft() {
        return left;
    }

    @Override
    public double getTop() {
        return top;
    }

    @Override
    public boolean isInCollision(Collision other) {
        return !((other.getLeft() >= (this.getLeft() + this.getWidth()))
                || ((other.getLeft() + other.getWidth()) <= this.getLeft())
                || (other.getTop() >= (this.getTop() + this.getHeight()))
                || ((other.getTop() + other.getHeight()) <= this.getTop()));

    }
}
