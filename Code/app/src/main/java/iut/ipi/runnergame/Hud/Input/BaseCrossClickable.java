package iut.ipi.runnergame.Hud.Input;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Hud.ArrowClickable;
import iut.ipi.runnergame.Hud.Cross;

public class BaseCrossClickable implements Cross {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    public static final int BOTTOM = 3;

    private List<Bitmap> arrowImage = new ArrayList<>();
    private List<ArrowClickable> arrow = new ArrayList<>();

    public BaseCrossClickable(int ressource, Point center, int size) {
        int centerX = center.x;
        int centerY = center.y;

        arrow.add(new BaseArrowClickable(new Point(centerX-size, centerY), size, size));
        arrow.add(new BaseArrowClickable(new Point(centerX+size, centerY), size, size));
        arrow.add(new BaseArrowClickable(new Point(centerX, centerY-size), size, size));
        arrow.add(new BaseArrowClickable(new Point(centerX, centerY+size), size, size));


    }

    public ArrowClickable getArrowTop() {
        return arrow.get(TOP);
    }

    public ArrowClickable getArrowBottom() {
        return arrow.get(BOTTOM);
    }

    public ArrowClickable getArrowLeft() {
        return arrow.get(LEFT);
    }

    public ArrowClickable getArrowRight() {
        return arrow.get(RIGHT);
    }

    public void drawRectOnCanvas(Canvas canvas, Paint paintNonClicked, Paint paintClicked) {
        for(ArrowClickable arrowClickable : arrow) {
            if (arrowClickable.getIsClicked()) {
                canvas.drawRect(arrowClickable.getRectangle(), paintClicked);
            } else {
                canvas.drawRect(arrowClickable.getRectangle(), paintNonClicked);
            }
        }
    }

    public void updateArrowPressed(Point point) {
        for(ArrowClickable arrowClickable : arrow) {
            if(arrowClickable.pointInside(point)) {
                arrowClickable.setIsClicked(true);
            }
            else {
                arrowClickable.setIsClicked(false);
            }
        }
    }
}
