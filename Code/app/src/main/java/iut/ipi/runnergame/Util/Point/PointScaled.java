package iut.ipi.runnergame.Util.Point;

import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Util.WindowDefinitions;

public class PointScaled extends AbstractPoint {
    public PointScaled() {
        this(0, 0);
    }

    public PointScaled(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
