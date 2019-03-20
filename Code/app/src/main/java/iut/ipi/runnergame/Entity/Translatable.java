package iut.ipi.runnergame.Entity;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;

public interface Translatable {
    AbstractPoint getOffset();
    void setOffset(AbstractPoint offset);
    void setOffset(float x, float y);
}
