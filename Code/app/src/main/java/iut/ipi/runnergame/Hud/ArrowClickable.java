package iut.ipi.runnergame.Hud;

import android.graphics.PointF;
import android.graphics.Rect;

public interface ArrowClickable {
    Rect getRectangle();
    PointF getPosition();

    boolean pointInside(PointF point);
    boolean getIsClicked();
    void setIsClicked(boolean isClicked);
}
