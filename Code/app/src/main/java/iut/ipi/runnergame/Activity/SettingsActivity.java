package iut.ipi.runnergame.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import iut.ipi.runnergame.Engine.Save.DbLoader;
import iut.ipi.runnergame.Engine.Save.DbSaver;
import iut.ipi.runnergame.Engine.Save.Loader;
import iut.ipi.runnergame.Engine.Save.Saver;
import iut.ipi.runnergame.Engine.Save.User;
import iut.ipi.runnergame.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Log.d("clem","JE PASSE DANS LE CREATE");

        //User u = new User("test",35);

        Loader s = new DbLoader();
        s.load();

    }
}
