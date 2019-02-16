package iut.ipi.runnergame.Util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class BitmapResizer {

    public static Bitmap bitmapResizerPixelPerfect(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(scaledBitmap);
        Paint paint = new Paint();

        paint.setAntiAlias(false);
        paint.setDither(false);
        paint.setFilterBitmap(false);

        canvas.drawBitmap(bitmap, null, new Rect(0, 0, newWidth, newHeight), paint);

        return scaledBitmap;
    }
}
