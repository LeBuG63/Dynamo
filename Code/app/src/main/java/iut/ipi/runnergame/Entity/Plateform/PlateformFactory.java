package iut.ipi.runnergame.Entity.Plateform;

import android.content.Context;
import iut.ipi.runnergame.Util.PointScaled;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import iut.ipi.runnergame.Entity.Plateform.TypesPlateform.SimplePlateform;
import iut.ipi.runnergame.R;

public class PlateformFactory {
    private PlateformFactory() {}

    public static AbstractPlateform create(Context context, PlateformType type, PointScaled position, int lenght, int scale) throws IOException {
        AbstractPlateform plateform = null;

        switch (type) {
            case SIMPLE:
                plateform = new SimplePlateform(context, R.drawable.sprite_simple_plateform_1, position, lenght, scale);
                break;
            case FROZEN:
                plateform = new SimplePlateform(context, R.drawable.sprite_frozen_plateform_1, position, lenght, scale);
                break;
        }

        return plateform;
    }
}
