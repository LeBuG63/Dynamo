package iut.ipi.runnergame.Game.Level.Background;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.PointRelative;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Entity.Player.BasePlayer;
import iut.ipi.runnergame.Entity.Player.Player;

public class StarsBackground implements Background {
    private class Star {
        static final float SPEED = -2500.0f;
        AbstractPoint position;
        int length;

        Star(AbstractPoint position, int length) {
            this.position = position;
            this.length = length;
        }
    }

    private AbstractPoint offset;
    private List<Star> stars = new ArrayList<>();

    private Player player;

    private Context context;
    private Random random = new Random();

    private Paint paint;

    public StarsBackground(Context context, int starCount) {
        this.context = context;

        try {
            player = new BasePlayer(context, new PointRelative(45, 40), Player.DEFAULT_SCALE * 4);
        } catch (IOException ignore) { }

        AbstractPoint.resizePointsInPool();

        for(int i = 0;i < starCount; ++i) {
            float x = random.nextFloat() * 100;
            float y = random.nextFloat() * 100;

            int radius = (int)WindowUtil.convertPixelsToDp(250);

            stars.add(new Star(new PointRelative(x, y), radius));
        }

        player.getAnimationManager().setDurationFrame(Player.ANIMATION_FALLING, 100);
        player.getAnimationManager().start(Player.ANIMATION_FALLING);

        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    @Override
    public AbstractPoint getOffset() {
        return offset;
    }

    @Override
    public void setOffset(AbstractPoint offset) {
        this.offset = offset;
    }

    @Override
    public void update(float dt) {
        for(Star star : stars) {
            star.position.y += Star.SPEED * dt;

            if (star.position.y < -star.length) {
                float x = random.nextFloat() * 100;
                float y = 100;

                star.position = new PointRelative(x, y);
            }
        }
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        for(Star star : stars) {
            canvas.drawRect(star.position.x, star.position.y, star.position.x + 10, star.position.y + star.length, paint);
        }

        canvas.drawBitmap(player.getSprite(), player.getPosition().x, player.getPosition().y, paint);
    }
}
