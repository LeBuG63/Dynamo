package iut.ipi.runnergame.Entity.Shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Util.Point.AbstractPoint;
import iut.ipi.runnergame.Util.Point.PointScaled;
import iut.ipi.runnergame.Util.WindowDefinitions;
import iut.ipi.runnergame.Util.WindowUtil;

public class Shadow extends AbstractEntity {
    private float radius = 0;

    private float shadowDecreaseValue;
    private float shadowIncreaseValueRatio;

    public Shadow(float shadowDecreaseValue, float shadowIncreaseValueRatio) {
        super(new PointScaled(WindowUtil.ScaleFloatToWindow(WindowDefinitions.widthPixels), WindowDefinitions.heightPixels / 2));

        this.shadowDecreaseValue = WindowUtil.ScaleFloatToWindow(shadowDecreaseValue);
        this.shadowIncreaseValueRatio = WindowUtil.ScaleFloatToWindow(shadowIncreaseValueRatio);
    }

    public void addToRadius(float value) {
        radius += value * shadowIncreaseValueRatio;
    }

    public void subToRadius(float value) {
        radius += value;
    }

    public void update() {
        if(radius > WindowUtil.ScaleFloatToWindow(100))
            radius -= shadowDecreaseValue;
    }
    public float getRadius() {
        return radius;
    }
}
