package iut.ipi.runnergame.Entity;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collision;

public abstract class AbstractEntity {
    protected PointF position = new PointF();

    protected RectF rectangle;
    protected Bitmap image;

    public AbstractEntity(PointF pos, Bitmap bitmap) {
        this(pos, bitmap.getWidth(), bitmap.getHeight());

        this.image = bitmap;
    }

    public AbstractEntity(PointF pos, int width, int height) {
        this(pos);

        this.rectangle = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);
    }

    public AbstractEntity(PointF pos) {
        this.position = pos;
    }

    public Bitmap getSprite() {
        return image;
    }

    public PointF getPosition() {
        return position;
    }

    public Bitmap getImage() {
        return image;
    }

    public RectF getRectangle() {
        return rectangle;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        this.rectangle = new RectF(getPosition().x, getPosition().y, getPosition().x + image.getWidth(), getPosition().y + image.getHeight());
    }

}
