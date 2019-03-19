package iut.ipi.runnergame.Engine;

import android.content.Context;
import android.util.DisplayMetrics;

public abstract class WindowUtil {
    public static float convertDpToPixel(final Context context, float dp){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(final Context context, float px){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void changeResolutionFactor(float resolutionFactor) {
        WindowDefinitions.RESOLUTION_FACTOR = resolutionFactor;

        WindowDefinitions.WIDTH = WindowDefinitions.DEFAULT_WIDTH / resolutionFactor;
        WindowDefinitions.HEIGHT= WindowDefinitions.DEFAULT_HEIGHT / resolutionFactor;
    }
}
