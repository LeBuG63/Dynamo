package iut.ipi.runnergame.Engine;

import android.content.res.Resources;

public class WindowDefinitions {
    public static final float DENSITY_DPI = Resources.getSystem().getDisplayMetrics().densityDpi;
    public static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    // inversé parce que le jeu est en mode portrait
    public static final float HEIGHT_DPI = WindowUtil.convertPixelsToDp(Resources.getSystem().getDisplayMetrics().heightPixels);
    public static final float WIDTH_DPI = WindowUtil.convertPixelsToDp(Resources.getSystem().getDisplayMetrics().widthPixels);
    public static final float SCALED_DPI = Resources.getSystem().getDisplayMetrics().scaledDensity;

    public static float WIDTH = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static float HEIGHT= Resources.getSystem().getDisplayMetrics().widthPixels;

    public static final float XDPI = Resources.getSystem().getDisplayMetrics().xdpi;
    public static final float YDPI = Resources.getSystem().getDisplayMetrics().ydpi;



    public static final float SCREEN_ADJUST = DENSITY_DPI / 160.0f;
    public static final float RATIO_DPI = DENSITY_DPI / 515;

    // nexus 6: 2560 x 1440
    public static final float RATIO_HEIGHT = HEIGHT / 1440.0f;
    public static final float RATIO_WIDTH = WIDTH / 2560.0f;
}
