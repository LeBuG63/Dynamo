package iut.ipi.runnergame.Entity.Plateform.TypesPlateform;

import android.content.Context;
import android.graphics.PointF;

import java.io.IOException;

import iut.ipi.runnergame.Entity.Collision.Collision;
import iut.ipi.runnergame.Entity.Plateform.AbstractPlateform;

public class SimplePlateform extends AbstractPlateform {
    public SimplePlateform(Context context, int resourceId, PointF pos, int length, int scale) throws IOException {
        super(context, resourceId, pos, length, scale);
    }
}
