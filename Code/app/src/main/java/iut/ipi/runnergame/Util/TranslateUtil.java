package iut.ipi.runnergame.Util;

import java.util.List;

import iut.ipi.runnergame.Entity.Translatable;
import iut.ipi.runnergame.Util.Point.Point;

public class TranslateUtil<T extends Translatable> {
    public void translateObject(T object, float offsetx, float offsety) {
        object.setOffset(new Point(offsetx, offsety));
    }

    public void translateListObject(List<T> objects, float offsetx, float offsety) {
        for(T object : objects) {
            translateObject(object, offsetx, offsety);
        }
    }
}
