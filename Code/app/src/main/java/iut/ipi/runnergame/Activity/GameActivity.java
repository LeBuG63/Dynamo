package iut.ipi.runnergame.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import iut.ipi.runnergame.Game.GameMaster;
import iut.ipi.runnergame.Game.GameOverDataBundle;
import iut.ipi.runnergame.Game.GameOverMaster;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Util.Point.AbstractPoint;
import iut.ipi.runnergame.Util.Point.Point;
import iut.ipi.runnergame.Util.WindowDefinitions;

public class GameActivity extends AppCompatActivity {
    public static String strTimer;
    private static GameActivity instance = null;

    private static GameMaster gameManager;

    private SurfaceView surfaceView;
    private TextView textViewTimer;

    private AbstractPoint[] fingerPoints = new Point[10]; // comme les 10 doigts de la main

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getSupportActionBar().hide();

        textViewTimer = findViewById(R.id.textview_timer);
        surfaceView = findViewById(R.id.surface_view);

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
                        fingerPoints[pointerId].x = -1;
                        fingerPoints[pointerId].y = -1;

                        gameManager.setPointsFingerPressed(fingerPoints);
                        break;
                    }
                }
                return true;
            }
        });
        
        Timer timer = new Timer();

        final long timerStarted = System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long millis = System.currentTimeMillis();

                        long millisec = ((millis - timerStarted)) % 1000;
                        long seconds = ((millis - timerStarted) / 1000) % 60;

                        String strSeconds = ((seconds < 10) ? "0" : "") + String.valueOf(seconds);
                        String strMillisec = ((millisec < 100) ? "0" : "") + String.valueOf(millisec);

                        strTimer = strSeconds + "." + strMillisec;

                        textViewTimer.setText(strTimer);
                    }
                });
            }
        }, 0, 10);

        instance = this;
    }

    public static void launchLoseActivity(GameOverDataBundle data) {
        if(instance == null) return;
        instance.finish();
        gameManager.kill();

        Intent loseIntent = new Intent(instance, GameOverActivity.class);

        loseIntent.putExtra("loseDataBundle", data);

        instance.startActivity(loseIntent);
    }
}
