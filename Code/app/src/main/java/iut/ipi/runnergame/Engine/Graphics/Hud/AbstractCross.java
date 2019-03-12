package iut.ipi.runnergame.Engine.Graphics.Hud;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Entity.Drawable;

public abstract class AbstractCross implements Drawable {
    public abstract RectangleClickable getArrowTop();
    public abstract RectangleClickable getArrowBottom();
    public abstract RectangleClickable getArrowLeft();
    public abstract RectangleClickable getArrowRight();

    public abstract void setPosition(AbstractPoint point);

    public abstract void drawRectOnCanvas(Canvas canvas, Paint paintNonClicked, Paint paintClicked);
    public abstract void updateArrowPressed(List<AbstractPoint> points);
}
