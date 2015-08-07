package eie.android.crunch;

/**
 * Created by emilyw on 7/31/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

public class SuperAwesomeCardFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    private Activity mActivity;
    private HabitsAdapter mAdapter;
    private FavoritesAdapter mFavoritesAdapter;
    private RecyclerView mHabitRecyclerView;

    public static SuperAwesomeCardFragment newInstance(int position) {
        SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);
//
//        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
//                .getDisplayMetrics());


        View view = inflater.inflate(R.layout.fragment_habit_list, container, false);


        mHabitRecyclerView = (RecyclerView) view.findViewById(R.id.habit_recycler_view);
        mHabitRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mAdapter == null) {
            mAdapter = new HabitsAdapter(getActivity(), (ViewGroup) view);
            mHabitRecyclerView.setAdapter(mAdapter);

        }


        fl.addView(view);
        return fl;

//        TextView v = new TextView(getActivity());
//        params.setMargins(margin, margin, margin, margin);
//        v.setLayoutParams(params);
//        v.setLayoutParams(params);
//        v.setGravity(Gravity.CENTER);
//        v.setBackgroundResource(R.drawable.background_card);
//        v.setText("CARD " + (position + 1));


    }
}

