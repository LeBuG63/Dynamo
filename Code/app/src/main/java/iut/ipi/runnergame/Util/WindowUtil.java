package iut.ipi.runnergame.Util;

public abstract class WindowUtil {
    public static int ScaleIntToWindow(int x) {
        return (int)(x * WindowDefinitions.density);
    }

    public static float ScaleFloatToWindow(float x) {
        return (x * WindowDefinitions.density);
    }
}
