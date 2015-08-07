package eie.android.crunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by elliekang on 7/24/15.
 */
public class DisplayRoutineFragment extends Fragment {

    private Button calendarButton;
    private Button cuesButton;
    private Button challengeButton;
    private TextView nameView;
    private Activity mActivity;

    private ProgressBar progressBar;
    private int progressBarStatus = 0;
    private Handler handler = new Handler();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_routine_display, container, false);

//        nameView = (TextView) v.findViewById(R.id.habit_title);
//
//        nameView.setText("Your new routine.");
//
//        ParseUser user = ParseUser.getCurrentUser();
//        ParseObject habit = (ParseObject) user.get("current_habit");
//        if (habit != null) {
//            try {
//                habit.fetchIfNeeded();
//
//                ParseObject goodHabit = (ParseObject) habit.get("good_habit");
//                if (goodHabit != null) {
//                    try {
//                        goodHabit.fetchIfNeeded();
//                        nameView.setText((String) goodHabit.get("good_habit_short"));
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//        }


        calendarButton = (Button) v.findViewById(R.id.calendar_button);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mActivity, CalendarPage.class);
                startActivity(i);
            }
        });


        progressBar = ((ProgressBar) v.findViewById(R.id.progressBar));
        /*int color = 0xFF00FF00;
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);*/
        progressBar.setVisibility(View.VISIBLE);

        /*new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    progressBarStatus += 1;
                    // Update the progress bar and display the

                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                            //textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.

                        //Just to display the progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/

        progressBar.setMax(6);
        progressBar.setProgress(2);




        return v;

    }

//    public static DisplayRoutineFragment newInstance(String text) {
//
//        DisplayRoutineFragment f = new DisplayRoutineFragment();
//        Bundle b = new Bundle();
//        b.putString("msg", text);
//
//        f.setArguments(b);
//
//        return f;
//    }


}


