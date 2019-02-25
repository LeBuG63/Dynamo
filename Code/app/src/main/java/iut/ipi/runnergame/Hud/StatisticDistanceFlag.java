package iut.ipi.runnergame.Hud;

import android.content.Context;
import android.graphics.Canvas;

import java.io.IOException;

import iut.ipi.runnergame.Entity.Drawable;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Util.Point.AbstractPoint;

public class StatisticDistanceFlag implements Drawable {
    private Spritesheet spritesheet;

    public StatisticDistanceFlag(Context context, int size, AbstractPoint position) {
        try {
            spritesheet = new Spritesheet(context, R.drawable.sprite_flags_1, 1, 4, 3);
        } catch (IOException e) { }
    }

    @Override
    public void drawOnCanvas(Canvas canvas) {

    }
}
