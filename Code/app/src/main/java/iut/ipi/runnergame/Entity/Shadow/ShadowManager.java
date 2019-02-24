package iut.ipi.runnergame.Entity.Shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Sensor.Accelerometer;
import iut.ipi.runnergame.Util.Constantes;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ShadowManager  {
    private Accelerometer accelerometer;
    private Shadow shadow;

    private Paint circlePaint = new Paint();
    private Paint shadowPaint = new Paint();

    private Random randomGenerator = new Random();

    private Path path = new Path();

    private float radiusOffset = 1.0f;
    private float shadowAmplitude = 15.0f;
    private float accelSpeed = 0;

    public ShadowManager(Context context, int shadowDecreaseValue, float shadowIncreaseValueRatio, int color) {
        shadow = new Shadow(shadowDecreaseValue, shadowIncreaseValueRatio);
        accelerometer = new Accelerometer(context);

        circlePaint.setColor(Color.TRANSPARENT);
        shadowPaint.setColor(Color.TRANSPARENT);
        //circlePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               radiusOffset = (float)Math.cos(System.currentTimeMillis() ) * shadowAmplitude;
            }
        }, 0, 100);
    }

    public void update() {
        float speed = accelerometer.getSpeed();

        if(accelSpeed  != speed) {
            accelSpeed = speed;

            //shadow.addToRadius(speed);
        }

        shadow.update();
    }

    public void drawShadowToCanvas(Canvas canvas, Player player) {
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
