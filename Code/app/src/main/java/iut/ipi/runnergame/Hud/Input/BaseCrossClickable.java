package iut.ipi.runnergame.Hud.Input;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Hud.ArrowClickable;
import iut.ipi.runnergame.Hud.Cross;
import iut.ipi.runnergame.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Util.Point.AbstractPoint;
import iut.ipi.runnergame.Util.Point.PointScaled;

public class BaseCrossClickable implements Cross {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    public static final int BOTTOM = 3;
    public static final int DEFAULT_SCALE = 10;

    private Spritesheet spritesheet;

    private List<ArrowClickable> arrow = new ArrayList<>();

    public BaseCrossClickable(Context context, int resource, int scale, AbstractPoint center) {
        float centerX = center.x;
        float centerY = center.y;

        int spriteWidth = Spritesheet.DEFAULT_SPRITE_SIZE;
        int spriteHeight = Spritesheet.DEFAULT_SPRITE_SIZE;

        int size =  spriteWidth * scale;

        arrow.add(new BaseArrowClickable(new PointScaled(centerX - size, centerY), size, size));
        arrow.add(new BaseArrowClickable(new PointScaled(centerX + size, centerY), size, size));
        arrow.add(new BaseArrowClickable(new PointScaled(centerX, centerY - size), size, size));
        arrow.add(new BaseArrowClickable(new PointScaled(centerX, centerY + size), size, size));

        try {
            spritesheet = new Spritesheet(context, resource, 1, 4, spriteWidth, spriteHeight, scale);
        }
        catch (IOException e) {}
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

    @Override
    public void drawOnCanvas(Canvas canvas) {
        int i = 0;

        Paint paint = new Paint();

        for(ArrowClickable arrowClickable : arrow) {
            float x = arrowClickable.getPosition().x;
            float y = arrowClickable.getPosition().y;

            canvas.drawBitmap(spritesheet.getSprite(0, i),  x, y, paint);
            i++;
        }
    }

    public void updateArrowPressed(AbstractPoint point) {
        for(ArrowClickable arrowClickable : arrow) {
            arrowClickable.setIsClicked(arrowClickable.pointInside(point));
        }
    }
}
