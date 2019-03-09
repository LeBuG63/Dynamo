package iut.ipi.runnergame.Game.Level.Loader;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import iut.ipi.runnergame.Engine.Graphics.Point.PointAdjusted;
import iut.ipi.runnergame.Entity.Gameplay.Piece.Piece;
import iut.ipi.runnergame.Entity.Gameplay.Piece.PieceFactory;
import iut.ipi.runnergame.Entity.Gameplay.Piece.PieceType;
import iut.ipi.runnergame.Entity.Plateform.AbstractPlateform;
import iut.ipi.runnergame.Entity.Plateform.PlateformFactory;
import iut.ipi.runnergame.Entity.Plateform.PlateformType;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Game.Level.Level;

public class LevelLoaderText implements LevelLoader {
    private final String OBJECT_PLATFORM = "PLATEFORMS";
    private final String OBJECT_PIECES = "PIECES";
    private final String OBJECT_UNKNOWN = "";

    private String actualObject = OBJECT_UNKNOWN;

    private Context context;
    private Player player;
    private int fileId;

    public LevelLoaderText(Context context, Player player, int fileId) {
        this.context = context;
        this.player = player;
        this.fileId = fileId;
    }

    @Override
    public Level load() {
        Level level = new Level(context, player);

        context = context;

        try {
            InputStream inputStream = context.getResources().openRawResource(fileId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                if(verifChangeObject(line))
                    continue;

                switch (actualObject) {
                    case OBJECT_PLATFORM:
                        level.addPlateform(readPlateformInput(line));
                        break;
                    case OBJECT_PIECES:
                        level.addPiece(readPieceInput(line));
                        break;
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return level;
        }

        return level;
    }

    private AbstractPlateform readPlateformInput(String line) throws IOException {
        String split[] = line.split("\\s+");

        String type = split[0];
        float x = Float.parseFloat(split[1]);
        float y = Float.parseFloat(split[2]);
        int length = Integer.parseInt(split[3]);

        return PlateformFactory.create(context, PlateformType.valueOf(type), new PointAdjusted(x, y), length);
    }

    private Piece readPieceInput(String line) throws IOException {
        String split[] = line.split("\\s+");

        String type = split[0];
        float x = Float.parseFloat(split[1]);
        float y = Float.parseFloat(split[2]);

        return PieceFactory.create(context, PieceType.valueOf(type), new PointAdjusted(x, y));
    }

    private boolean verifChangeObject(String line) {
        if(line.compareTo(OBJECT_PLATFORM) == 0) {
            actualObject = OBJECT_PLATFORM;
            return true;
        }
        else if(line.compareTo(OBJECT_PIECES) == 0) {
            actualObject = OBJECT_PIECES;
            return true;
        }

        return false;
    }
}
