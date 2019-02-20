package iut.ipi.runnergame.Util;

import android.graphics.PointF;

public class PointScaled {
    private PointF pointF;

    public float x;
    public float y;

    public PointScaled() {
        this(0, 0);
    }

    public PointScaled(float x, float y) {
        this.x = (x);
        this.y = (y);
    }
}
