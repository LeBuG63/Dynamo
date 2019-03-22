package iut.ipi.runnergame.Game.Level.Background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import iut.ipi.runnergame.Engine.Graphics.BitmapResizer;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.R;

public class GameBackground extends AbstractEntity implements Background {
    private AbstractPoint offset = new Point();

    private Paint paint = new Paint();
    private Bitmap[] backgroundBitmaps = new Bitmap[2];

    private int step = 0;

    public GameBackground(Context context) {
        super(new Point(0,0));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        // ici on charge 2x le meme background car ils se completent
        // mais on peut facilement en mettre 2 ou plus pour creer un effet encore meilleurs
        backgroundBitmaps[0] = BitmapResizer.bitmapResizerNN(BitmapFactory.decodeResource(context.getResources(), R.drawable.background_1, options), (int)WindowDefinitions.WIDTH, (int)WindowDefinitions.HEIGHT);
        backgroundBitmaps[1] = BitmapResizer.bitmapResizerNN(BitmapFactory.decodeResource(context.getResources(), R.drawable.background_1, options), (int)WindowDefinitions.WIDTH, (int)WindowDefinitions.HEIGHT);
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

    private int lastStep = 0;
    @Override
    public void drawOnCanvas(Canvas canvas) {
        float x = getPosition().x - getOffset().x;
        float y = getPosition().y - getOffset().y;


        // permet de creer un effet de scrolling infini
        // il y a 2 backgrounds, des qu'un est completement affiché alors l autre va passer soit sur le coté gauche, soit sur le coté
        // en fonction de la direction du joueur
        step = (int)(x / -WindowDefinitions.WIDTH);

        if(step != lastStep) {
            lastStep = step;

            Bitmap tmp = backgroundBitmaps[0];
            backgroundBitmaps[0] = backgroundBitmaps[1];
            backgroundBitmaps[1] = tmp;
        }

        canvas.drawBitmap(backgroundBitmaps[0], x + WindowDefinitions.WIDTH * (step), y, paint);
        canvas.drawBitmap(backgroundBitmaps[1], x + WindowDefinitions.WIDTH * (step + 1), y, paint);
    }

    @Override
    public void update(float dt) {

    }
}
