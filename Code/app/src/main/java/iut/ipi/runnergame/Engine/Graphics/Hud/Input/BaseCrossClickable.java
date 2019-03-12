package iut.ipi.runnergame.Engine.Graphics.Hud.Input;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Hud.AbstractCross;
import iut.ipi.runnergame.Engine.Graphics.Hud.RectangleClickable;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.Graphics.Spritesheet.Spritesheet;

public class BaseCrossClickable extends AbstractCross {
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    public static final float DEFAULT_SCALE = 1.5f;

    private int nArrow = 0;
    private float scale;
    private Spritesheet spritesheet;

    private List<RectangleButton> arrow = new ArrayList<>();

    private Context context;

    public BaseCrossClickable(Context context, int resource, float scale, int nArrow, AbstractPoint center) {
        this.context = context;

        this.scale = scale;
        this.nArrow = nArrow;

        try {
            spritesheet = new Spritesheet(context, resource, 1, nArrow, Spritesheet.DEFAULT_SPRITE_SIZE, Spritesheet.DEFAULT_SPRITE_SIZE, scale);
            setPosition(center);
        }
        catch (IOException e) {}
    }

    public void setPosition(AbstractPoint center) {
        float centerX = center.x;
        float centerY = center.y;

        int size =  spritesheet.getFrameWidth();

        arrow.clear();

        for(int i = 0; i < nArrow; ++i) {

        }
        arrow.add(new RectangleButton(context, spritesheet.getSprite(0, 0), scale, new Point(centerX - size, centerY)));
        arrow.add(new RectangleButton(context, spritesheet.getSprite(0, 1), scale, new Point(centerX, centerY - size)));
        arrow.add(new RectangleButton(context, spritesheet.getSprite(0, 2), scale, new Point(centerX + size, centerY)));
        arrow.add(new RectangleButton(context, spritesheet.getSprite(0, 3), scale, new Point(centerX, centerY + size)));
    }

    public RectangleButton getArrowTop() {
        return arrow.get(TOP);
    }
    public RectangleButton getArrowBottom() {
        return arrow.get(BOTTOM);
    }
    public RectangleButton getArrowLeft() {
        return arrow.get(LEFT);
    }
    public RectangleButton getArrowRight() {
        return arrow.get(RIGHT);
    }

    public void drawRectOnCanvas(Canvas canvas, Paint paintNonClicked, Paint paintClicked) {
        int i = 0;
        for(RectangleClickable arrowClickable : arrow) {
            if(i >= nArrow) break;
            ++i;

            if (arrowClickable.getIsClicked()) {
                canvas.drawRect(arrowClickable.getRectangle(), paintClicked);
            } else {
                canvas.drawRect(arrowClickable.getRectangle(), paintNonClicked);
            }
        }
    }

    public void drawOnCanvas(Canvas canvas) {
        int i = 0;

        Paint paint = new Paint();

        for(RectangleButton arrowClickable : arrow) {
            if(i >= nArrow) return;

            arrowClickable.drawOnCanvas(canvas);
            i++;
        }
    }

    public void updateArrowPressed(List<AbstractPoint> points) {
        int i = 0;

        for(RectangleClickable arrowClickable : arrow) {
            if(i >= nArrow) return;

            arrowClickable.updatePressed(points);

            i++;
        }
    }
}
