package iut.ipi.runnergame.Hud;

import android.graphics.RectF;

import iut.ipi.runnergame.Util.Point.AbstractPoint;

public interface ArrowClickable {
    RectF getRectangle();
    AbstractPoint getPosition();

    boolean pointInside(AbstractPoint point);
    boolean getIsClicked();
    void setIsClicked(boolean isClicked);
}
