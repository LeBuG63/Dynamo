package iut.ipi.runnergame.Entity.Gameplay.Piece;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Entity.Player.AbstractPlayer;

public class PieceManager {
    private List<Piece> pieceList = new ArrayList<>();

    private Context context;

    private Paint paint = new Paint();

    public PieceManager(Context context) {
        this.context = context;
    }

    public void add(PieceType piece, AbstractPoint point) {
        Piece pieceCreated;

        pieceCreated = PieceFactory.create(context, piece, point);

        if(pieceCreated != null)
            pieceList.add(pieceCreated);
    }

    public void add(Piece piece) {
        pieceList.add(piece);
    }

    public void drawPiecesOnCanvas(Canvas canvas) {
        for(Piece piece : pieceList) {
            float x = piece.getPosition().x - piece.getOffset().x;
            float y = piece.getPosition().y - piece.getOffset().y;

            canvas.drawBitmap(piece.getSprite(), x, y, paint);
        }
    }

    public void update(AbstractPlayer player) {
        for(int i = 0; i < pieceList.size(); ++i) {
            Piece piece = pieceList.get(i);

            if(player.getCollision().isInCollision(piece.getCollision())) {
                piece.updateBecauseCollision();

                player.addToScore(piece.getValue());

                pieceList.remove(i);
            }
        }
    }

    public List<Piece> getPieces() {
        return pieceList;
    }
}
