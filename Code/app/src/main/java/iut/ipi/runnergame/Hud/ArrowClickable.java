package iut.ipi.runnergame.Hud;

import android.graphics.PointF;
import android.graphics.RectF;

public interface ArrowClickable {
    RectF getRectangle();
    PointF getPosition();

    boolean pointInside(PointF point);
    boolean getIsClicked();
    void setIsClicked(boolean isClicked);
}
