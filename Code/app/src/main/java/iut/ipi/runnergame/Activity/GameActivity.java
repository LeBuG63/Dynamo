package iut.ipi.runnergame.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import iut.ipi.runnergame.Entity.Shadow.ShadowManager;
import iut.ipi.runnergame.Game.GameManager;
import iut.ipi.runnergame.Hud.Input.BaseArrowClickable;
import iut.ipi.runnergame.Sensor.Accelerometer;
import iut.ipi.runnergame.Util.Point.AbstractPoint;
import iut.ipi.runnergame.Util.Point.PointCell;

import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

import iut.ipi.runnergame.Animation.SpriteSheetAnimation.BaseSpriteSheetAnimation;
import iut.ipi.runnergame.Entity.Plateform.PlateformManager;
import iut.ipi.runnergame.Entity.Plateform.PlateformType;
import iut.ipi.runnergame.Entity.Plateform.TypesPlateform.SimplePlateform;
import iut.ipi.runnergame.Entity.Player.Player;
import iut.ipi.runnergame.Hud.Cross;
import iut.ipi.runnergame.Hud.Input.BaseCrossClickable;
import iut.ipi.runnergame.Physics.PhysicsManager;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Spritesheet.Spritesheet;
import iut.ipi.runnergame.Util.Point.PointScaled;
import iut.ipi.runnergame.Util.WindowDefinitions;

public class GameActivity extends SurfaceView {
    private GameManager gameManager;

    public GameActivity(Context context) {
        super(context);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        gameManager = new GameManager(context, getHolder());

        gameManager.start();

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = -1;
                float y = -1;

                getRootView().performClick();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = event.getX() / WindowDefinitions.ratioWidth;
                    y = event.getY() / WindowDefinitions.ratioHeight;

                    gameManager.setPointFingerPressed(x, y);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    x = -1;
                    y = -1;

                    gameManager.setPointFingerPressed(x, y);
                }


                return true;
            }
        });
    }

}