package iut.ipi.runnergame.Engine.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class PlayerDetailFragment extends Fragment {


    public static PlayerDetailFragment newInstance(int index) {
        PlayerDetailFragment f = new PlayerDetailFragment();

        Bundle args = new Bundle();
        args.putInt("idPlayer", index);
        f.setArguments(args);

        return f;
    }

    public int getIdCurrentPlayer() {
        return getArguments().getInt("idPlayer", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        scroller.addView(text);
        text.setText(PlayerListFragment.TEST[getIdCurrentPlayer()]);
        return scroller;
    }
}
