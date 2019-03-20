package iut.ipi.runnergame.Engine;

import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Entity.Translatable;

public class TranslateUtil<T extends Translatable> {
    public synchronized void translateObject(T object, float offsetx, float offsety) {
        object.setOffset(offsetx, offsety);
    }

    public void translateListObject(List<T> objects, float offsetx, float offsety) {
        for(T object : objects) {
            translateObject(object, offsetx, offsety);
        }
    }
}
