package iut.ipi.runnergame.Entity;

import android.graphics.Bitmap;
import android.graphics.RectF;

import iut.ipi.runnergame.Util.Point.AbstractPoint;

public abstract class AbstractEntity {
    public static int DEFAULT_SCALE = 2;
    private AbstractPoint position;

    private RectF rectangle;
    private Bitmap image;

    public AbstractEntity(AbstractPoint pos) {
        this.position = pos;
    }

    public AbstractEntity(AbstractPoint pos, Bitmap bitmap) {
        this(pos, bitmap.getWidth(), bitmap.getHeight());

        setImage(bitmap);
    }

    public AbstractEntity(AbstractPoint pos, int width, int height) {
        this(pos);

        this.rectangle = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);
    }

    public Bitmap getSprite() {
        return image;
    }

    public AbstractPoint getPosition() {
        return position;
    }

    public Bitmap getImage() {
        return image;
    }

    public RectF getRectangle() {
        return rectangle;
    }

    public void setPosition(AbstractPoint position) {
        this.position = position;

        if(getImage() != null)
            this.rectangle = new RectF(getPosition().x, getPosition().y, getPosition().x + getImage().getWidth(), getPosition().y + getImage().getHeight());
    }

    public void setImage(Bitmap image) {
        this.image = image;
        this.rectangle = new RectF(getPosition().x, getPosition().y, getPosition().x + image.getWidth(), getPosition().y + image.getHeight());
    }
}
