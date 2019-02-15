package iut.ipi.runnergame.Hud;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public interface Cross {
    ArrowClickable getArrowTop();
    ArrowClickable getArrowBottom();
    ArrowClickable getArrowLeft();
    ArrowClickable getArrowRight();
    void drawRectOnCanvas(Canvas canvas, Paint paintNonClicked, Paint paintClicked);
    void updateArrowPressed(Point point);
}
