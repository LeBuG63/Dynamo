package iut.ipi.runnergame.Entity;

import iut.ipi.runnergame.Util.PointScaled;

public interface Movable {
    void moveUp(float force);
    void moveDown(float force);
    void moveLeft(float force);
    void moveRight(float force);
    void jump(float force);

    void stopY();
    void stopX();

    PointScaled getImpulse();
    void setImpulse(PointScaled impulse);
}
