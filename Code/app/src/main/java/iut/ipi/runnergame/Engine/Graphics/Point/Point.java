package iut.ipi.runnergame.Engine.Graphics.Point;

public class Point extends AbstractPoint {
    public Point() {
        this(0, 0);
    }

    public Point(float x, float y) {
        setPoint(x, y);
    }

    @Override
    protected void setPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
