package iut.ipi.runnergame.Game.Level;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import iut.ipi.runnergame.Engine.TranslateUtil;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Game.Level.Loader.LevelLoader;

public class LevelCreator {
    private final int PARALLAX_VALUE = 4; // plus c est haut moins le background bougera

    private Level level;
    private Player refToPlayer;

    private TranslateUtil translateUtil = new TranslateUtil();

    public LevelCreator(Context context, Player player, LevelLoader levelLoader) {
        level = levelLoader.load();
        refToPlayer = player;
    }

    public void updateLevel(final float dt) {
        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            exec.submit(new Runnable() {
                @Override
                public void run() {
                    translateUtil.translateListObject(level.getPlateforms(), refToPlayer.getPosition().x - Player.DEFAULT_POS.x, 0);
                    translateUtil.translateListObject(level.getPieces(), refToPlayer.getPosition().x - Player.DEFAULT_POS.x, 0);
                    translateUtil.translateObject(level.getBackground(), (refToPlayer.getPosition().x - Player.DEFAULT_POS.x) / PARALLAX_VALUE, 0);
                }
            });

            exec.submit(new Runnable() {
                @Override
                public void run() {
                    level.getPieceManager().update(refToPlayer);
                    level.getShadowManager().update(dt);
                }
            });
        } finally {
            exec.shutdown();
        }

        while (!exec.isTerminated()) {}
    }

    public Level getLevel() {
        return level;
    }
}
