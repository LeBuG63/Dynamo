package iut.ipi.runnergame.Engine.Graphics.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPoint {
    // une pool de points, qui peut s averer tres pratique
    // si les dimensions de la fenetre changent pour une quelquonque raison
    // grace a ca, tout est automatiquement mise a jour sans passer par des
    // methodes esotheriques toutes moches
    protected static List<AbstractPoint> pointsPool = new ArrayList<>();

    protected float paramX;
    protected float paramY;

    public float x;
    public float y;

    /**
     * permet de recalculer automatiquement la position en foncion de la taille de l ecran
     */
    public static synchronized  void resizePointsInPool() {
        if(pointsPool.isEmpty()) return;

        try {
            for (int i = 0; i < pointsPool.size(); i++) {
                AbstractPoint point = pointsPool.get(i);

                if (point != null)
                    point.setPoint(point.paramX, point.paramY);
            }
        } catch (IndexOutOfBoundsException ignore) {}
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * supprime tous les points du pool
     */
    public static void clearPoolPoints() {
        pointsPool.clear();
    }

    /**
     * ajoute un point au pool
     * @param point
     */
    protected void addToPool(AbstractPoint point) {
        pointsPool.add(point);
    }

    protected abstract void setPoint(float x, float y);

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
