package iut.ipi.runnergame.Entity.Player;

import android.graphics.Bitmap;
import android.graphics.Point;

import iut.ipi.runnergame.Animation.AnimationManager;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;

public class Player extends AbstractEntity implements Collidable {
    public static final float IMPULSE_MOVEMENT = 10.0f;
    public static final float IMPULSE_JUMP = 40.0f;

    public static final int ANIMATION_IDLE = 0;
    public static final int ANIMATION_RUNNING_RIGHT = 1;
    public static final int ANIMATION_RUNNING_LEFT = 2;

    public Player(Point pos, Collision collision, AnimationManager animationManager) {
        super(pos, collision, animationManager);

        animationManager.start(0);
    }

    public Bitmap getSprite() {
        return animationManager.getFrame();
    }

}
