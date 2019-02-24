package iut.ipi.runnergame.Util.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPoint {
    // une pool de points, qui peut s averer tres pratique
    // si les dimensions de la fenetre changent pour une quelquonc raison
    // grace a ca, tout est automatiquement mise a jour sans passer par des
    // methodes esotheriques toutes moches
    protected static List<AbstractPoint> pointsPool = new ArrayList<>();

    protected float paramX;
    protected float paramY;

    public float x;
    public float y;

    public static void resizePointsInPool() {
        for(AbstractPoint point : pointsPool) {
            point.setPoint(point.paramX, point.paramY);
        }
    }

    protected void addToPool(AbstractPoint point) {
        pointsPool.add(point);
    }

    protected abstract void setPoint(float x, float y);

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
