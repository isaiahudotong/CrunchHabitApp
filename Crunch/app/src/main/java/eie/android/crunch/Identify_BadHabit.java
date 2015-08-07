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
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by emilyw on 7/13/15.
 * <p/>
 * NOTE: need to have a default value OR something else
 * currently app will crash if you don't input the right types of values
 */
public class Identify_BadHabit extends Fragment {

    public static Fragment newInstance(Context context) {
        Identify_BadHabit f = new Identify_BadHabit();

        return f;
    }

    private EditText badHabitShort;
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

        View v = inflater.inflate(R.layout.fragment_bad_routine, container, false);
        badHabitShort = (EditText) v.findViewById(R.id.bad_habit_short);


        // Clicking the next button should save the bad habit
        nextButton = (Button) v.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (badHabitShort.getEditableText() == null || badHabitShort.getEditableText().toString().equals("") )  {

                    Toast.makeText(getActivity(), "Oops! You didn't enter your habit information", Toast.LENGTH_SHORT).show();

                }

                else {

                    BadHabit badHabit = ((NewIndividualHabitActivity) mActivity).getBadHabit();

                    badHabit.setBadHabitShort(badHabitShort.getText().toString());

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

                    final ParseUser user = ParseUser.getCurrentUser();

                    final IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                            .getIndividualHabit();

                    habit.setBadHabit(badHabit);
                    habit.setProgressInt(0);

                    habit.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e != null) {

                                // The save failed.
                                Toast.makeText(
                                        mActivity.getApplicationContext(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                ParseRelation<ParseObject> relation = user.getRelation("habits");
                                relation.add(habit);
                                user.put("current_habit", habit);
                                user.saveInBackground(new SaveCallback() {

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

                            }
                        }
                    });


                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    Identify_Category fm2 = new Identify_Category();
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