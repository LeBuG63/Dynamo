package iut.ipi.runnergame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import iut.ipi.runnergame.Activity.LoadingActivity;

public class MainActivity extends AppCompatActivity {
    private Button gameButton;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameButton = findViewById(R.id.button_game);
        exitButton = findViewById(R.id.button_exit);

        final Intent loadingIntent = new Intent(this, LoadingActivity.class);

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loadingIntent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}
