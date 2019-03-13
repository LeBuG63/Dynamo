package iut.ipi.runnergame.Engine.Graphics.Hud;

import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Hud.Hint.AbstractHint;
import iut.ipi.runnergame.Entity.Drawable;
import iut.ipi.runnergame.Entity.Updatable;

public interface Hud extends Updatable, Drawable {
    AbstractHint getHint();
    AbstractCross getCross();
    AbstractCross getCrossAB();
    RectangleClickable getButton(int index);
}
