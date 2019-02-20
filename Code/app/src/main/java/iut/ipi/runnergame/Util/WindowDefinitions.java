package iut.ipi.runnergame.Util;

import android.content.res.Resources;

public class WindowDefinitions {
    public static final int heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static final int widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;

    // nexus 6: 2560 x 1440
    // inversé parce que le jeu est en mode protrait
    public static final float ratioHeight = widthPixels / 1440.0f;
    public static final float ratioWidth = heightPixels / 2560.0f;

    public static final float density = Resources.getSystem().getDisplayMetrics().scaledDensity;
}
