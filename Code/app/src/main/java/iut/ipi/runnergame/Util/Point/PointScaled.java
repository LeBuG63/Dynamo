package iut.ipi.runnergame.Util.Point;

import iut.ipi.runnergame.Util.WindowUtil;

public class PointScaled extends AbstractPoint {
    public PointScaled() {
        this(0, 0);
    }

    public PointScaled(float x, float y) {
        this.x = WindowUtil.convertPixelsToDp(x);
        this.y = WindowUtil.convertPixelsToDp(y);
    }
}
