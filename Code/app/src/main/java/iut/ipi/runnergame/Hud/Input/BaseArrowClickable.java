package iut.ipi.runnergame.Hud.Input;

import android.graphics.Point;

import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Hud.ArrowClickable;

public class BaseArrowClickable extends AbstractEntity implements ArrowClickable {
    private boolean isClicked = false;

    public BaseArrowClickable(Point pos, int width, int heigth) {
        super(pos, width, heigth);
    }

    public boolean pointInside(Point point) {
        return getCollision().isInCollision(new BaseCollisionBox(point.x, point.y, 1, 1));
    }

    public boolean getIsClicked() {
        return isClicked;
    }

    public void setIsClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }
}
