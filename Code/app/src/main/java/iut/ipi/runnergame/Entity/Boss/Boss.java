package iut.ipi.runnergame.Entity.Boss;

import iut.ipi.runnergame.Entity.Drawable;
import iut.ipi.runnergame.Entity.Translatable;
import iut.ipi.runnergame.Entity.Updatable;

public interface Boss extends Drawable, Updatable, Translatable {
    void setAppeared(boolean b);
}
