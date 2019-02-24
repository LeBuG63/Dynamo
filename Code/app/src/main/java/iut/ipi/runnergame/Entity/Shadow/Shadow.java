package iut.ipi.runnergame.Entity.Shadow;

import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Util.Point.PointRelative;
import iut.ipi.runnergame.Util.WindowUtil;

public class Shadow extends AbstractEntity {
    private float radius = 11;

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
        if(radius > WindowUtil.convertPixelsToDp(200))
            radius -= shadowDecreaseValue;
    }
    public float getRadius() {
        return radius;
    }
}
