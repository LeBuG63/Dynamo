package iut.ipi.runnergame.Physics;

import android.graphics.PointF;

public class PhysicsManager {
    public static final float GRAVITY = 30f;
    public static final float FRICTION = 0.9f;
    private static final float VECTOR_CONSIDERED_NULL = 0.1f;

    public static void mulVecWithGravity(PointF point, PointF dir, float dt) {
        PointF oldPoint = dir;

        dir.y += GRAVITY * dt;
        point.y += dir.y + (oldPoint.y + dir.y) * 0.5f * dt;
    }

    public static void mulVecWithFriction(PointF point, PointF dir, float dt) {
        dir.x *= FRICTION  * dt;

        if(Math.abs(dir.x) < VECTOR_CONSIDERED_NULL) {
            dir.x = 0;
        }

        point.x += dir.x;
    }

    public static void mulVecWithWorldsPhysic(PointF point, PointF dir, float dt) {
        mulVecWithGravity(point, dir, dt);
        mulVecWithFriction(point, dir, dt);
    }
}
