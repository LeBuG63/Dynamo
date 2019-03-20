package iut.ipi.runnergame.Engine;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;

public class WindowDefinitions {
    public static float RESOLUTION_FACTOR = 2f;

    public static final float DENSITY_DPI = Resources.getSystem().getDisplayMetrics().densityDpi;
    public static final float SCALED_DPI = Resources.getSystem().getDisplayMetrics().scaledDensity;

    public static float DEFAULT_WIDTH;
    public static float DEFAULT_HEIGHT;

    public static float WIDTH = Resources.getSystem().getDisplayMetrics().heightPixels / RESOLUTION_FACTOR;
    public static float HEIGHT= Resources.getSystem().getDisplayMetrics().widthPixels / RESOLUTION_FACTOR;

    public static final float SCREEN_ADJUST = DENSITY_DPI / DisplayMetrics.DENSITY_DEFAULT;
}
