package iut.ipi.runnergame.Hud;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public interface ICross {
    IArrowClickable getTop();
    IArrowClickable getBottom();
    IArrowClickable getLeft();
    IArrowClickable getRight();
    void drawRectOnCanvas(Canvas canvas, Paint paintNonClicked, Paint paintClicked);
    void updateArrowPressed(Point point);
}
