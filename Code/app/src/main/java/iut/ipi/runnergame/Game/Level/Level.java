package iut.ipi.runnergame.Game.Level;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Entity.Gameplay.Piece.Piece;
import iut.ipi.runnergame.Entity.Gameplay.Piece.PieceManager;
import iut.ipi.runnergame.Entity.Gameplay.Piece.PieceType;
import iut.ipi.runnergame.Entity.Plateform.AbstractPlateform;
import iut.ipi.runnergame.Entity.Plateform.PlateformManager;
import iut.ipi.runnergame.Entity.Plateform.PlateformType;
import iut.ipi.runnergame.Entity.Player.AbstractPlayer;
import iut.ipi.runnergame.Entity.Shadow.ShadowManager;
import iut.ipi.runnergame.Game.Level.Background.GameBackground;

public class Level {
    private ShadowManager shadowManager;
    private PlateformManager plateformManager;
    private PieceManager pieceManager;

    private GameBackground background;

    public Level(Context context, AbstractPlayer player) {
        plateformManager = new PlateformManager(context);
        shadowManager = new ShadowManager(context, player, WindowUtil.convertPixelsToDp(context, 80) * WindowDefinitions.SCREEN_ADJUST, WindowUtil.convertPixelsToDp(context, 20) * WindowDefinitions.SCREEN_ADJUST, Color.WHITE);
        pieceManager = new PieceManager(context);

        background = new GameBackground(context);
    }

    public void destroy() {
        BaseSpriteSheetAnimation.destroyTimer();
    }

    public void addPlateform(PlateformType type, AbstractPoint point, int length) {
        plateformManager.add(type, point, length);
    }

    public void addPlateform(AbstractPlateform plateform) {
        plateformManager.add(plateform);
    }

    public void addPiece(PieceType type, AbstractPoint point) {
        pieceManager.add(type, point);
    }

    public void addPiece(Piece piece) {
        pieceManager.add(piece);
    }

    public void drawOnCanvas(Canvas canvas) {
        background.drawOnCanvas(canvas);
        plateformManager.drawPlateformOnCanvas(canvas);
        pieceManager.drawPiecesOnCanvas(canvas);
    }

    public void drawShadowOnCanvas(Canvas canvas) {
        shadowManager.drawOnCanvas(canvas);
    }

    public List<AbstractPlateform> getPlateforms() {
        return plateformManager.getPlateforms();
    }

    public List<Piece> getPieces() {
        return pieceManager.getPieces();
    }

    public int getLength() {
        return plateformManager.getLevelLength();
    }

    public PieceManager getPieceManager() {
        return pieceManager;
    }

    public ShadowManager getShadowManager() {
        return shadowManager;
    }

    public GameBackground getBackground() {
        return background;
    }
}
