package iut.ipi.runnergame.Entity.Collision;

public class CollisionBox implements ICollision {
    @Override
    public void setLeft() {

    }

    @Override
    public void setTop() {

    }

    @Override
    public int getLeft() {
        return 0;
    }

    @Override
    public int getTop() {
        return 0;
    }

    @Override
    public boolean isInCollision(ICollision other) {
        return false;
    }
}
