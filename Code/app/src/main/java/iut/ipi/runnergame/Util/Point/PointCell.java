package iut.ipi.runnergame.Util.Point;

import iut.ipi.runnergame.Util.WindowDefinitions;
import iut.ipi.runnergame.Util.WindowUtil;

public class PointCell extends AbstractPoint {
    public static final int GRID_CELL = 10;

    public PointCell() {
        this(0, 0);
    }

    public PointCell(int cellX, int cellY) {
        this.x = ((WindowDefinitions.widthPixels) / (float)GRID_CELL) * cellX;
        this.y = ((WindowDefinitions.heightPixels) / (float)GRID_CELL) * cellY;
    }
}
