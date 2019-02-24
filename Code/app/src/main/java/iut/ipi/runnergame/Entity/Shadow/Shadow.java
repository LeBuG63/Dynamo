package iut.ipi.runnergame.Entity.Shadow;

import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Util.Point.PointRelative;
import iut.ipi.runnergame.Util.WindowUtil;

public class Shadow extends AbstractEntity {
    private float defaultRadius = WindowUtil.convertPixelsToDp(200);
    private float radius = defaultRadius;

    private float shadowDecreaseValue;
    private float shadowIncreaseValueRatio;

    public Shadow(float shadowDecreaseValue, float shadowIncreaseValueRatio) {
        super(new PointRelative(50.0f, 50.0f));

        this.shadowDecreaseValue = shadowDecreaseValue;
        this.shadowIncreaseValueRatio = shadowIncreaseValueRatio;
    }

    public void addToRadius(float value) {
        if(radius < WindowUtil.convertPixelsToDp(2000))
            radius += value * shadowIncreaseValueRatio;
    }

    public void subToRadius(float value) {
        radius -= value * shadowDecreaseValue;
    }

    public void update() {
        if(radius > defaultRadius)
            radius -= shadowDecreaseValue;
        else
            radius = defaultRadius;
    }
    public float getRadius() {
        return radius;
    }
}
