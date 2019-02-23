package iut.ipi.runnergame.Util;

import android.content.res.Resources;

public class WindowDefinitions {
    public static final float DENSITY_DPI = Resources.getSystem().getDisplayMetrics().densityDpi;
    public static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    // inversé parce que le jeu est en mode portrait
    public static final float HEIGHT_DPI = WindowUtil.convertPixelsToDp(Resources.getSystem().getDisplayMetrics().widthPixels);
    public static final float WIDTH_DPI = WindowUtil.convertPixelsToDp(Resources.getSystem().getDisplayMetrics().heightPixels);

    // nexus 6: 2560 x 1440
    public static final float RATIO_HEIGHT = HEIGHT_DPI / WindowUtil.convertPixelsToDp(2560.0f);
    public static final float RATIO_WIDTH = WIDTH_DPI / WindowUtil.convertPixelsToDp(1440.0f);
}
