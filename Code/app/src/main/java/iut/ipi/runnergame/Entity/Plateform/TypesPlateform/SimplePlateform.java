package iut.ipi.runnergame.Entity.Plateform.TypesPlateform;

import android.content.Context;

import java.io.IOException;

import iut.ipi.runnergame.Entity.Plateform.AbstractPlateform;
import iut.ipi.runnergame.Util.Point.AbstractPoint;

public class SimplePlateform extends AbstractPlateform {
    public SimplePlateform(Context context, int resourceId, AbstractPoint pos, int length) throws IOException {
        super(context, resourceId, pos, length);
    }
}
