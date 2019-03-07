package iut.ipi.runnergame.Entity.Gameplay;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.IOException;

import iut.ipi.runnergame.Animation.Animable;
import iut.ipi.runnergame.Animation.AnimationManager;
import iut.ipi.runnergame.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Translatable;
import iut.ipi.runnergame.Util.Point.AbstractPoint;
import iut.ipi.runnergame.Util.Point.Point;

public class Piece extends AbstractEntity implements Collidable, Animable, Translatable {
    private static int DEFAULT_SIZE = 3;

    public static int VALUE_LOW = 100;
    public static int VALUE_NORMAL = 250;
    public static int VALUE_HIGH = 500;

    private AnimationManager animationManager;
    private Collision collision;

    private AbstractPoint offset = new Point();

    private int value = 0;
    private boolean needToBeDeleted = false;

    public Piece(Context context, AbstractPoint pos, int resourceId, int value) {
        super(pos);

        this.value = value;

        try {
            animationManager = new BaseSpriteSheetAnimation(context, resourceId, DEFAULT_SIZE, 4, 250, 1, 4);
            animationManager.start(0);

            setCollision(new BaseCollisionBox(pos.x, pos.y, getSprite().getWidth(), getSprite().getHeight()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public Bitmap getSprite() {
        return animationManager.getFrame();
    }

    @Override
    public AbstractPoint getOffset() {
        return offset;
    }
    @Override
    public void setOffset(AbstractPoint offset) {
        this.offset = offset;

        float x = getPosition().x;
        float y = getPosition().y;

        setCollision(new BaseCollisionBox(x - offset.x, y - offset.y, getSprite().getWidth(), getSprite().getHeight()));
    }

    @Override
    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    @Override
    public Collision getCollision() {
        return collision;
    }

    @Override
    public void updateBecauseCollision() {
        this.needToBeDeleted = true;
    }
}
