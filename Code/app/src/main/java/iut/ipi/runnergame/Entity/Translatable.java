package iut.ipi.runnergame.Entity;

import iut.ipi.runnergame.Util.Point.AbstractPoint;

public interface Translatable {
    AbstractPoint getOffset();
    void setOffset(AbstractPoint offset);
}
