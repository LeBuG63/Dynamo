package iut.ipi.runnergame.Util.Point;

import iut.ipi.runnergame.Util.WindowDefinitions;

public class PointCell extends AbstractPoint {
    public static final int GRID_CELL = 10;

    public PointCell() {
        this(0, 0);
    }

    public PointCell(int cellX, int cellY) {
        this.x = ((WindowDefinitions.WIDTH_DPI) / (float)GRID_CELL) * cellX;
        this.y = ((WindowDefinitions.HEIGHT_DPI) / (float)GRID_CELL) * cellY;
    }
}
