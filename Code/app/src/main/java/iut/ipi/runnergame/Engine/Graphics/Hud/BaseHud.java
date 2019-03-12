package iut.ipi.runnergame.Engine.Graphics.Hud;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Hud.Hint.AbstractHint;
import iut.ipi.runnergame.Engine.Graphics.Hud.Hint.Hint;
import iut.ipi.runnergame.Engine.Graphics.Hud.Hint.ShakeHint;
import iut.ipi.runnergame.Engine.Graphics.Hud.Input.BaseCrossClickable;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.Graphics.Point.PointRelative;
import iut.ipi.runnergame.Entity.Player.AbstractPlayer;
import iut.ipi.runnergame.R;

public class BaseHud implements Hud {
    private AbstractCross cross;
    private AbstractCross crossAB;

    private AbstractHint shakeHint;
    private AbstractPoint posHideHud = new PointRelative(0, 10);

    private final AbstractPoint defaultPointCross = new PointRelative(10, 70);
    private final AbstractPoint defaultPointCrossAB = new PointRelative(90, 70);

    private AbstractPlayer refPlayer;
    private List<AbstractPoint> refPointFingerPressed;

    public BaseHud(Context context, final AbstractPlayer player, final List<AbstractPoint> pointFingerPressed) {
        cross = new BaseCrossClickable(context, R.drawable.sprite_cross_1, BaseCrossClickable.DEFAULT_SCALE, 4, defaultPointCross);
        crossAB = new BaseCrossClickable(context, R.drawable.sprite_cross_ab, BaseCrossClickable.DEFAULT_SCALE, 1, defaultPointCrossAB);

        shakeHint = new ShakeHint(context, 0.5f, 4, 200, new PointRelative(45, 20));
        shakeHint.show();

        refPlayer = player;
        refPointFingerPressed = pointFingerPressed;
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {
        shakeHint.drawOnCanvas(canvas);
        cross.drawOnCanvas(canvas);
        crossAB.drawOnCanvas(canvas);
    }

    @Override
    public void update(float dt) {
        if(refPlayer.getPosition().x > posHideHud.x) {
            shakeHint.hide();
        }

        cross.updateArrowPressed(refPointFingerPressed );
        crossAB.updateArrowPressed(refPointFingerPressed );
    }

    @Override
    public AbstractHint getHint() {
        return shakeHint;
    }

    @Override
    public AbstractCross getCross() {
        return cross;
    }

    public AbstractCross getCrossAB() {
        return crossAB;
    }
}
