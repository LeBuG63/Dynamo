package iut.ipi.runnergame.Engine.Graphics.Hud.Hint;

import iut.ipi.runnergame.Engine.Graphics.Animation.Animable;
import iut.ipi.runnergame.Engine.Graphics.Animation.AnimationManager;
import iut.ipi.runnergame.Entity.AbstractEntity;
import iut.ipi.runnergame.Entity.Drawable;

public interface Hint extends Drawable, Animable {
    void show();
    void hide();
}
