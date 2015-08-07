package eie.android.crunch;

import android.app.Activity;
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
 * <p/>
 * NOTE: need to have a default value OR something else
 * currently app will crash if you don't input the right types of values
 */
public class LensQ_1 extends Fragment {

    private EditText badHabitShort;
    private EditText badHabitDesc;
    private EditText badHabitDifficulty;
    private Button nextButton;
    private Activity mActivity;

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

        View v = inflater.inflate(R.layout.fragment_lensq_1, container, false);

        badHabitShort = (EditText) v.findViewById(R.id.bad_habit_short);

        badHabitDesc = (EditText) v.findViewById(R.id.bad_habit_desc);

        badHabitDifficulty = (EditText) v.findViewById(R.id.bad_habit_difficulty);

        // Clicking the next button should save the bad habit
        nextButton = (Button) v.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BadHabit badHabit = ((NewIndividualHabitActivity) mActivity).getBadHabit();

                badHabit.setBadHabitShort(badHabitShort.getText().toString());
                badHabit.setBadHabitDesc(badHabitDesc.getText().toString());
                badHabit.setDifficulty(Integer.parseInt(badHabitDifficulty.getText().toString()));

                badHabit.saveInBackground(new SaveCallback() {

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

                IndividualHabit individualHabit = ((NewIndividualHabitActivity) getActivity())
                        .getIndividualHabit();

                individualHabit.setBadHabit(badHabit);

                individualHabit.saveInBackground(new SaveCallback() {

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

                LensQ_2 fm2 = new LensQ_2();
                fm2.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();
            }
        });
        return v;
    }
}