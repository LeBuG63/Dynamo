package iut.ipi.runnergame.Entity.Collision;

public interface Collidable {
    void setCollision(Collision collision);
    Collision getCollision();

    void updateBecauseCollision();
}
