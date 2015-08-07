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
public class ChallengeFriendSelectionIntro extends Fragment {
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


    public ChallengeFriendSelectionIntro() {

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
        View v = lf.inflate(R.layout.fragment_challenge_first, container, false);



        return v;
    }

    public void setNameText(String s) {
        nameText.setText(s+" will");
    }


    public TextView getNameText() {
        return nameText;
    }

    public void setHabitName(String s) {
        habitName.setText(s.toUpperCase());
    }
}
