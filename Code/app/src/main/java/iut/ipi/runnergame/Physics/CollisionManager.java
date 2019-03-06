package iut.ipi.runnergame.Physics;

import java.util.List;

import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;

public class CollisionManager<T extends Collidable> {
    public void updateCollision(T object) {
        object.updateBecauseCollision();
    }
}
