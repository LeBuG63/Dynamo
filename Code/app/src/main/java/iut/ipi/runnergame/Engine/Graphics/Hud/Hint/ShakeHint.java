package iut.ipi.runnergame.Engine.Graphics.Hud.Hint;

import android.content.Context;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.R;

public class ShakeHint extends AbstractHint {
    public ShakeHint(Context context, float scale, int totalFrame, int frameDuration, AbstractPoint pos) {
        super(context, R.drawable.sprite_hint, scale, totalFrame, frameDuration, pos);
    }
}
