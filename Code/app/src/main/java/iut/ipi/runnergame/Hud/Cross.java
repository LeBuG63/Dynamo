package iut.ipi.runnergame.Hud;

import android.graphics.Canvas;
import android.graphics.Paint;
import iut.ipi.runnergame.Util.PointScaled;

public interface Cross {
    ArrowClickable getArrowTop();
    ArrowClickable getArrowBottom();
    ArrowClickable getArrowLeft();
    ArrowClickable getArrowRight();
    void drawRectOnCanvas(Canvas canvas, Paint paintNonClicked, Paint paintClicked);
    void drawOnCanvas(Canvas canvas);
    void updateArrowPressed(PointScaled point);
}
