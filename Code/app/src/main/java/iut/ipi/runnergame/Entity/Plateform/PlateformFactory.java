package iut.ipi.runnergame.Entity.Plateform;

import android.content.Context;

import java.io.IOException;

import iut.ipi.runnergame.Entity.Plateform.TypesPlateform.SimplePlateform;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Util.Point.AbstractPoint;

public class PlateformFactory {
    private PlateformFactory() {}

    public static AbstractPlateform create(Context context, PlateformType type, AbstractPoint position, int lenght) throws IOException {
        AbstractPlateform plateform = null;

        switch (type) {
            case SIMPLE:
                plateform = new SimplePlateform(context, R.drawable.sprite_simple_plateform_1, position, lenght);
                break;
            case FROZEN:
                plateform = new SimplePlateform(context, R.drawable.sprite_frozen_plateform_1, position, lenght);
                break;
        }

        return plateform;
    }
}
