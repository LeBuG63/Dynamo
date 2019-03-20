package iut.ipi.runnergame.Entity.Shadow;

import android.content.Context;

import iut.ipi.runnergame.Engine.Graphics.Point.PointRelative;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Entity.AbstractEntity;

public class Shadow extends AbstractEntity {
    private float defaultRadius;
    private float maxRadius;
    private float radius;

    private float shadowDecreaseValue;
    private float shadowIncreaseValueRatio;

    public Shadow(Context context, float shadowDecreaseValue, float shadowIncreaseValueRatio) {
        super(new PointRelative(50.0f, 50.0f));

        defaultRadius = WindowUtil.convertPixelsToDp(context,80) * WindowDefinitions.SCREEN_ADJUST;
        maxRadius = WindowUtil.convertPixelsToDp(context,2000) * WindowDefinitions.SCREEN_ADJUST;

        radius = maxRadius;

        this.shadowDecreaseValue = shadowDecreaseValue;
        this.shadowIncreaseValueRatio = shadowIncreaseValueRatio;
    }

    public void addToRadius(float value) {
        if(radius < maxRadius)
            radius += value * shadowIncreaseValueRatio;
    }

    public void update(float dt) {
        if(radius > defaultRadius)
            radius -= shadowDecreaseValue * dt;
        else
            radius = defaultRadius;
    }
    public float getRadius() {
        return radius;
    }
    public float getMinRadius() {
        return defaultRadius;
    }
}
