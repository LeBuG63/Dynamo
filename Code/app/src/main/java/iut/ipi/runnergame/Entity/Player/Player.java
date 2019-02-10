package iut.ipi.runnergame.Entity.Player;

import android.graphics.Bitmap;
import android.graphics.Point;

import iut.ipi.runnergame.Animation.IAnimationManager;
import iut.ipi.runnergame.Entity.AEntity;
import iut.ipi.runnergame.Entity.Collision.ICollidable;
import iut.ipi.runnergame.Entity.Collision.ICollision;

public class Player extends AEntity implements ICollidable {
    public Player(Point pos, ICollision collision, IAnimationManager animationManager) {
        super(pos, collision, animationManager);

        animationManager.start(0);
    }

    public Bitmap getSprite() {
        return animationManager.getFrame();
    }
}
