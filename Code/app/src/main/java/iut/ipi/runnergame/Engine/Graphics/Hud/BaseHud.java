package iut.ipi.runnergame.Engine.Graphics.Hud;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Hud.Hint.AbstractHint;
import iut.ipi.runnergame.Engine.Graphics.Hud.Hint.ShakeHint;
import iut.ipi.runnergame.Engine.Graphics.Hud.Input.BaseCrossClickable;
import iut.ipi.runnergame.Engine.Graphics.Hud.Input.RectangleButton;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.PointRelative;
import iut.ipi.runnergame.Entity.Player.AbstractPlayer;
import iut.ipi.runnergame.R;

public class BaseHud implements Hud {
    public static final int BUT_EXIT = 0;
    public static final int BUT_MUTE = 1;

    private AbstractCross cross;
    private AbstractCross crossAB;

    private AbstractHint shakeHint;
    private AbstractPoint posHideHud = new PointRelative(0, 10);

    private final AbstractPoint defaultPointCross = new PointRelative(10, 60);
    private final AbstractPoint defaultPointCrossAB = new PointRelative(90, 60);

    private AbstractPlayer refPlayer;
    private List<AbstractPoint> refPointFingerPressed;
    private List<RectangleButton> buttons = new ArrayList<>();

    public BaseHud(Context context, final AbstractPlayer player, final List<AbstractPoint> pointFingerPressed) {
        cross = new BaseCrossClickable(context, R.drawable.sprite_cross_1, BaseCrossClickable.DEFAULT_SCALE, 4, defaultPointCross);
        crossAB = new BaseCrossClickable(context, R.drawable.sprite_cross_ab, BaseCrossClickable.DEFAULT_SCALE, 1, defaultPointCrossAB);

        buttons.add(new RectangleButton(context, R.drawable.sprite_close, 2, new PointRelative(0,0), 400));
        buttons.add(new RectangleButton(context, R.drawable.sprite_music_on, 2, new PointRelative(90,0), 400));

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

        for(RectangleButton rectangleButton : buttons) {
            rectangleButton.drawOnCanvas(canvas);
        }
    }

    @Override
    public void update(float dt) {
        if(refPlayer.getPosition().x > posHideHud.x) {
            shakeHint.hide();
        }

        cross.updateArrowPressed(refPointFingerPressed);
        crossAB.updateArrowPressed(refPointFingerPressed);

        for(RectangleButton rectangleButton : buttons) {
            rectangleButton.updatePressed(refPointFingerPressed);
        }
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

    @Override
    public RectangleClickable getButton(int index) {
        return buttons.get(index);
    }
}
