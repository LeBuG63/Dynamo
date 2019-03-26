package iut.ipi.runnergame.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import iut.ipi.runnergame.Engine.Fragment.PlayerDetailFragment;

public class PlayerDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            PlayerDetailFragment detailJoueur = PlayerDetailFragment.newInstance(getIntent().getIntExtra("idPlayer",0));
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, detailJoueur).commit();
        }
    }
}