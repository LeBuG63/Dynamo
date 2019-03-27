package iut.ipi.runnergame.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.Set;

import iut.ipi.runnergame.Engine.Save.DbLoader;
import iut.ipi.runnergame.Engine.Save.DbSaver;
import iut.ipi.runnergame.Engine.Save.Loader;
import iut.ipi.runnergame.Engine.Save.User;
import iut.ipi.runnergame.R;

public class EnterPlayerNameActivity extends AppCompatActivity {
    private EditText editText;
    private Button buttonSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent gameIntent = new Intent(this,GameActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_player_name);
        editText = (EditText) findViewById(R.id.editText);
        buttonSave = (Button) findViewById(R.id.button);
        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = editText.getText().toString();
                User u = new User(username);
                gameIntent.putExtra("user", (Parcelable) u);
                SharedPreferences.Editor editor = getSharedPreferences("shared_pref_user", 0).edit();
                editor.putString("username",u.getPseudo());
                startActivity(gameIntent);
                }

            });

    }
}
