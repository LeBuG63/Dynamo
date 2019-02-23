package iut.ipi.runnergame.Util;

import android.content.res.Resources;

public class WindowDefinitions {

    // inversé parce que le jeu est en mode protrait
    public static final int heightPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int widthPixels = Resources.getSystem().getDisplayMetrics().heightPixels;

    // nexus 6: 2560 x 1440
    public static final float ratioHeight = heightPixels / 2560.0f;
    public static final float ratioWidth = widthPixels / 1440.0f;

    public static final float density = Resources.getSystem().getDisplayMetrics().density;
}
