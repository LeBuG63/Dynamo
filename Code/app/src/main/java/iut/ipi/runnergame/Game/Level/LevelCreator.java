package iut.ipi.runnergame.Game.Level;

import android.content.Context;

import iut.ipi.runnergame.Engine.TranslateUtil;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Game.Level.Loader.LevelLoader;

public class LevelCreator {
    private Level level;
    private Player refToPlayer;

    private TranslateUtil translateUtil = new TranslateUtil();

    public LevelCreator(Context context, Player player, LevelLoader levelLoader) {
        level = levelLoader.load();
        refToPlayer = player;
    }

    public void updateLevel(float dt) {
        translateUtil.translateListObject(level.getPlateforms(), refToPlayer.getPosition().x - Player.DEFAULT_POS.x, 0);
        translateUtil.translateListObject(level.getPieces(), refToPlayer.getPosition().x - Player.DEFAULT_POS.x, 0);
        translateUtil.translateObject(level.getBackground(), refToPlayer.getPosition().x - Player.DEFAULT_POS.x, 0);

        level.getPieceManager().update(refToPlayer);
        level.getShadowManager().update(dt);
    }

    public Level getLevel() {
        return level;
    }
}
