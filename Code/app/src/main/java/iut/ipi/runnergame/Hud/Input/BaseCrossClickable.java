package iut.ipi.runnergame.Hud.Input;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Hud.ArrowClickable;
import iut.ipi.runnergame.Hud.Cross;
import iut.ipi.runnergame.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Util.WindowDefinitions;

public class BaseCrossClickable implements Cross {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    public static final int BOTTOM = 3;

    private Spritesheet spritesheet;

    private List<Bitmap> arrowImage = new ArrayList<>();
    private List<ArrowClickable> arrow = new ArrayList<>();

    public BaseCrossClickable(Context context, int ressource, int spriteWidth, int spriteHeight, int scale) {
        this(context, ressource, spriteWidth, spriteHeight, scale, new PointF((spriteWidth*scale), WindowDefinitions.heightPixels - (spriteHeight*scale)*3));
    }

    public BaseCrossClickable(Context context, int ressource, int spriteWidth, int spriteHeight, int scale, PointF center) {
        float centerX = center.x;
        float centerY = center.y;

        int size = spriteWidth * scale;

        arrow.add(new BaseArrowClickable(new PointF(centerX - size, centerY), size, size));
        arrow.add(new BaseArrowClickable(new PointF(centerX + size, centerY), size, size));
        arrow.add(new BaseArrowClickable(new PointF(centerX, centerY - size), size, size));
        arrow.add(new BaseArrowClickable(new PointF(centerX, centerY + size), size, size));

        try {
            spritesheet = new Spritesheet(context, ressource, 1, 4, spriteWidth, spriteHeight, scale);
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
        for(ArrowClickable arrowClickable : arrow) {
            canvas.drawBitmap(spritesheet.getSprites().get(0).get(i), arrowClickable.getPosition().x, arrowClickable.getPosition().y, new Paint());
            i++;
        }
    }

    public void updateArrowPressed(PointF point) {
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
