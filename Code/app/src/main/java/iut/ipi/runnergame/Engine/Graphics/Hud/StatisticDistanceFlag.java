package iut.ipi.runnergame.Engine.Graphics.Hud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.Graphics.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Entity.Drawable;

public class StatisticDistanceFlag implements Drawable {
    private Spritesheet spritesheet;


    private Paint paint = new Paint();
    private AbstractPoint position;
    private AbstractPoint positionArrow;

    private int lenght;
    private float offsetY;
    private float arrowAmplitude;

    public StatisticDistanceFlag(Context context, int resourceId, int size, int lenght, int playerDistance, int levelLength, float arrowAmp, AbstractPoint position) {
        this.lenght = lenght;
        this.position = position;
        this.arrowAmplitude = arrowAmp;

        if(playerDistance < 0)
            playerDistance = 0;
        else if(playerDistance > levelLength)
            playerDistance = levelLength;


        try {
            spritesheet = new Spritesheet(context, resourceId, 1, 4, size);

            float arrowPercentage = ((Spritesheet.DEFAULT_SPRITE_SIZE * size) * (lenght - 1)) * ((float)playerDistance / levelLength);
            float arrowOffset =  arrowPercentage * WindowDefinitions.SCREEN_ADJUST;

            this.positionArrow = new Point(position.x + arrowOffset, position.y);

        } catch (IOException e) { }

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                offsetY = -(float)Math.cos(System.currentTimeMillis() / 75) * arrowAmplitude;
            }
        }, 0, 5);
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        for(int index = 0; index < lenght; ++index) {
            float left = position.x + spritesheet.getFrameWidth() * index;
            float top = position.y;

            if(index == 0) {
                canvas.drawBitmap(spritesheet.getSprite(0, 0), left, top, paint);
            }
            else if(index == lenght - 1) {
                canvas.drawBitmap(spritesheet.getSprite(0, 2), left, top, paint);
            }
            else {
                canvas.drawBitmap(spritesheet.getSprite(0, 1), left, top, paint);
            }

            canvas.drawBitmap(spritesheet.getSprite(0, 3), positionArrow.x, positionArrow.y + offsetY, paint);
        }
    }
}
