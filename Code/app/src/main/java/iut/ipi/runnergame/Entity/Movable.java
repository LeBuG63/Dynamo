package iut.ipi.runnergame.Entity;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;

public interface Movable {
    void moveUp(float force);
    void moveDown(float force);
    void moveLeft(float force);
    void moveRight(float force);
    void jump(float force);

    void stopY();
    void stopX();

    AbstractPoint getImpulse();
    void setImpulse(AbstractPoint impulse);
}
