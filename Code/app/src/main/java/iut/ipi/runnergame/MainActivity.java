package iut.ipi.runnergame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import iut.ipi.runnergame.Activity.GameActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button gameButton = findViewById(R.id.button_game);
        Button optionsButton = findViewById(R.id.button_options);
        Button exitButton = findViewById(R.id.button_exit);

        gameButton.setText(R.string.main_button_game_text);
        optionsButton.setText(R.string.main_button_options_text);
        exitButton.setText(R.string.main_button_exit_text);

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity gameActivity = new GameActivity(getBaseContext());
                Thread gameThread = new Thread(gameActivity);

                gameThread.start();

                setContentView(gameActivity);
            }
        });
    }
}
