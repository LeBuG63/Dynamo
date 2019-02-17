package iut.ipi.runnergame.Entity.Collision;

public class BaseCollisionBox implements Collision {
    private static final float COLLISION_OFFSET = 10.0f;

    private double left;
    private double top;
    private double width;
    private double height;

    public CollisionOccuredSide collisionOccuredSide;

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

    private boolean collision(Collision other) {
        return !((other.getLeft() >= (this.getLeft() + this.getWidth()))
                || ((other.getLeft() + other.getWidth()) <= this.getLeft())
                || (other.getTop() >= (this.getTop() + this.getHeight()))
                || ((other.getTop() + other.getHeight()) <= this.getTop()));
    }

    @Override
    public boolean isInCollision(Collision other) {
        if (collision(other)) {

            if(collision(new BaseCollisionBox(other.getLeft(), other.getTop(), other.getWidth(), -COLLISION_OFFSET))) {
                collisionOccuredSide = CollisionOccuredSide.TOP;
            }

            else if(collision(new BaseCollisionBox(other.getLeft(), other.getTop() + COLLISION_OFFSET, -COLLISION_OFFSET, other.getHeight()))) {
                collisionOccuredSide = CollisionOccuredSide.LEFT;
            }
            else if(collision(new BaseCollisionBox(other.getLeft() + other.getWidth(), other.getTop() + COLLISION_OFFSET, COLLISION_OFFSET, other.getHeight()))) {
                collisionOccuredSide = CollisionOccuredSide.RIGHT;
            }
            else if(collision(new BaseCollisionBox(other.getLeft() + COLLISION_OFFSET, other.getTop() + other.getHeight(), other.getWidth() - COLLISION_OFFSET*2, COLLISION_OFFSET))) {
                collisionOccuredSide = CollisionOccuredSide.DOWN;
            }
            return true;
        }

        collisionOccuredSide = CollisionOccuredSide.NONE;

        return false;
    }

    @Override
    public CollisionOccuredSide getCollisionSide() {
        return collisionOccuredSide;
    }
}
