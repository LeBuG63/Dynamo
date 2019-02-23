package iut.ipi.runnergame.Util.Point;

import iut.ipi.runnergame.Util.WindowDefinitions;

public class PointRelative extends AbstractPoint {
    public PointRelative(float percentX, float percentY) {
        this.x = percentX * WindowDefinitions.WIDTH_DPI / 100.0f;
        this.y = percentY * WindowDefinitions.HEIGHT_DPI / 100.0f;
    }
}
