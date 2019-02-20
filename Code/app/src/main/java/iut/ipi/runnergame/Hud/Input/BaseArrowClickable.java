package iut.ipi.runnergame.Hud.Input;

import iut.ipi.runnergame.Util.PointScaled;

import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Hud.ArrowClickable;

public class BaseArrowClickable extends AbstractEntity implements ArrowClickable, Collidable {
    private boolean isClicked = false;

    private Collision collision;

    public BaseArrowClickable(PointScaled pos, int width, int heigth) {
        super(pos, width, heigth);

        collision = new BaseCollisionBox(pos.x, pos.y,  width, heigth);
    }

    public boolean pointInside(PointScaled point) {
        return getCollision().isInCollision(new BaseCollisionBox(point.x, point.y, 1, 1));
    }

    public boolean getIsClicked() {
        return isClicked;
    }

    public void setIsClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    @Override
    public PointScaled getPosition() {
        return super.getPosition();
    }

    @Override
    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    @Override
    public Collision getCollision() {
        return collision;
    }
}
