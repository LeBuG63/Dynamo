package iut.ipi.runnergame.Util.Point;

import iut.ipi.runnergame.Util.WindowDefinitions;
import iut.ipi.runnergame.Util.WindowUtil;

import static iut.ipi.runnergame.Util.WindowDefinitions.RATIO_DPI;

public class PointRelative extends AbstractPoint {
    public PointRelative(float percentX, float percentY) {
        setPoint(percentX, percentY);

        addToPool(this);
    }

    @Override
    protected void setPoint(float x, float y) {
        paramX = x;
        paramY = y;

        this.x = (x * (WindowDefinitions.WIDTH) / 100.0f);
        this.y = (y * (WindowDefinitions.HEIGHT) / 100.0f);
    }
}