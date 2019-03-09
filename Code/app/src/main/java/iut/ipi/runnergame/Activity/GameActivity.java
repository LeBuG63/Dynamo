package iut.ipi.runnergame.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import iut.ipi.runnergame.Engine.Graphics.Point.AbstractPoint;
import iut.ipi.runnergame.Engine.Graphics.Point.Point;
import iut.ipi.runnergame.Engine.WindowDefinitions;
import iut.ipi.runnergame.Engine.WindowUtil;
import iut.ipi.runnergame.Game.GameMaster;
import iut.ipi.runnergame.Game.GameOverDataBundle;
import iut.ipi.runnergame.R;

public class GameActivity extends AppCompatActivity {
    public static String strTimer;
    private static GameActivity instance = null;

    private static GameMaster gameManager;

    private SurfaceView surfaceView;
    private TextView textViewTimer;

    private AbstractPoint[] fingerPoints = new Point[10]; // comme les 10 doigts de la main

    private Timer timerUpdateScore = new Timer();

    private boolean instanceOnPause = false;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getSupportActionBar().hide();

        textViewTimer = findViewById(R.id.textview_timer);
        surfaceView = findViewById(R.id.surface_view);

        WindowDefinitions.HEIGHT = getWindowManager().getDefaultDisplay().getHeight();
        WindowDefinitions.WIDTH = getWindowManager().getDefaultDisplay().getWidth();

        gameManager = new GameMaster(getApplicationContext(), surfaceView.getHolder());
        gameManager.start();

        for(int i = 0; i < fingerPoints.length; ++i) {
            fingerPoints[i] = new Point();
        }

        surfaceView.setOnTouchListener(new  View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                surfaceView.getRootView().performClick();

                int maskedAction = event.getActionMasked();
                int pointerId = event.getPointerId(event.getActionIndex());

                switch (maskedAction) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_MOVE: {
                        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();

                        try {
                            event.getPointerCoords(pointerId, pointerCoords);

                            fingerPoints[pointerId].x = pointerCoords.x;
                            fingerPoints[pointerId].y = pointerCoords.y;
                        } catch (Exception e) {}
                        gameManager.setPointsFingerPressed(fingerPoints);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        // permet d eviter une collision accidentel
                        fingerPoints[pointerId].x = -1;
                        fingerPoints[pointerId].y = -1;

                        gameManager.setPointsFingerPressed(fingerPoints);
                        break;
                    }
                }
                return true;
            }
        });

        final long timerStarted = System.currentTimeMillis();
        timerUpdateScore.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!instanceOnPause) {
                            long millis = System.currentTimeMillis();

                            long millisec = ((millis - timerStarted)) % 1000;
                            long seconds = ((millis - timerStarted) / 1000);

                            String strSeconds = ((seconds < 10) ? "0" : "") + String.valueOf(seconds);
                            String strMillisec = ((millisec < 100) ? "0" : "") + String.valueOf(millisec);

                            strTimer = strSeconds + "." + strMillisec;

                            textViewTimer.setText(strTimer);
                        }
                    }
                });
            }
        }, 0, 10);

        gameManager.updatePoolPoints();

        instance = this;
    }

    @Override
    public void onPause() {
        super.onPause();
        instanceOnPause = true;
        gameManager.pauseUpdate();
    }

    @Override
    public void onResume() {
        super.onResume();
        instanceOnPause = false;
        gameManager.resumeUpdate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

            WindowDefinitions.HEIGHT = WindowUtil.convertDpToPixel(newConfig.screenHeightDp);
            WindowDefinitions.WIDTH = WindowUtil.convertDpToPixel(newConfig.screenWidthDp);
            gameManager.updatePoolPoints();

    }

    public static void launchLoseActivity(GameOverDataBundle data) {
        if(instance == null) return;

        // si on change d activite, alors celle ci doit finir et le thread du gamemanager doit etre tue pour eviter de surcharger le cpu
        gameManager.stopUpdate();
        gameManager.kill();
        instance.finish();

        Intent loseIntent = new Intent(instance, GameOverActivity.class);

        loseIntent.putExtra("loseDataBundle", data);

        instance.startActivity(loseIntent);
    }
}
