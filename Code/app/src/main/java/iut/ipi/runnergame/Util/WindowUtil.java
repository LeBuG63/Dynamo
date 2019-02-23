package iut.ipi.runnergame.Util;

import iut.ipi.runnergame.Util.Point.AbstractPoint;

public abstract class WindowUtil {
    public static int ScaleIntToWindow(int x) {
        return (int)(x * WindowDefinitions.density);
    }

    public static float ScaleFloatToWindow(float x) {
        return (x * WindowDefinitions.density);
    }
}
