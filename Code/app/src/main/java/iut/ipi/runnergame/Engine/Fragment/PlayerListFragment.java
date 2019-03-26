package iut.ipi.runnergame.Engine.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.LinkedList;

import iut.ipi.runnergame.Activity.PlayerDetailActivity;
import iut.ipi.runnergame.Engine.Save.FileLoader;
import iut.ipi.runnergame.Engine.Save.User;
import iut.ipi.runnergame.R;

public class PlayerListFragment extends ListFragment {
    protected boolean listDetailMode = false;
    protected int currentPlayerIndex = 0;
    public static LinkedList<User> scores;
    public static String[] TEST = new String[]{"test"};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FileLoader l = new FileLoader();
        scores = l.load();
        for(int i=1;i<scores.size();i++)
            TEST[i]=scores.get(i).getPseudo();

        if (savedInstanceState != null) {
            currentPlayerIndex = savedInstanceState.getInt("currentPlayerIndex", 0);
        }

        View PlayerDetailFragment = getActivity().findViewById(R.id.joueurDetail);
        listDetailMode = PlayerDetailFragment != null && PlayerDetailFragment.getVisibility() == View.VISIBLE;

        setListAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, TEST) {
        });
        if (listDetailMode) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showPlayer(currentPlayerIndex);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPlayerIndex", currentPlayerIndex);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showPlayer(position);
    }

    void showPlayer(int index) {
        currentPlayerIndex = index;

        if (listDetailMode) {
            getListView().setItemChecked(index, true);
            PlayerDetailFragment playerDetail = (PlayerDetailFragment) getFragmentManager().findFragmentById(R.id.joueurDetail);

            if (playerDetail == null || playerDetail.getIdCurrentPlayer() != index) {
                playerDetail = PlayerDetailFragment.newInstance(index);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.joueurDetail, playerDetail);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }
        else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), PlayerDetailActivity.class);
            intent.putExtra("idPlayer", index);
            startActivity(intent);
        }
    }

}
