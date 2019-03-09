package iut.ipi.runnergame.Engine.Physics;

import iut.ipi.runnergame.Entity.Collision.Collidable;

public class CollisionManager<T extends Collidable> {
    public void updateCollision(T object) {
        object.updateBecauseCollision();
    }
}
