package iut.ipi.runnergame.Engine.Graphics.Point;

import iut.ipi.runnergame.Engine.WindowDefinitions;

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