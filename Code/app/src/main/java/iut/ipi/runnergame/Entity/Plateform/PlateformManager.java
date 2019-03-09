package iut.ipi.runnergame.Entity.Plateform;

import android.content.Context;
import android.graphics.Canvas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;

public class PlateformManager {
    private List<AbstractPlateform> plateforms = new ArrayList<>();

    private Context context;

    public PlateformManager(Context context) {
        this.context = context;
    }

    public void translate(float offsetx, float offsety) {
        for (AbstractPlateform plateform : plateforms) {
            plateform.setOffset(new Point(offsetx, offsety));
        }
    }

    public void add(PlateformType type, AbstractPoint position, int lenght) {
        try {
            plateforms.add(PlateformFactory.create(context, type, position, lenght));
        } catch (IOException e) {}
    }

    public void drawPlateformOnCanvas(Canvas canvas) {
        for(AbstractPlateform plateform : plateforms) {
            plateform.drawOnCanvas(canvas);
        }
    }

    public int getLevelLength() {
        int maxX = 0;

        for(AbstractPlateform plateform : getPlateforms()) {
            int rightSide = (int)plateform.getRectangle().right;

            if(rightSide > maxX) {
               maxX = rightSide;
           }
        }

        return maxX;
    }

    public List<AbstractPlateform> getPlateforms() {
        return plateforms;
    }
}
