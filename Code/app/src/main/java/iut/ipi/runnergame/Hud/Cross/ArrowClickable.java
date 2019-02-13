package iut.ipi.runnergame.Hud.Cross;

import android.graphics.Point;

import iut.ipi.runnergame.Entity.AEntity;
import iut.ipi.runnergame.Entity.Collision.CollisionBox;
import iut.ipi.runnergame.Hud.IArrowClickable;

public class ArrowClickable extends AEntity implements IArrowClickable {
    public ArrowClickable(Point pos, int width, int heigth) {
        super(pos, width, heigth);
    }

    public boolean isClicked(Point point) {
        return getCollision().isInCollision(new CollisionBox(point.x, point.y, 1, 1));
    }
}
