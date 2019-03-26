package iut.ipi.runnergame;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import iut.ipi.runnergame.Activity.EnterPlayerNameActivity;
import iut.ipi.runnergame.Activity.GameActivity;
import iut.ipi.runnergame.Activity.SettingsActivity;
import iut.ipi.runnergame.Engine.Save.DbLoader;
import iut.ipi.runnergame.Engine.Save.DbSaver;
import iut.ipi.runnergame.Engine.Save.FileLoader;
import iut.ipi.runnergame.Engine.Save.FileSaver;
import iut.ipi.runnergame.Engine.Save.Loader;
import iut.ipi.runnergame.Engine.Save.Saver;
import iut.ipi.runnergame.Engine.Save.User;

public class MainActivity extends AppCompatActivity {
    private Button gameButton;
    private Button optionsButton;
    private Button exitButton;
    private TextView loadingText;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent scoreIntent = new Intent(this, SettingsActivity.class);
        final Intent enterNameIntent = new Intent(this, EnterPlayerNameActivity.class);

        //loadingText = findViewById(R.id.textview_loading);
        //loadingText.setText("");


        gameButton = findViewById(R.id.button_game);
        optionsButton = findViewById(R.id.button_options);
        exitButton = findViewById(R.id.button_exit);

        gameButton.setVisibility(View.VISIBLE);
        optionsButton.setVisibility(View.VISIBLE);
        exitButton.setVisibility(View.VISIBLE);

        gameButton.setText(R.string.main_button_game_text);
        optionsButton.setText(R.string.main_button_options_text);
        exitButton.setText(R.string.main_button_exit_text);

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(enterNameIntent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(scoreIntent);           }
        });
    }
}
