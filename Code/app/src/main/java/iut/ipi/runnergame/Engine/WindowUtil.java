package iut.ipi.runnergame.Engine;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public abstract class WindowUtil {
    public static float convertDpToPixel(float dp){
        return dp * (WindowDefinitions.DENSITY_DPI / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px){
        return px / (WindowDefinitions.DENSITY_DPI / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void changeResolutionFactor(float resolutionFactor) {
        WindowDefinitions.RESOLUTION_FACTOR = resolutionFactor;

        WindowDefinitions.WIDTH = WindowDefinitions.DEFAULT_WIDTH / resolutionFactor;
        WindowDefinitions.HEIGHT= WindowDefinitions.DEFAULT_HEIGHT / resolutionFactor;
    }
}
