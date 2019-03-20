package iut.ipi.runnergame.Entity.Boss;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;

import iut.ipi.runnergame.Engine.Graphics.Animation.AnimationManager;
import iut.ipi.runnergame.Engine.Graphics.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Drawable;
import iut.ipi.runnergame.Entity.Translatable;
import iut.ipi.runnergame.Entity.Updatable;

public class Bullet extends AbstractEntity implements Drawable, Updatable, Collidable, Translatable {
    private Paint paint = new Paint();
    private AnimationManager animationManager;

    private AbstractPoint offset = new Point();

    private float speed;
    private float angle;

    private Collision collision;
    private final Context context;

    public Bullet(Context context, int resourceId, AbstractPoint pos, float scale, float speed, float angle) {
        super(pos);

        this.context = context;
        this.speed = speed;
        this.angle = angle;

        collision = new BaseCollisionBox(context);

        try {
            animationManager = new BaseSpriteSheetAnimation(context, resourceId, scale, 4, 200, 1, 4);
            animationManager.start(0);
        } catch (IOException ignore) {
        }
    }

    public float getSpeed() {
        return speed;
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

    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        canvas.drawBitmap(animationManager.getFrame(), getPosition().x - getOffset().x, getPosition().y - getOffset().y, paint);
    }

    @Override
    public void update(float dt) {
        getPosition().x += Math.cos(angle) * getSpeed();
        getPosition().y += Math.sin(angle) * getSpeed();

        collision.set(getPosition().x - getOffset().x, getPosition().y - getOffset().y, animationManager.getFrame().getWidth(), animationManager.getFrame().getHeight());
    }

    @Override
    public AbstractPoint getOffset() {
        return offset;
    }

    @Override
    public void setOffset(AbstractPoint offset) {
        this.offset = offset;
    }

    @Override
    public void setOffset(float x, float y) {
        offset.x = x;
        offset.y = y;
    }
}
