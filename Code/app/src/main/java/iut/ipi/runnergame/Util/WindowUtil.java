package iut.ipi.runnergame.Util;

import android.util.DisplayMetrics;

public abstract class WindowUtil {
    public static int ScaleIntToWindow(int x) {
        return (int)(x * WindowDefinitions.DENSITY_DPI);
    }

    public static float ScaleFloatToWindow(float x) {
        return (x * WindowDefinitions.DENSITY_DPI);
    }

    public static float convertDpToPixel(float dp){
        return dp * (WindowDefinitions.DENSITY_DPI / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px){
        return px / (WindowDefinitions.DENSITY_DPI / DisplayMetrics.DENSITY_DEFAULT);
    }
}
