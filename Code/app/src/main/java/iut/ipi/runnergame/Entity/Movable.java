package iut.ipi.runnergame.Entity;

import android.graphics.PointF;

public interface Movable {
    void moveUp(float force);
    void moveDown(float force);
    void moveLeft(float force);
    void moveRight(float force);
    void jump(float force);

    void stopY();
    void stopX();

    PointF getImpulse();
    void setImpulse(PointF impulse);
}
