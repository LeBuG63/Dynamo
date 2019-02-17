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
    public static final int DEFAULT_SCALE = 8;

    public static final int BLOCK_SPRITE_START = 0;
    public static final int BLOCK_SPRITE_INBETWEEN = 1;
    public static final int BLOCK_SPRITE_END = 2;

    public static final int N_COL_SPRITESHEET = 3;
    public static final int N_ROW_SPRITESHEET = 1;

    private Collision collision;
    private PointF offset = new PointF(0, 0);

    private Spritesheet spritesheet;
    private List<Block> blocks = new ArrayList<>();

    private int length;
    private int width;
    private int height;

    public AbstractPlateform(Context context, int resourceId, PointF pos, int length, int scale) throws IOException {
        super(pos, length * scale * Spritesheet.DEFAULT_SPRITE_SIZE, scale * Spritesheet.DEFAULT_SPRITE_SIZE);

        this.length = length;

        spritesheet = new Spritesheet(context, resourceId, N_ROW_SPRITESHEET, N_COL_SPRITESHEET, Spritesheet.DEFAULT_SPRITE_SIZE, Spritesheet.DEFAULT_SPRITE_SIZE, DEFAULT_SCALE);

        width = length * spritesheet.getFrameWidth();
        height = spritesheet.getFrameHeight();

        setCollision(new BaseCollisionBox(pos.x, pos.y, width, height));

        for(int blockId = 0; blockId < length; ++blockId) {
            float x = pos.x + (float) (blockId * spritesheet.getFrameWidth());
            float y = pos.y;

            Bitmap sprite = spritesheet.getSprite(0, BLOCK_SPRITE_INBETWEEN);

            if (blockId == 0) {
                sprite = spritesheet.getSprite(0, BLOCK_SPRITE_START);
            }
            else if (blockId == length - 1) {
                sprite = spritesheet.getSprite(0, BLOCK_SPRITE_END);
            }

            blocks.add(new Block(new PointF(x, y), sprite));
        }
    }

    public void drawOnCanvas(Canvas canvas) {
        for(Block block : blocks) {
            float x = block.getPosition().x - getOffset().x;
            float y = block.getPosition().y - getOffset().y;

            canvas.drawBitmap(block.getImage(), x, y, new Paint());
        }
    }

    @Override
    public PointF getPosition() {
        return new PointF(super.getPosition().x - getOffset().x, super.getPosition().y - getOffset().y);
    }

    public PointF getOffset() {
        return offset;
    }

    public void setOffset(PointF offset) {
        this.offset = offset;

        float x = getPosition().x;
        float y = getPosition().y;

        // il faut recalculer la hitbox a chaque modification de l offset
        setCollision(new BaseCollisionBox(x, y, width, height));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void setPosition(PointF position) {
        int blockId = 0;

        for (Block block : blocks) {
            float x = position.x + (float) (blockId * spritesheet.getFrameWidth());
            float y = position.y;

            block.setPosition(new PointF(x, y));

            blockId++;
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
