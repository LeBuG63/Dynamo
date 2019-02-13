package iut.ipi.runnergame.Hud;

import android.graphics.Point;
import android.graphics.Rect;

public interface IArrowClickable {
    Rect getRectangle();
    boolean isClicked(Point point);
}
