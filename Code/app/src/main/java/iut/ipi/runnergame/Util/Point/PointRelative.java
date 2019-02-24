package iut.ipi.runnergame.Util.Point;

import iut.ipi.runnergame.Util.WindowDefinitions;
import iut.ipi.runnergame.Util.WindowUtil;

import static iut.ipi.runnergame.Util.WindowDefinitions.RATIO_DPI;

public class PointRelative extends AbstractPoint {
    public PointRelative(float percentX, float percentY) {
        this.x = (percentX * (WindowDefinitions.WIDTH) / 100.0f);
        this.y = (percentY * (WindowDefinitions.HEIGHT) / 100.0f);
    }
}