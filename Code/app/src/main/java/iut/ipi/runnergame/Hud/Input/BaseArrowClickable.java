package iut.ipi.runnergame.Hud.Input;

import android.graphics.PointF;

import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Hud.ArrowClickable;

public class BaseArrowClickable extends AbstractEntity implements ArrowClickable {
    private boolean isClicked = false;

    public BaseArrowClickable(PointF pos, int width, int heigth) {
        super(pos, width, heigth);
    }

    public boolean pointInside(PointF point) {
        return getCollision().isInCollision(new BaseCollisionBox(point.x, point.y, 1, 1));
    }

    public boolean getIsClicked() {
        return isClicked;
    }

    public void setIsClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    @Override
    public PointF getPosition() {
        return super.getPosition();
    }
}
