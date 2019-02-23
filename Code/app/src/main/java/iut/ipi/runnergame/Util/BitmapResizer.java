package iut.ipi.runnergame.Util;

import android.graphics.Bitmap;

public class BitmapResizer {
    public static Bitmap bitmapResizerNN(Bitmap bitmap, int newWidth, int newHeight) {
        int[] colors = new int[newWidth * newHeight];

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int xRatio = ((width << 16) / newWidth) + 1;
        int yRatio = ((height << 16) / newHeight) + 1;

        for(int i = 0; i < newHeight; ++i) {
            for(int j = 0; j < newWidth; ++j) {
                int x = (j*xRatio) >> 16;
                int y = (i*yRatio) >> 16;

                colors[i * newWidth + j] = bitmap.getPixel(x, y);
            }
        }

        return Bitmap.createBitmap(colors, newWidth, newHeight, Bitmap.Config.ARGB_8888);
    }
}
