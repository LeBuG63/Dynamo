package iut.ipi.runnergame.Game.Level;

import android.app.WallpaperInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.BitmapResizer;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.Graphics.Point.PointAdjusted;
import iut.ipi.runnergame.Engine.Graphics.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Drawable;
import iut.ipi.runnergame.Entity.Translatable;
import iut.ipi.runnergame.R;

public class Background extends AbstractEntity implements Translatable, Drawable {
    private AbstractPoint offset;

    private Paint paint = new Paint();
    private Bitmap[] backgroundBitmaps = new Bitmap[2];

    private int step = 0;

    public Background(Context context) {
        super(new Point(0,0));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        // TODO: utiliser un spritesheet
        backgroundBitmaps[0] = BitmapResizer.bitmapResizerNN(BitmapFactory.decodeResource(context.getResources(), R.drawable.background_1, options), (int)WindowDefinitions.WIDTH, (int)WindowDefinitions.HEIGHT);
        backgroundBitmaps[1] = BitmapResizer.bitmapResizerNN(BitmapFactory.decodeResource(context.getResources(), R.drawable.background_1, options), (int)WindowDefinitions.WIDTH, (int)WindowDefinitions.HEIGHT);
    }

    @Override
    public AbstractPoint getOffset() {
        return offset;
    }

    @Override
    public void setOffset(AbstractPoint offset) {
        this.offset = offset;
    }

    private int lastStep = 0;
    @Override
    public void drawOnCanvas(Canvas canvas) {
        float x = getPosition().x - getOffset().x;
        float y = getPosition().y - getOffset().y;

        boolean left = false;

        step = (int)(x / -WindowDefinitions.WIDTH);
        Log.d("step", Integer.toString(step));

        if(step != lastStep) {
            lastStep = step;

            left = true;
            Bitmap tmp = backgroundBitmaps[0];
            backgroundBitmaps[0] = backgroundBitmaps[1];
            backgroundBitmaps[1] = tmp;
        }

        canvas.drawBitmap(backgroundBitmaps[0], x + WindowDefinitions.WIDTH * (step), y, paint);
        canvas.drawBitmap(backgroundBitmaps[1], x + WindowDefinitions.WIDTH * (step + 1), y, paint);
    }
}
