package eie.android.crunch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by emilyw on 7/13/15.
 */
public class CrunchCentralExerciseFragment extends Fragment {

    private Activity mActivity;
    private HabitsExerciseAdapter mAdapter;
    private FavoritesAdapter mFavoritesAdapter;
    private RecyclerView mHabitRecyclerView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_habit_list, container, false);


        mHabitRecyclerView = (RecyclerView) view.findViewById(R.id.habit_recycler_view);
        mHabitRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        if (mAdapter == null) {
            mAdapter = new HabitsExerciseAdapter(mActivity, (ViewGroup) view);
            mHabitRecyclerView.setAdapter(mAdapter);
        }



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

