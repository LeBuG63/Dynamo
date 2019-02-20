package iut.ipi.runnergame.Hud;

import iut.ipi.runnergame.Util.PointScaled;
import android.graphics.RectF;

public interface ArrowClickable {
    RectF getRectangle();
    PointScaled getPosition();

    boolean pointInside(PointScaled point);
    boolean getIsClicked();
    void setIsClicked(boolean isClicked);
}
