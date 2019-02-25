package iut.ipi.runnergame.Hud;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;

import iut.ipi.runnergame.Util.Point.AbstractPoint;

public interface Cross {
    ArrowClickable getArrowTop();
    ArrowClickable getArrowBottom();
    ArrowClickable getArrowLeft();
    ArrowClickable getArrowRight();

    void setPosition(AbstractPoint point);

    void drawRectOnCanvas(Canvas canvas, Paint paintNonClicked, Paint paintClicked);
    void drawOnCanvas(Canvas canvas);
    void updateArrowPressed(List<AbstractPoint> points);
}
