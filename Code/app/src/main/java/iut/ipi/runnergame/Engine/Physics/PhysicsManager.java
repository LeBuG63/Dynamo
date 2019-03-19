package iut.ipi.runnergame.Engine.Physics;

import android.content.Context;

import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Collision.CollisionOccuredSide;
import iut.ipi.runnergame.Entity.Plateform.AbstractPlateform;
import iut.ipi.runnergame.Entity.Player.AbstractPlayer;

public class PhysicsManager {
    public static float GRAVITY;
    public static final float FRICTION = 1f ;
    public static final float Y_PLAYER_CONSIDERED_DEAD = WindowDefinitions.HEIGHT*1.2f;

    private static final float VECTOR_CONSIDERED_NULL = 0.1f;

    public static void mulVecWithGravity(AbstractPoint point, AbstractPoint dir, float dt) {
        AbstractPoint oldPoint = new Point(dir.x, dir.y);

        dir.y += GRAVITY * dt;
        point.y += dir.y * dt + (0.5f * GRAVITY * dt * dt);
    }

    public static void mulVecWithFriction(AbstractPoint point, AbstractPoint dir, float dt) {
        dir.x *= FRICTION  * dt;

        if(Math.abs(dir.x) < VECTOR_CONSIDERED_NULL) {
            dir.x = 0;
        }

        point.x += dir.x;
    }

    public static void updatePlayerPosition(Context context, AbstractPlayer player, List<AbstractPlateform> plateforms, float dt) {
        player.setOnGround(false);

        // pour faire une bonne collision, il faut prevoir au moins une etape en avance
        // cela sert notamment a eviter que le joueur entre dans les blocs ou il y a collision
        // pour ca, une projection est faite pour permettre d'avoir ou potentiellement le joueur sera a la prochaine frame
        // ensuite, il faut faire les calculs sur les projections pour savoir si il y a collision

        AbstractPoint pointProjection = new Point(AbstractPlayer.DEFAULT_POS.x, player.getPosition().y);
        AbstractPoint impulseProjection = new Point(player.getImpulse().x, player.getImpulse().y);

        mulVecWithGravity(pointProjection, impulseProjection, dt);
        mulVecWithFriction(pointProjection, impulseProjection, dt);

        float playerWidth = player.getSprite().getWidth();
        float playerHeight = player.getSprite().getHeight();

        float playerX = player.getPosition().x;
        float playerY = player.getPosition().y;

        Collision collisionProjection = new BaseCollisionBox(context, pointProjection.x, pointProjection.y, playerWidth, playerHeight);

        for(AbstractPlateform plateform : plateforms) {
            if(collisionProjection.isInCollision(plateform.getCollision())) {
                float plateformX = plateform.getPosition().x;
                float plateformY = plateform.getPosition().y;

                List<CollisionOccuredSide> collisionOccuredSides = collisionProjection.getCollisionSide();

                boolean cancelRight = false;
                boolean cancelLeft = false;

                for(CollisionOccuredSide c : collisionOccuredSides) {
                    if(c == CollisionOccuredSide.TOP) {
                        cancelLeft = true;
                        cancelRight = true;

                        player.setOnGround(true);
                        player.setPosition(new Point(playerX, (int) (plateformY - playerHeight)));
                    }
                    else if(c == CollisionOccuredSide.DOWN) {
                        player.stopY();
                    }
                    if(!cancelRight && c == CollisionOccuredSide.RIGHT) {
                        player.stopX();
                        player.setPosition(new Point(playerX, playerY));
                    }
                    else if(!cancelLeft && c == CollisionOccuredSide.LEFT) {
                        player.stopX();
                        player.setPosition(new Point(playerX, playerY));
                    }
                }
            }
        }

        if(!player.isOnGround()) {
            mulVecWithGravity(player.getPosition(), player.getImpulse(), dt);
        }

        mulVecWithFriction(player.getPosition(), player.getImpulse(), dt);

        if(player.getPosition().y > Y_PLAYER_CONSIDERED_DEAD) {
            player.setDeath(true);
        }

        player.setCollision(new BaseCollisionBox(context, AbstractPlayer.DEFAULT_POS.x, player.getPosition().y, playerWidth, playerHeight));
    }
}
