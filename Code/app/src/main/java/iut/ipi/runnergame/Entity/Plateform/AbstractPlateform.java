package iut.ipi.runnergame.Entity.Plateform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Spritesheet.Spritesheet;

public abstract class AbstractPlateform extends AbstractEntity implements Collidable {
    public static final int DEFAULT_SCALE = 4;

    private Collision collision;

    private Spritesheet spritesheet;
    private List<Block> blocks = new ArrayList<>();

    private int length;

    public AbstractPlateform(Context context, int resourceId, PointF pos, int length) throws IOException {
        super(pos);

        this.length = length;

        spritesheet = new Spritesheet(context, resourceId, 1, 3, Spritesheet.DEFAULT_SPRITE_SIZE, Spritesheet.DEFAULT_SPRITE_SIZE, DEFAULT_SCALE);
        setCollision(new BaseCollisionBox(pos.x, pos.y, length * spritesheet.getFrameWidth(), spritesheet.getFrameHeight()));

        for(int blockId = 0; blockId < length; ++blockId) {
            float x = pos.x + (float) (blockId * spritesheet.getFrameWidth());
            float y = pos.y;

            Bitmap sprite = spritesheet.getSprite(0, 1);

            if (blockId == 0) {
                sprite = spritesheet.getSprite(0, 0);
            }
            else if (blockId == length - 1) {
                sprite = spritesheet.getSprite(0, 2);
            }

            blocks.add(new Block(new PointF(x, y), sprite));
        }
    }

    public void drawOnCanvas(Canvas canvas) {
        for(Block block : blocks) {
            canvas.drawBitmap(block.getImage(), block.getPosition().x, block.getPosition().y, new Paint());
        }
    }

    @Override
    public void setCollision(Collision collision) {
        this.collision = collision;
    }

    @Override
    public Collision getCollision() {
        return collision;
    }
}
