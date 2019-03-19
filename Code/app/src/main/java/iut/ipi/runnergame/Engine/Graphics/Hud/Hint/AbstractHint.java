package iut.ipi.runnergame.Engine.Graphics.Hud.Hint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;

import iut.ipi.runnergame.Engine.Graphics.Animation.AnimationManager;
import iut.ipi.runnergame.Engine.Graphics.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Entity.AbstractEntity;

public abstract class AbstractHint extends AbstractEntity implements Hint {
    private static int DEFAULT_FRAME_WIDTH = 64;
    private static int DEFAULT_FRAME_HEIGHT = 64;
    private static int DEFAULT_N_ROW = 1;

    private AnimationManager animationManager;

    private Paint paint = new Paint();
    private boolean show = true;

    public AbstractHint(Context context, int resourceId, float scale, int totalFrame, int frameDuration, AbstractPoint pos) {
        super(pos);

        try {
            animationManager = new BaseSpriteSheetAnimation(context, resourceId, scale, DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT, totalFrame, frameDuration, DEFAULT_N_ROW, totalFrame);
            animationManager.start(0);
        } catch (IOException ignore) {}
    }

    public void hide() {
        show = false;
    }

    public void show() {
        show = true;
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        if(show) {
            canvas.drawBitmap(animationManager.getFrame(), getPosition().x, getPosition().y, paint);
        }
    }
}
