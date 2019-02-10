package iut.ipi.runnergame.Entity;

import android.graphics.Bitmap;
import android.graphics.Point;

import iut.ipi.runnergame.Animation.IAnimationManager;
import iut.ipi.runnergame.Entity.Collision.ICollision;

public abstract class AEntity {
    private final Point pos;
    protected Point position = new Point();

    protected ICollision collision;
    protected IAnimationManager animationManager;

    public AEntity(Point pos) {
        this(pos, null, null);
    }

    public AEntity(Point pos, ICollision collision) {
        this(pos, collision, null);
    }

    public AEntity(Point pos, ICollision collision, IAnimationManager animationManager) {
        this.pos = pos;
        this.collision = collision;
        this.animationManager = animationManager;
    }

    public abstract Bitmap getSprite();
}
