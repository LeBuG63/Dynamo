package iut.ipi.runnergame.Entity.Collision;

public class CollisionBox implements ICollision {

    private int left;
    private int top;
    private int width;
    private int height;

    public CollisionBox(int left, int top, int width, int height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void setLeft(int x) {
        left = x;
    }

    @Override
    public void setTop(int x) {
        top = x;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public int getTop() {
        return top;
    }

    @Override
    public boolean isInCollision(ICollision other) {
        return !((other.getLeft() >= (this.getLeft() + this.getWidth()))
                || ((other.getLeft() + other.getWidth()) <= this.getLeft())
                || (other.getTop() >= (this.getTop() + this.getHeight()))
                || ((other.getTop() + other.getHeight()) <= this.getTop()));

    }
}
