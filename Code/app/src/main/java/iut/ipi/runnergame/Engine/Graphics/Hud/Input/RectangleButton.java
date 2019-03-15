package iut.ipi.runnergame.Engine.Graphics.Hud.Input;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.BitmapResizer;
import iut.ipi.runnergame.Engine.Graphics.Hud.RectangleClickable;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Drawable;

public class RectangleButton extends AbstractEntity implements RectangleClickable, Collidable, Drawable {
    private long delayRepressed;

    private boolean isClicked = false;

    public Collision collision;

    private Bitmap bitmap;
    private Paint paint = new Paint();

    private long lastTimePressed = System.currentTimeMillis();

    public RectangleButton(Context context, int resourceId, float size, AbstractPoint pos, long  delayRepressed) {
        super(pos);

        this.delayRepressed = delayRepressed;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        int w = (int)(WindowUtil.convertPixelsToDp(context, tmp.getWidth() * size) * WindowDefinitions.SCALED_DPI);
        int h = (int)(WindowUtil.convertPixelsToDp(context, tmp.getHeight() * size) * WindowDefinitions.SCALED_DPI);

        collision = new BaseCollisionBox(context, getPosition().x, getPosition().y, w, h);

        bitmap = BitmapResizer.bitmapResizerNN(tmp, w, h);
    }

    public RectangleButton(Context context, Bitmap bitmap, float size, AbstractPoint pos) {
        super(pos);

        if(bitmap == null) return;

        int w = (int)(bitmap.getWidth() * size);
        int h = (int)(bitmap.getHeight() * size);

        collision = new BaseCollisionBox(context, getPosition().x, getPosition().y, w, h);
        this.bitmap = bitmap;
    }

    @Override
    public synchronized void updatePressed(final List<AbstractPoint> points) {
        boolean clicked = false;
        long now = System.currentTimeMillis();

        setIsClicked(false);

        if(now - lastTimePressed > delayRepressed) {
            for (int i = 0; i < points.size(); ++i) {
                try {
                    clicked = pointInside(points.get(i));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
                if (clicked) {
                    lastTimePressed = now;
                    break;
                }
            }

            setIsClicked(clicked);
        }
    }

    @Override
    public boolean pointInside(AbstractPoint point) {
        return getCollision().isInCollisionWithPoint(point);
    }

    @Override
    public boolean getIsClicked() {
        return isClicked;
    }

    @Override
    public void setIsClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    @Override
    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    @Override
    public Collision getCollision() {
        return collision;
    }

    @Override
    public void updateBecauseCollision() {
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        canvas.drawBitmap(bitmap, getPosition().x, getPosition().y, paint);
    }
}
