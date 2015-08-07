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
public class LensQ_2 extends Fragment {

    private EditText badWhatTriggerShort;
    private EditText badWhatTriggerDesc;
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

        View v = inflater.inflate(R.layout.fragment_lensq_2, container, false);

        badWhatTriggerShort = (EditText) v.findViewById(R.id.bad_what_trigger_short);

        badWhatTriggerDesc = (EditText) v.findViewById(R.id.bad_what_trigger_desc);

        // Clicking the next button should save the bad habit
        nextButton = (Button) v.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                        .getIndividualHabit();
                // When the user clicks "Next," upload the habit to parse

                habit.setBadWhatTriggerShort(badWhatTriggerShort.getText().toString());
                habit.setBadWhatTriggerDesc(badWhatTriggerDesc.getText().toString());

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
                LensQ_3 fm2 = new LensQ_3();
                fm2.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();
            }
        });

        return v;
    }
}