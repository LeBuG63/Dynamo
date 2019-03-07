package iut.ipi.runnergame.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import iut.ipi.runnergame.Game.GameOverDataBundle;
import iut.ipi.runnergame.Game.GameOverMaster;
import iut.ipi.runnergame.R;
import iut.ipi.runnergame.Util.Util;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gameover);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();

        Intent parentIntent = getIntent();
        GameOverDataBundle dataBundle = parentIntent.getParcelableExtra("loseDataBundle");

        SurfaceView surfaceView = findViewById(R.id.surface_view_gameover);

        final GameOverMaster gameOverMaster = new GameOverMaster(getApplicationContext(), surfaceView.getHolder(), dataBundle.getDistance(), dataBundle.getLevelLength(), 10.0f);
        gameOverMaster.start();

        TextView timer = findViewById(R.id.textview_gameover_time);
        TextView distance = findViewById(R.id.textview_gameover_distance_player);
        TextView score = findViewById(R.id.textview_gameover_score);

        Button mainMenuButton = findViewById(R.id.button_gameover_mainmenu);

        timer.setText(dataBundle.getTimer());
        score.setText(Integer.toString(dataBundle.getScore()));

        float percentage = (float)dataBundle.getDistance() / dataBundle.getLevelLength() * 100.0f;

        if(percentage < 0)
            percentage = 0.0f;
        else if(percentage > 100)
            percentage = 100.0f;

        distance.setText(String.format("%s%%", Util.roundFloatNDigits(percentage, 2)));

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                gameOverMaster.kill();
            }
        });
    }
}
