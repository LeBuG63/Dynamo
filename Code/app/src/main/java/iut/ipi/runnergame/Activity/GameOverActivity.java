package iut.ipi.runnergame.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import iut.ipi.runnergame.Game.GameOverDataBundle;
import iut.ipi.runnergame.R;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent parentIntent = getIntent();
        GameOverDataBundle dataBundle = parentIntent.getParcelableExtra("loseDataBundle");

        TextView timer = findViewById(R.id.textview_gameover_time);
        TextView distance = findViewById(R.id.textview_gameover_distance);

        Button mainMenuButton = findViewById(R.id.button_gameover_mainmenu);

        timer.setText(dataBundle.getTimer());
       // distance.setText(dataBundle.getDistance());

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
