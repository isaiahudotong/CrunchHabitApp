package eie.android.crunch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class CurrentUserChallengeFragment extends Fragment {

    private Activity mActivity;
    private static TextView nameText;
    private TextView habitName;
    private FragmentTransaction fragmentTransaction;
    private CurrentUserProgress mCurrentUserProgress;

    public CurrentUserChallengeFragment() {

    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View v = lf.inflate(R.layout.fragment_challenge, container, false);
        nameText = (TextView) v.findViewById(R.id.user_name_text_view);
        nameText.setText("Username");
        habitName = (TextView) v.findViewById(R.id.habit_name_text_view);
        habitName.setText("Habit Name");
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        mCurrentUserProgress = new CurrentUserProgress();
        mCurrentUserProgress.onAttach(getActivity());
        fragmentTransaction
                .replace(R.id.progress_bar, mCurrentUserProgress, "progress")
                .commit();

        ParseUser currentUser = ParseUser.getCurrentUser();
        String currentUsername = currentUser.getUsername();
        String currentname = (String) currentUser.get("name");

        setNameText(currentname);

        ParseObject habit = (ParseObject) currentUser.get("current_habit");
        if (habit != null) {
            try {
                habit.fetchIfNeeded();

                ParseObject goodHabit = (ParseObject) habit.get("good_habit");
                if (goodHabit != null) {
                    try {
                        goodHabit.fetchIfNeeded();
                        setHabitName((String) goodHabit.get("good_habit_short"));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        return v;
    }

    public void setNameText(String s) {
        nameText.setText(s + " will");
    }


    public TextView getNameText() {
        return nameText;
    }

    public void setHabitName(String s) {
        habitName.setText(s);
    }

}