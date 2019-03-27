package iut.ipi.runnergame.Entity.Gameplay.Piece;

import android.content.Context;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.R;

public class PieceFactory {
    // comme ca instanciation impossible
    private PieceFactory() {}

    public static Piece create(Context context, PieceType type, AbstractPoint point) {
        switch (type) {
            case LOW:
                return new Piece(context, point, R.drawable.sprite_piece_low_1, type);
            case NORMAL:
                return new Piece(context, point, R.drawable.sprite_piece_normal_1, type);
        }

        return null;
    }
}
