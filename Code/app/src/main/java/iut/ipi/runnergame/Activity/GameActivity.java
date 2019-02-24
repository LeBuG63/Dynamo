package iut.ipi.runnergame.Activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import iut.ipi.runnergame.Game.GameManager;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Util.WindowDefinitions;

public class GameActivity extends AppCompatActivity {
    private GameManager gameManager;

    private SurfaceView surfaceView;
    private TextView textViewTimer;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getSupportActionBar().hide();

       // textViewTimer = findViewById(R.id.textview_timer);
        surfaceView = findViewById(R.id.surface_view);

        surfaceView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        gameManager = new GameManager(getApplicationContext(), surfaceView.getHolder());
        gameManager.start();

        surfaceView.setOnTouchListener(new  View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = -1;
                float y = -1;

                surfaceView.getRootView().performClick();

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
/*
        Timer timer = new Timer();

        final long timerStarted = System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long millis = System.currentTimeMillis();

                        long minutes = ((millis - timerStarted) / 60000);
                        long seconds = ((millis - timerStarted) / 1000) % 60;

                        textViewTimer.setText(String.valueOf(minutes) + " " + String.valueOf(seconds));
                    }
                });
            }
        }, 0, 100); */
    }
}
