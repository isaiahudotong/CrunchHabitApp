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
 */
public class Routine_Cons extends Fragment {

    private EditText cons_desc;
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

        View v = inflater.inflate(R.layout.fragment_consequence, container, false);

        cons_desc = (EditText) v.findViewById(R.id.cons_desc);


        // Clicking the next button should save the bad habit
        nextButton = (Button) v.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Consequence consequence = ((NewIndividualHabitActivity) mActivity)
                        .getConsequence();

                IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                        .getIndividualHabit();

                consequence.setConsequence(cons_desc.getText().toString());

                habit.setConsequence(consequence);

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
                Routine_Obstacle fm2 = new Routine_Obstacle();
                fm2.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();
            }
        });


        return v;
    }
}