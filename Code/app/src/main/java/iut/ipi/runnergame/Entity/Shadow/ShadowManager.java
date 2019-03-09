package iut.ipi.runnergame.Entity.Shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Timer;
import java.util.TimerTask;

import iut.ipi.runnergame.Engine.Sensor.Accelerometer;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Entity.Drawable;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Util.Constantes;

public class ShadowManager implements Drawable {
    private final int SHAKE_THRESHOLD = 40;

    private Player player;

    private Accelerometer accelerometer;
    private Shadow shadow;

    private Paint circlePaint = new Paint();
    private Paint shadowPaint = new Paint();

    private Path path = new Path();

    private float radiusOffset = 1.0f;
    private float shadowAmplitude = 10.0f;

    public ShadowManager(Context context, Player player, float shadowDecreaseValue, float shadowIncreaseValueRatio, int color) {
        shadow = new Shadow(shadowDecreaseValue, shadowIncreaseValueRatio);
        accelerometer = new Accelerometer(context, SHAKE_THRESHOLD);

        this.player = player;

        circlePaint.setColor(Color.TRANSPARENT);
        shadowPaint.setColor(Color.TRANSPARENT);

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               radiusOffset = WindowUtil.convertPixelsToDp((float)Math.cos(System.currentTimeMillis() ) * shadowAmplitude);
            }
        }, 0, 100);
    }

    public void update() {
        if(accelerometer.isShaked()) {
            shadow.addToRadius(accelerometer.getSpeed() / 100);
        }

        shadow.update();
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        path.reset();

        float cx = shadow.getPosition().x + player.getSprite().getWidth() / 2;
        float cy = player.getPosition().y + player.getSprite().getHeight() / 2;

        path.addCircle(cx, cy, shadow.getRadius() + radiusOffset, Path.Direction.CW);
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);

        canvas.drawCircle(cx, cy, shadow.getRadius(), circlePaint);

        canvas.drawPath(path, shadowPaint);
        canvas.clipPath(path);

        if(Constantes.DEBUG)
            canvas.drawColor(Color.parseColor("#AA000000"));
        else
            canvas.drawColor(Color.parseColor("#FF000000"));
    }
}
