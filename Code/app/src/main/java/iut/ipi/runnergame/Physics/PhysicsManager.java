package iut.ipi.runnergame.Physics;

import android.graphics.PointF;
import android.util.Log;

import java.util.List;

import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Plateform.AbstractPlateform;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Util.WindowDefinitions;

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

    public static void updatePlayerPosition(Player player, List<AbstractPlateform> plateforms, float dt) {
        player.setOnGround(false);

        for(AbstractPlateform plateform : plateforms) {
            if(player.getCollision().isInCollision(plateform.getCollision())) {
                switch(player.getCollision().getCollisionSide()) {
                    case NONE:
                        break;
                    case TOP:
                        Log.d("COLLISION", "TOP");

                        player.setOnGround(true);
                        break;
                    case DOWN:
                        Log.d("COLLISION", "DOWN");
                        player.stopY();
                        break;
                    case RIGHT:
                        Log.d("COLLISION", "RIGHT");
                        player.setImpulse(new PointF(1.0f, player.getImpulse().y));
                        break;
                    case LEFT:
                        Log.d("COLLISION", "LEFT");
                        player.setImpulse(new PointF(-1.0f, player.getImpulse().y));
                        break;
                }
            }
        }

        if(!player.isOnGround()) {
            mulVecWithGravity(player.getPosition(), player.getImpulse(), dt);
        }

        mulVecWithFriction(player.getPosition(), player.getImpulse(), dt);

        player.setCollision(new BaseCollisionBox(player.getPosition().x, player.getPosition().y, player.getAnimationManager().getFrame().getWidth(), player.getAnimationManager().getFrame().getHeight()));
    }
}
