package iut.ipi.runnergame.Hud;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;

import iut.ipi.runnergame.Entity.Drawable;
import iut.ipi.runnergame.Util.Point.AbstractPoint;

public abstract class AbstractCross implements Drawable {
    public abstract ArrowClickable getArrowTop();
    public abstract ArrowClickable getArrowBottom();
    public abstract ArrowClickable getArrowLeft();
    public abstract ArrowClickable getArrowRight();

    public abstract void setPosition(AbstractPoint point);

    public abstract void drawRectOnCanvas(Canvas canvas, Paint paintNonClicked, Paint paintClicked);
    public abstract void updateArrowPressed(List<AbstractPoint> points);
}
