package iut.ipi.runnergame.Engine.Graphics.Spritesheet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import iut.ipi.runnergame.Engine.Graphics.BitmapResizer;
import iut.ipi.runnergame.Engine.WindowDefinitions;

public class Spritesheet {
    public static final int DEFAULT_SPRITE_SIZE = 16;

    private final int row;
    private final int col;
    private final int frameWidth;
    private final int defaultFrameWidth;
    private final int frameHeight;
    private final int defaultFrameHeight;
    private final int scale;

    private Map<Integer, List<Bitmap>> bitmapMap = new HashMap<>();

    public Spritesheet(Context context, int resourceId, int row, int col, int scale) throws IOException {
        this(context, resourceId, row, col, DEFAULT_SPRITE_SIZE, DEFAULT_SPRITE_SIZE, scale);
    }
    public Spritesheet(Context context, int resourceId, int row, int col, int frameWidth, int frameHeight, int scale) throws IOException {
        this.row = row;
        this.col = col;

        this.defaultFrameWidth = frameWidth;
        this.defaultFrameHeight = frameHeight;

        this.frameWidth = (int)(frameWidth * scale * WindowDefinitions.DENSITY);
        this.frameHeight = (int)(frameHeight * scale * WindowDefinitions.DENSITY);
        this.scale = scale;

        Bitmap spritesheet = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        spritesheet = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        if(spritesheet == null) {
            throw new IOException("spritesheet not found: " + resourceId);
        }

        cutSpritesheet(spritesheet);
    }


    private Object key = new Object();
    private void cutSpritesheet(Bitmap spritesheet) {
        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final Bitmap finalSpritesheet = spritesheet;

        try {
            final boolean first = true;

            for(int yy = 0; yy < getRow(); yy++) {
                bitmapMap.put(yy, new ArrayList<Bitmap>());
            }

            for(int yy = 0; yy < getRow(); ++yy){
                final int y = yy;

                exec.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int x = 0; x < getCol(); ++x) {
                            Bitmap frame = null;

                            frame = Bitmap.createBitmap(finalSpritesheet,
                                    getDefaultFrameWidth() * x, getDefaultFrameHeight() * y,
                                    getDefaultFrameWidth(), getDefaultFrameHeight());

                            synchronized (key) {
                                bitmapMap.get(y).add(BitmapResizer.bitmapResizerNN(frame, getFrameWidth(), getFrameHeight()));
                            }
                        }
                    }
                });
            }
        } finally {
            exec.shutdown();
        }
        while(!exec.isTerminated()) {}
    }

    public Map<Integer, List<Bitmap>> getSprites() {
        return bitmapMap;
    }

    public Bitmap getSprite(int row, int col) {
        if(col > 0)
            return bitmapMap.get(row).get(col);

        return bitmapMap.get(row).get(0);
    }

    public int getDefaultFrameWidth() {
        return defaultFrameWidth;
    }

    public int getDefaultFrameHeight() {
        return defaultFrameHeight;
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
