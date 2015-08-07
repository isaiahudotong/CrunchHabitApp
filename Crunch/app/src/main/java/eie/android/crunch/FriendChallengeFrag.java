package eie.android.crunch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by elliekang on 7/30/15.
 */
public class FriendChallengeFrag extends Fragment {
    private Activity mActivity;
    private static TextView nameText;
    private static TextView habitName;
    private FragmentTransaction fragmentTransaction;
    private SeekBar mSeekBar;

    private Handler handler =new Handler();
    private Handler seekBarHandler;
    private int progress = 0;
    private int max = 0;
    private FriendProgress mFriendProgress;


    public FriendChallengeFrag() {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View v = lf.inflate(R.layout.fragment_challenge_friend, container, false);
        nameText = (TextView) v.findViewById(R.id.user_name_text_view2);
        habitName = (TextView) v.findViewById(R.id.habit_name_text_view2);
        habitName.setText("Habit Name");

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        mFriendProgress = new FriendProgress();
        mFriendProgress.onAttach(getActivity());
        fragmentTransaction
                .replace(R.id.progress_bar2, mFriendProgress, "progress")
                .commit();


        ParseUser currentUser = ParseUser.getCurrentUser();
        String currentUsername = currentUser.getUsername();
        ParseObject habit = (ParseObject) currentUser.get("current_habit");
        if (habit != null) {
            try {
                habit.fetchIfNeeded();

                ParseObject goodHabit = (ParseObject) habit.get("good_habit");
                if (goodHabit != null) {
                    try {
                        goodHabit.fetchIfNeeded();
                        habitName.setText((String) goodHabit.get("good_habit_short"));

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
        nameText.setText(s+" will");
    }


    public TextView getNameText() {
        return nameText;
    }

    public void setHabitName(String s) {
        habitName.setText(s);
    }
}
