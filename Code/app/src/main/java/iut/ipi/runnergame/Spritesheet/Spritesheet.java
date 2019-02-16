package iut.ipi.runnergame.Spritesheet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iut.ipi.runnergame.Util.BitmapResizer;

public class Spritesheet {
    private final int row;
    private final int col;
    private final int frameWidth;
    private final int frameHeight;
    private final int scale;

    private Map<Integer, List<Bitmap>> bitmapMap = new HashMap<>();

    public Spritesheet(Context context, int resourceId, int row, int col, int frameWidth, int frameHeight, int scale) throws IOException {
        this.row = row;
        this.col = col;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.scale = scale;

        Bitmap spritesheet = null;

        spritesheet = BitmapFactory.decodeResource(context.getResources(), resourceId);

        if(spritesheet == null) {
            throw new IOException("spritesheet not found: " + resourceId);
        }

        cutSpritesheet(spritesheet);
    }

    private void cutSpritesheet(Bitmap spritesheet) {
        for(int y = 0; y < getRow(); ++y){
            bitmapMap.put(y, new ArrayList<Bitmap>());

            for (int x = 0; x < getCol(); ++x) {
                Bitmap frame = null;

                frame = Bitmap.createBitmap(spritesheet, getFrameWidth() * x, getFrameHeight() * y, getFrameWidth(), getFrameHeight());
                frame = BitmapResizer.bitmapResizerPixelPerfect(frame, getFrameWidth() * getScale(), getFrameHeight() * getScale());

                bitmapMap.get(y).add(frame);
            }
        }
    }

    public Map<Integer, List<Bitmap>> getSprites() {
        return bitmapMap;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public int getScale() {
        return scale;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
