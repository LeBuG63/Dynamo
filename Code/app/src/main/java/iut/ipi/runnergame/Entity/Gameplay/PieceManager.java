package iut.ipi.runnergame.Entity.Gameplay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Physics.CollisionManager;

public class PieceManager {
    private List<Piece> pieceList = new ArrayList<>();

    private Context context;

    private Paint paint = new Paint();

    public PieceManager(Context context) {
        this.context = context;
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

    public void update(Player player) {
        for(Piece piece : pieceList) {
            if(player.getCollision().isInCollision(piece.getCollision())) {
                piece.updateBecauseCollision();

                pieceList.remove(piece);
            }
        }
    }

    public List<Piece> getPieces() {
        return pieceList;
    }
}
