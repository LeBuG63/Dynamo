package iut.ipi.runnergame.Game.Level;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Entity.Gameplay.Piece.Piece;
import iut.ipi.runnergame.Entity.Gameplay.Piece.PieceManager;
import iut.ipi.runnergame.Entity.Gameplay.Piece.PieceType;
import iut.ipi.runnergame.Entity.Plateform.AbstractPlateform;
import iut.ipi.runnergame.Entity.Plateform.PlateformManager;
import iut.ipi.runnergame.Entity.Plateform.PlateformType;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Entity.Shadow.ShadowManager;

public class Level {
    private ShadowManager shadowManager;
    private PlateformManager plateformManager;
    private PieceManager pieceManager;

    private Background background;

    public Level(Context context, Player player) {
        plateformManager = new PlateformManager(context);
        shadowManager = new ShadowManager(context, player, WindowUtil.convertPixelsToDp(5), WindowUtil.convertPixelsToDp(15), Color.WHITE);
        pieceManager = new PieceManager(context);

        background = new Background(context);
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

    public Background getBackground() {
        return background;
    }
}
