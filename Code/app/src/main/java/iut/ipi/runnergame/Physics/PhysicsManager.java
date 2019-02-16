package iut.ipi.runnergame.Physics;

import android.graphics.PointF;

public class PhysicsManager {
    public static final double GRAVITY = 0.8;
    public static final double FRICTION = 0.9;
    private static final double VECTOR_CONSIDERED_NULL = 0.1;

    public static void mulVecWithGravity(PointF point, PointF dir) {
        dir.y *= GRAVITY;

        if(Math.abs(dir.x) < VECTOR_CONSIDERED_NULL) {
            dir.x = 0;
        }

        point.y += dir.y;
    }

    public static void mulVecWithFriction(PointF point, PointF dir) {
        dir.x *= FRICTION;

        if(Math.abs(dir.x) < VECTOR_CONSIDERED_NULL) {
            dir.x = 0;
        }

        point.x += dir.x;
    }

    public static void mulVecWithWorldsPhysic(PointF point, PointF dir) {
        mulVecWithGravity(point, dir);
        mulVecWithFriction(point, dir);
    }
}
