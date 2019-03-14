package iut.ipi.runnergame.Entity.Plateform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.Graphics.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.BaseCollisionBox;
import iut.ipi.runnergame.Entity.Collision.Collidable;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Translatable;

public abstract class AbstractPlateform extends AbstractEntity implements Collidable, Translatable {
    public static final int BLOCK_SPRITE_START = 0;
    public static final int BLOCK_SPRITE_INBETWEEN = 1;
    public static final int BLOCK_SPRITE_END = 2;
    public static final int BLOCK_SPRITE_ONLY = 3;

    public static final int N_COL_SPRITESHEET = 4;
    public static final int N_ROW_SPRITESHEET = 1;

    private Collision collision;
    private AbstractPoint offset = new Point();

    private Spritesheet spritesheet;
    private List<Block> blocks = new ArrayList<>();

    private int length;
    private int width;
    private int height;

    private final Context context;

    public AbstractPlateform(Context context, int resourceId, AbstractPoint pos, int length) throws IOException {
        super(pos, (int)(length * AbstractEntity.DEFAULT_SCALE * Spritesheet.DEFAULT_SPRITE_SIZE), (int)(AbstractEntity.DEFAULT_SCALE * Spritesheet.DEFAULT_SPRITE_SIZE));

        //setPosition(new Point(WindowUtil.ScaleFloatToWindow(pos.x), WindowUtil.ScaleFloatToWindow(pos.y)));

        this.context = context;
        this.length = length;

        spritesheet = new Spritesheet(context, resourceId, N_ROW_SPRITESHEET, N_COL_SPRITESHEET, Spritesheet.DEFAULT_SPRITE_SIZE, Spritesheet.DEFAULT_SPRITE_SIZE, AbstractEntity.DEFAULT_SCALE, false);

        width = length * spritesheet.getFrameWidth();
        height = spritesheet.getFrameHeight();

        setCollision(new BaseCollisionBox(context, pos.x, pos.y, width, height));

        for(int blockId = 0; blockId < length; blockId++) {
            float x = pos.x + (float) (blockId * spritesheet.getFrameWidth());
            float y = pos.y;

            Bitmap sprite = spritesheet.getSprite(0, BLOCK_SPRITE_INBETWEEN);

            if (blockId == 0) {
                if (length == 1) {
                    sprite = spritesheet.getSprite(0, BLOCK_SPRITE_ONLY);
                } else {
                    sprite = spritesheet.getSprite(0, BLOCK_SPRITE_START);
                }
            }
            else if (blockId == length - 1) {
                sprite = spritesheet.getSprite(0, BLOCK_SPRITE_END);
            }

            blocks.add(new Block(new Point(x, y), sprite));
        }
    }

    public void drawOnCanvas(Canvas canvas) {
        for(Block block : blocks) {
            float x = block.getPosition().x - getOffset().x;
            float y = block.getPosition().y - getOffset().y;

            if (x > 0 - block.getSprite().getWidth() && y > 0 && y < WindowDefinitions.HEIGHT) {
                if (x > WindowDefinitions.WIDTH) {
                    break;
                }
                else {
                    canvas.drawBitmap(block.getImage(), x, y, new Paint());
                }
            }
        }
    }

    @Override
    public void updateBecauseCollision() {
        // on ne fait rien si il y a collision
    }

    @Override
    public AbstractPoint getPosition() {
        return new Point(super.getPosition().x - getOffset().x, super.getPosition().y - getOffset().y);
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

        // il faut recalculer la hitbox a chaque modification de l offset
        setCollision(new BaseCollisionBox(context, x, y, width, height));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void setPosition(AbstractPoint position) {
        int blockId = 0;

        for (Block block : blocks) {
            float x = position.x + (float) (blockId * spritesheet.getFrameWidth());
            float y = position.y;

            block.setPosition(new Point(x, y));

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
