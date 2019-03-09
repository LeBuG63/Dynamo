package iut.ipi.runnergame.Entity.Gameplay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Util.Point.AbstractPoint;

public class PieceManager {
    private List<Piece> pieceList = new ArrayList<>();

    private Context context;

    private Paint paint = new Paint();

    public PieceManager(Context context) {
        this.context = context;
    }

    public void add(PieceType piece, AbstractPoint point) {
        Piece pieceCreated;

        pieceCreated = PieceFactory.create(context, point, piece);

        if(pieceCreated != null)
            pieceList.add(pieceCreated);
    }

    public void drawPiecesOnCanvas(Canvas canvas) {
        for(Piece piece : pieceList) {
            float x = piece.getPosition().x - piece.getOffset().x;
            float y = piece.getPosition().y - piece.getOffset().y;

            canvas.drawBitmap(piece.getSprite(), x, y, paint);
        }
    }

    public void update(Player player) {
        List<Piece> tmp = new ArrayList<>(pieceList);

        for(Piece piece : pieceList) {
            if(player.getCollision().isInCollision(piece.getCollision())) {
                piece.updateBecauseCollision();

                player.addToScore(piece.getValue());

                tmp.remove(piece);
            }
        }

        pieceList = tmp;
    }

    public List<Piece> getPieces() {
        return pieceList;
    }
}
