package iut.ipi.runnergame.Hud.Cross;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Hud.IArrowClickable;
import iut.ipi.runnergame.Hud.ICross;

public class CrossClickable implements ICross {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    public static final int BOTTOM = 3;

    private int arrowClicked = 0;

    private List<IArrowClickable> arrow = new ArrayList<>();

    public CrossClickable(Point center, int size) {
        int centerX = center.x;
        int centerY = center.y;

        arrow.add(new ArrowClickable(new Point(centerX-size, centerY), size, size));
        arrow.add(new ArrowClickable(new Point(centerX+size, centerY), size, size));
        arrow.add(new ArrowClickable(new Point(centerX, centerY-size), size, size));
        arrow.add(new ArrowClickable(new Point(centerX, centerY+size), size, size));
    }

    public IArrowClickable getTop() {
        return arrow.get(TOP);
    }

    public IArrowClickable getBottom() {
        return arrow.get(BOTTOM);
    }

    public IArrowClickable getLeft() {
        return arrow.get(LEFT);
    }

    public IArrowClickable getRight() {
        return arrow.get(RIGHT);
    }

    public void drawRectOnCanvas(Canvas canvas, Paint paintNonClicked, Paint paintClicked) {
        int i = 0;

        for(IArrowClickable arrowClickable : arrow) {
            if (i == arrowClicked) {
                canvas.drawRect(arrowClickable.getRectangle(), paintClicked);
            } else {
                canvas.drawRect(arrowClickable.getRectangle(), paintNonClicked);
            }

            i++;
        }
    }

    public void updateArrowPressed(Point point) {
        int i = 0;
        arrowClicked = -1;
        for(IArrowClickable arrowClickable : arrow) {
            if(arrowClickable.isClicked(point)) {
                arrowClicked = i;
            }
            i++;
        }
    }
}
