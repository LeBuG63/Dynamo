package iut.ipi.runnergame.Engine;

import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Entity.Translatable;

public class TranslateUtil<T extends Translatable> {
    /**
     * permet de translater un objet en fonction de l offset x et y
     * @param object l objet a translater
     * @param offsetx l offset sur l axe x
     * @param offsety l offset sur l axe y
     */
    public synchronized void translateObject(T object, float offsetx, float offsety) {
        object.setOffset(offsetx, offsety);
    }

    /**
     * permet de translater une liste d objets en fonction de l offset x et y
     * @param objects la liste d objets a translater
     * @param offsetx l offset sur l axe x
     * @param offsety l offset sur l axe y
     */
    public void translateListObject(List<T> objects, float offsetx, float offsety) {
        for(T object : objects) {
            translateObject(object, offsetx, offsety);
        }
    }
}
