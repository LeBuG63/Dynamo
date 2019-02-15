package iut.ipi.runnergame.Hud;

import android.graphics.Point;
import android.graphics.Rect;

public interface ArrowClickable {
    Rect getRectangle();
    boolean pointInside(Point point);
    boolean getIsClicked();
    void setIsClicked(boolean isClicked);
}
