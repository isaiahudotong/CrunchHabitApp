package eie.android.crunch;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.Random;

/**
 * Created by emilyw on 7/13/15.
 * <p/>
 * NOTE: need to have a default value OR something else
 * currently app will crash if you don't input the right types of values
 */
public class Identify_Category extends Fragment {

    private EditText badHabitShort;
    private Button nextButton;
    private Activity mActivity;
    Random rand = new Random();
    private int likes = rand.nextInt(50);

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

        View v = inflater.inflate(R.layout.fragment_bad_category, container, false);

        final ImageButton exercise = (ImageButton) v.findViewById(R.id.exercise);
        ImageButton nutrition = (ImageButton) v.findViewById(R.id.nutrition);
        ImageButton learning = (ImageButton) v.findViewById(R.id.learning);
        ImageButton other = (ImageButton) v.findViewById(R.id.other);



        exercise.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                final IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                        .getIndividualHabit();
                habit.put("tag", "exercise");

                final GoodHabit goodHabit = ((NewIndividualHabitActivity) mActivity)
                        .getGoodHabit();
                goodHabit.put("good_tag", "exercise");
                goodHabit.put("likes", rand.nextInt(50));


            }
        });

        nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                        .getIndividualHabit();
                habit.put("tag", "nutrition");

                final GoodHabit goodHabit = ((NewIndividualHabitActivity) mActivity)
                        .getGoodHabit();
                goodHabit.put("good_tag", "nutrition");
                goodHabit.put("likes", rand.nextInt(50));

            }
        });

        learning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                        .getIndividualHabit();
                habit.put("tag", "learning");
                final GoodHabit goodHabit = ((NewIndividualHabitActivity) mActivity)
                        .getGoodHabit();
                goodHabit.put("good_tag", "learning");
                goodHabit.put("likes", rand.nextInt(50));

            }
        });

        other.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                        .getIndividualHabit();
                habit.put("tag", "lifestyle");
                final GoodHabit goodHabit = ((NewIndividualHabitActivity) mActivity)
                        .getGoodHabit();
                goodHabit.put("good_tag", "lifestyle");
                goodHabit.put("likes", rand.nextInt(50));

            }
        });


        // Clicking the next button should save the bad habit
        nextButton = (Button) v.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final GoodHabit gh = ((NewIndividualHabitActivity) mActivity)
                        .getGoodHabit();

                gh.saveInBackground(new SaveCallback() {

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


                final IndividualHabit habit = ((NewIndividualHabitActivity) mActivity)
                        .getIndividualHabit();

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

                Identify_Reward fm2 = new Identify_Reward();
                fm2.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();
            }
        });
        return v;
    }
}