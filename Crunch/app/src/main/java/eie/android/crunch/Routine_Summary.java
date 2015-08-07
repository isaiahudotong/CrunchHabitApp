package eie.android.crunch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class Routine_Summary extends Fragment {

    public static Fragment newInstance(Context context) {
        Routine_Summary f = new Routine_Summary();

        return f;
    }

    private EditText days;
    private Activity mActivity;
private Integer integer;
    private Button finishButton;

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

        View v = inflater.inflate(R.layout.fragment_summary, container, false);

        final EditText days = (EditText) v.findViewById(R.id.days);





        // Clicking the next button should save the bad habit
        finishButton = (Button) v.findViewById(R.id.finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                 if (days.getEditableText().toString().equals("") || days.getEditableText() == null)  {

                    Toast.makeText(getActivity(), "Oops! Please enter a numeric value", Toast.LENGTH_SHORT).show();

            }
                else {


                    IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                            .getIndividualHabit();

                    // number of days user wants to do the challenge for
                    habit.setDays(Integer.parseInt(days.getText().toString()));

                    // setting the progress as false since the user hasn't finished Day 1
                    for (int i = 0; i < habit.getDays(); i++) {
                        habit.setProgress(false);
                    }

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

                    mActivity.setResult(Activity.RESULT_OK);
                    mActivity.finish();
                    Intent i = new Intent(mActivity, EachHabitPage.class);
                    startActivity(i);
                }
            }
        });


        return v;
    }
}