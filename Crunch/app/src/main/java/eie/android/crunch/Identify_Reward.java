package eie.android.crunch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Created by emilyw on 7/13/15.
 */

// will later implement drag and sort list view

public class Identify_Reward extends Fragment {

    public static Fragment newInstance(Context context) {
        Identify_Reward f = new Identify_Reward();

        return f;
    }
    private EditText reward_desc;
    private Activity mActivity;

    private Button nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reason, container, false);
        reward_desc = (EditText) v.findViewById(R.id.reward_desc);


        // Clicking the next button should save the bad habit
        nextButton = (Button) v.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (reward_desc.getEditableText() == null || reward_desc.getEditableText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Oops! You didn't enter your habit information", Toast.LENGTH_SHORT).show();

                } else {


                    Reward reward = ((NewIndividualHabitActivity) mActivity)
                            .getReward();

                    IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                            .getIndividualHabit();

                    reward.setReward(reward_desc.getText().toString());

                    reward.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e != null) {

                                // The save failed.
                                Toast.makeText(
                                        mActivity.getApplicationContext(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    habit.setReward(reward);

                    habit.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e != null) {

                                // The save failed.
                                Toast.makeText(
                                        mActivity.getApplicationContext(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    Identify_Cues fm2 = new Identify_Cues();
                    fm2.onAttach(mActivity);
                    fragmentTransaction
                            .replace(R.id.fragment_container, fm2)
                            .commit();
                }
            }
        });


        return v;
    }
}