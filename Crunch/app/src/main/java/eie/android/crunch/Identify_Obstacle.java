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

import java.util.ArrayList;

/**
 * Created by elliekang on 7/27/15.
 */
public class Identify_Obstacle extends Fragment {

    private EditText obstacle1;
    private EditText obstacle2;
    private EditText obstacle3;
    private EditText obstacle4;
    private EditText obstacle5;
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

        View v = inflater.inflate(R.layout.fragment_obstacle, container, false);

        obstacle1 = (EditText) v.findViewById(R.id.obstacle1);
        obstacle2 = (EditText) v.findViewById(R.id.obstacle2);
        obstacle3 = (EditText) v.findViewById(R.id.obstacle3);
        

        // Clicking the next button should save the bad habit
        finishButton = (Button) v.findViewById(R.id.next);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((obstacle1.getEditableText() == null || obstacle1.getEditableText().toString().equals("")) &&
                        (obstacle2.getEditableText() == null || obstacle2.getEditableText().toString().equals("")) && (obstacle3.getEditableText() == null ||
                        obstacle3.getEditableText().toString().equals(""))) {

                    Toast.makeText(getActivity(), "Oops! Please enter at least 1 obstacle", Toast.LENGTH_SHORT).show();

                } else {


                    IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                            .getIndividualHabit();

                    if (obstacle1 != null) {
                        habit.put("obstacle1", obstacle1.getText().toString());
                    }

                    if (obstacle2 != null) {
                        habit.put("obstacle2", obstacle2.getText().toString());
                    }

                    if (obstacle3 != null) {
                        habit.put("obstacle3", obstacle3.getText().toString());
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


                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    Identify_Motivation fm2 = new Identify_Motivation();
                    fragmentTransaction
                            .replace(R.id.fragment_container, fm2)
                            .commit();
                }
            }
        });


        return v;
    }
}