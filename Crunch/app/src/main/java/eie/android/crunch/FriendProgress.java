package eie.android.crunch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.parse.ParseObject;

/**
 * Created by elliekang on 7/30/15.
 */
public class FriendProgress extends Fragment {
    private SeekBar progressBar;
    private Handler handler =new Handler();
    private Activity mActivity;
    private Handler seekBarHandler;
    private ParseObject habit;
    private int progress = 0;
    private int max = 0;
    private String currentUserId;

    public FriendProgress() {

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
        View v = lf.inflate(R.layout.fragment_progress_friend, container, false);
        progressBar = (SeekBar) v.findViewById(R.id.progressBar2);

        /*currentUserId = getObjectID();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", currentUserId);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    habit = (ParseObject) userList.get(0).get("current_habit");
                    // habit is the Individual Habit
                    if (habit != null) {
                        try {
                            habit.fetchIfNeeded();
                            Object pr = habit.get("progress_status");
                            if (pr != null) {
                                progress = (Integer) pr;
                            } else {
                                progress = 0;
                                habit.put("progress_status", progress);
                            }

                            Object maxTemp = habit.get("days");
                            if (maxTemp != null) {
                                max = (Integer) maxTemp;
                            } else {
                                max = 30;
                            }

                        } catch (ParseException ex) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });*/

        //seekBar.setMax(max);
        progressBar.setMax(30);
        seekBarHandler = new Handler();
        seekBarHandler.post(new Runnable() {
            @Override
            public void run() {
                if (progressBar != null) {
                    progressBar.setProgress(15);
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

    public void setCurrentProgress(final int pro) {
        seekBarHandler.post(new Runnable() {
            @Override
            public void run() {
                if (progressBar != null) {
                    progressBar.setProgress(pro);
                    //habit.put("progress_status", pro);
                    progressBar.setEnabled(false);
                }
            }
        });
    }

    /*public String getObjectID() {

    }*/
}
