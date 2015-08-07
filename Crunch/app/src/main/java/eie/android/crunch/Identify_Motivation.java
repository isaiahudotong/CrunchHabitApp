package eie.android.crunch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by elliekang on 7/27/15.
 */
public class Identify_Motivation extends Fragment {

    private EditText motivation1;
    private EditText motivation2;
    private EditText motivation3;
    private Activity mActivity;
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

        View v = inflater.inflate(R.layout.fragment_motivation, container, false);

        motivation1 = (EditText) v.findViewById(R.id.motivation1);
        motivation2 = (EditText) v.findViewById(R.id.motivation2);
        motivation3 = (EditText) v.findViewById(R.id.motivation3);

        // Clicking the next button should save the bad habit
        finishButton = (Button) v.findViewById(R.id.next_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((motivation1.getEditableText() == null || motivation1.getEditableText().toString().equals("")) &&
                        (motivation2.getEditableText() == null || motivation2.getEditableText().toString().equals("")) && (motivation3.getEditableText() == null ||
                        motivation3.getEditableText().toString().equals(""))) {
                    Toast.makeText(getActivity(), "Opps! Please enter at least 1 motivation", Toast.LENGTH_SHORT).show();


                } else {
                    IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                            .getIndividualHabit();

//
//                    final ArrayList<String> mot = new ArrayList();

                    if (motivation1 != null) {
                        habit.put("mot1", (motivation1.getText().toString()));
                    }

                    if (motivation2 != null) {
                        habit.put("mot2", (motivation2.getText().toString()));
                    }

                    if (motivation3 != null) {
                        habit.put("mot3", (motivation1.getText().toString()));
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

                /*mActivity.setResult(Activity.RESULT_OK);
                mActivity.finish();*/
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    Routine_Summary fm2 = new Routine_Summary();
                    fragmentTransaction
                            .replace(R.id.fragment_container, fm2)
                            .commit();
                }
            }
        });


        return v;
    }
}