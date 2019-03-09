package iut.ipi.runnergame.Engine.Gfx.Hud;

import android.graphics.RectF;

import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Util.Point.AbstractPoint;

public interface ArrowClickable extends Collidable {
    RectF getRectangle();
    AbstractPoint getPosition();

    boolean pointInside(AbstractPoint point);
    boolean getIsClicked();
    void setIsClicked(boolean isClicked);
}
