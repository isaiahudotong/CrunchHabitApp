package eie.android.crunch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by elliekang on 7/30/15.
 */
public class CurrentUserProgress extends Fragment {
    private SeekBar progressBar;
    private Handler handler =new Handler();
    private Activity mActivity;
    private Handler seekBarHandler;
    private int progress = 0;
    private int max = 0;
    private ParseObject habit;
    private String currentName;

    public CurrentUserProgress() {

    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();
        View v = lf.inflate(R.layout.fragment_progress_current, container, false);
        progressBar = (SeekBar) v.findViewById(R.id.progressBar);

        ParseUser user = ParseUser.getCurrentUser();
        currentName = (String) user.get("name");

        habit = (ParseObject) user.get("current_habit");
        // habit is the Individual Habit
        if (habit != null) {
            try {
                habit.fetchIfNeeded();
                Object pr = habit.get("progress_status");
                if(pr != null) {
                    progress = (Integer) pr;

                } else {
                    progress = 0;
                    habit.put("progress_status", progress);
                }

                Object maxTemp = habit.get("days");
                if(maxTemp != null) {
                    max = (Integer) maxTemp;
                } else {
                    max = 30;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        //seekBar.setMax(max);
        progressBar.setMax(30);
        seekBarHandler = new Handler();
        seekBarHandler.post(new Runnable() {
            @Override
            public void run() {
                if (progressBar != null) {
                    progressBar.setProgress(progress);
                    if (habit != null) {
                        habit.put("progress_status", progress);
                    }

                    progressBar.setEnabled(false);
                }
            }
        });



        return v;
    }

    public void setProgressDecrease() {
        progress--;
        if(progress <= 0) {
            progress = 0;
        }
        setCurrentProgress(progress);
    }

    public void setProgressIncrease() {
        progress++;
        setCurrentProgress(progress);
    }

    public int getProgress() {
        return progress;
    }

    public int getMax() {
        return max;
    }



    public void setCurrentProgress(final int pro) {
        seekBarHandler.post(new Runnable() {
            @Override
            public void run() {
                if (progressBar != null && habit != null) {
                    progressBar.setProgress(pro);
                    habit.put("progress_status", progress);
                    progressBar.setEnabled(false);
                    if(progress >= max) {
                        Toast.makeText(getActivity().getApplicationContext(), currentName + "wins!" , Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Nice try but you need to create a habit first to update your progress! \nClick on the create new button to start!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
