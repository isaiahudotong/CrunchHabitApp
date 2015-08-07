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
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Created by emilyw on 7/13/15.
 */
public class Cue_Feels extends Fragment {

    private Activity mActivity;
    private static final String TAG = "TAG";
    private String emotion;

    private Button finished;

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

        View v = inflater.inflate(R.layout.fragment_feels, container, false);

        ImageButton happy = (ImageButton) v.findViewById(R.id.happy);
        ImageButton sad = (ImageButton) v.findViewById(R.id.sad);
        ImageButton angry = (ImageButton) v.findViewById(R.id.angry);
        ImageButton excited = (ImageButton) v.findViewById(R.id.excited);
        ImageButton embarassed = (ImageButton) v.findViewById(R.id.embarassed);
        ImageButton sleepy = (ImageButton) v.findViewById(R.id.sleepy);

        ImageButton[] emoticons = {happy, sad, angry, excited, embarassed, sleepy};


        for (int i = 0; i < emoticons.length; i++) {
            emoticons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {

                        case R.id.happy:
                            emotion = "happy";
                            break;

                        case R.id.sad:
                            emotion = "sad";


                            break;

                        case R.id.angry:
                            emotion = "angry";

                            break;

                        case R.id.excited:
                            emotion = "excited";

                            break;

                        case R.id.embarassed:
                            emotion = "embarrassed";

                            break;
                        case R.id.sleepy:
                            emotion = "sleepy";

                            break;
                    }
                }
            });
        }

        // Clicking the next button should save the bad habit
        finished = (Button) v.findViewById(R.id.next_button);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {

                IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                        .getIndividualHabit();

                habit.setBadEmotionBefore(emotion);

                habit.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e != null) {

                            // The save failed.

                            Toast.makeText(
                                    mActivity.getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();

                            Log.w(TAG,
                                    "Error : " + e.getMessage() + ":::" + e.getCode());
                        }
                    }
                });


                // may need to change to popping off the back stack later


                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                Identify_Cues fm2 = new Identify_Cues();
                fm2.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();
            }
        });


        return v;
    }


}