package iut.ipi.runnergame.Engine.Graphics.Hud.Input;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Hud.AbstractCross;
import iut.ipi.runnergame.Engine.Graphics.Hud.ArrowClickable;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.Graphics.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Engine.WindowDefinitions;

public class BaseCrossClickable extends AbstractCross {
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    public static final float DEFAULT_SCALE = 1.5f;

    private int nArrow = 0;
    private float scale;
    private Spritesheet spritesheet;

    private List<ArrowClickable> arrow = new ArrayList<>();

    public BaseCrossClickable(Context context, int resource, float scale, int nArrow, AbstractPoint center) {
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

        arrow.add(new BaseArrowClickable(new Point(centerX - size, centerY), size, size));
        arrow.add(new BaseArrowClickable(new Point(centerX, centerY - size), size, size));
        arrow.add(new BaseArrowClickable(new Point(centerX + size, centerY), size, size));
        arrow.add(new BaseArrowClickable(new Point(centerX, centerY + size), size, size));
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
        int i = 0;
        for(ArrowClickable arrowClickable : arrow) {
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

        for(ArrowClickable arrowClickable : arrow) {
            if(i >= nArrow) return;

            float x = arrowClickable.getPosition().x;
            float y = arrowClickable.getPosition().y;

            canvas.drawBitmap(spritesheet.getSprite(0, i),  x, y, paint);
            i++;
        }
    }

    public void updateArrowPressed(List<AbstractPoint> points) {
        for(ArrowClickable arrowClickable : arrow) {
            boolean clicked = false;

            if(!points.isEmpty()) {
                for (int index = 0; index < points.size(); ++index) {
                    // si au moins 1 est clique
                    if (arrowClickable.pointInside(points.get(index))) {
                        clicked = true;
                        arrowClickable.setIsClicked(clicked);
                        break;
                    }
                }
            }

            arrowClickable.setIsClicked(clicked);
        }
    }
}
