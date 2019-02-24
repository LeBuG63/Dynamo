package iut.ipi.runnergame.Util.Point;

import iut.ipi.runnergame.Util.WindowDefinitions;

public class PointAdjusted extends AbstractPoint {
    public PointAdjusted() {
        this(0, 0);
    }

    public PointAdjusted(float x, float y) {
        setPoint(x, y);
        addToPool(this);
    }

    @Override
    protected void setPoint(float x, float y) {
        paramX = x;
        paramY = y;

        this.x = x * WindowDefinitions.SCREEN_ADJUST;
        this.y = y * WindowDefinitions.SCREEN_ADJUST;
    }
}
