package eie.android.crunch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

/**
 * Created by emilyw on 7/13/15.
 * <p/>
 * NOTE: need to have a default value OR something else
 * currently app will crash if you don't input the right types of values
 */
public class Home_Obstacle extends Fragment {

    public static Fragment newInstance(Context context) {
        Home_Motivation f = new Home_Motivation();

        return f;
    }

    private TextView mot1;
    private TextView mot2;
    private TextView mot3;
    private Button nextButton;
    private Activity mActivity;

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

        View v = inflater.inflate(R.layout.fragment_display_obstacle, container, false);

        mot1 = (TextView) v.findViewById(R.id.obstacle1);
        mot2 = (TextView) v.findViewById(R.id.obstacle2);
        mot3 = (TextView) v.findViewById(R.id.obstacle3);


        ParseUser user = ParseUser.getCurrentUser();
        ParseObject habit = (ParseObject) user.get("current_habit");
        if (habit != null) {
            try {
                habit.fetchIfNeeded();

                if (habit.get("mot1") != null){
                    mot1.setText((String) habit.get("obstacle1"));
                }

                if (habit.get("mot2") != null){
                    mot2.setText((String) habit.get("obstacle2"));
                }


                if (habit.get("mot3") != null){
                    mot3.setText((String) habit.get("obstacle3"));
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return v;
    }

}