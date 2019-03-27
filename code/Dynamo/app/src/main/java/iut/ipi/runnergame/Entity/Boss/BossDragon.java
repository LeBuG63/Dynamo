package iut.ipi.runnergame.Entity.Boss;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import iut.ipi.runnergame.Engine.Graphics.Animation.AnimationManager;
import iut.ipi.runnergame.Engine.Graphics.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.TranslateUtil;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Player.AbstractPlayer;
import iut.ipi.runnergame.R;

public class BossDragon extends AbstractEntity implements Boss {
    private final int SIZE_SPRITE_WIDTH = 152;
    private final int SIZE_SPRITE_HEIGHT = 129;
    private final float BULLET_SPEED = 1.8f;

    private final double SPEED;

    private AnimationManager animationManager;
    private Timer timer;

    private TranslateUtil translateUtil = new TranslateUtil();
    private List<Bullet> bullets = new ArrayList<>();

    private AbstractPlayer refPlayer;
    private AbstractPoint offset = new Point();

    private Paint paint = new Paint();
    private boolean appeared = false;

    public BossDragon(final Context context, AbstractPoint pos, int shotRateMs, final AbstractPlayer player) {
        super(pos);

        refPlayer = player;

        SPEED =  WindowUtil.convertPixelsToDp(context, 10);

        try {
            animationManager = new BaseSpriteSheetAnimation(context, R.drawable.boss_dragon_1, 1, SIZE_SPRITE_WIDTH, SIZE_SPRITE_HEIGHT, 4, 200, 1, 4);
        } catch (IOException ignore) {
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(appeared) {
                    synchronized (bullets) {
                        float x = getPosition().x;
                        float y = getPosition().y + (animationManager.getFrame().getHeight() / 1.5f);

                        float angle = (float) Math.atan2(player.getPosition().y - y, AbstractPlayer.DEFAULT_POS.x - x);

                        x += getOffset().x;
                        y -= getOffset().y;

                        bullets.add(new Bullet(context, R.drawable.sprite_bullet_1, new Point(x, y), 0.5f, WindowUtil.convertDpToPixel(context, BULLET_SPEED), angle));
                    }
                }
            }
        }, 0, shotRateMs);

        bullets.clear();
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        synchronized (bullets) {
            for (Bullet bullet : bullets) {
                bullet.drawOnCanvas(canvas);
            }
        }

        canvas.drawBitmap(animationManager.getFrame(), getPosition().x, getPosition().y, paint);
    }

    @Override
    public void update(float dt) {
        getPosition().y = (int)(Math.sin(System.currentTimeMillis() * (SPEED / WindowDefinitions.HEIGHT) * dt) * (WindowDefinitions.HEIGHT - animationManager.getFrame().getHeight()));

        translateUtil.translateObject(this, refPlayer.getPosition().x - AbstractPlayer.DEFAULT_POS.x, 0);
        translateUtil.translateListObject(bullets, refPlayer.getPosition().x - AbstractPlayer.DEFAULT_POS.x, 0);

        Collision collision = refPlayer.getCollision();

        collision.setHeight(collision.getHeight()/2);
        collision.setWidth(collision.getWidth()/2f);

        collision.setTop(collision.getTop() + collision.getHeight());

        for(int i = 0; i < bullets.size(); ++i){
            Bullet bullet = bullets.get(i);

            bullet.update(dt);

            if(bullet.getCollision().isInCollision(collision)) {
                refPlayer.setDeath(true);
            }

            // si la balle sort de l ecran alors on l enleve
            if(bullet.getPosition().x < 0
            || bullet.getPosition().y < 0
            || bullet.getPosition().y > WindowDefinitions.HEIGHT) {
                bullets.remove(bullet);
            }
        }
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
    public void setOffset(float x, float y) {
        offset.x = x;
        offset.y = y;
    }

    @Override
    public void setAppeared(boolean b) {
        if (b == false)
            bullets.clear();
        else {
            appeared = b;
            animationManager.start(0);
        }
    }
}
