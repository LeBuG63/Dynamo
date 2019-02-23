package iut.ipi.runnergame.Entity.Plateform;

import android.graphics.Bitmap;

import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Util.Point.AbstractPoint;
import iut.ipi.runnergame.Util.Point.PointScaled;

public class Block extends AbstractEntity {
    public Block(AbstractPoint pos, Bitmap bitmap) {
        super(pos, bitmap);
    }
}
