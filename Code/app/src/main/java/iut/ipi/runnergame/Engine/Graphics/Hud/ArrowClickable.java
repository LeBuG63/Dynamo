package iut.ipi.runnergame.Engine.Graphics.Hud;

import android.graphics.RectF;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Entity.Collision.Collidable;

public interface ArrowClickable extends Collidable {
    RectF getRectangle();
    AbstractPoint getPosition();

    boolean pointInside(AbstractPoint point);
    boolean getIsClicked();
    void setIsClicked(boolean isClicked);
}
