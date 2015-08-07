package eie.android.crunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.List;


public class EachHabitBorderFragment extends Fragment {

    private View v;
    private TextView nameView;
    ParseQueryAdapter<ParseObject> habitAdapter;
    Activity mActivity;

    private String habitName = "make a habit to display the habit";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.view_camera_and_title, container, false);

        nameView = (TextView) v.findViewById(R.id.habit_title);
        nameView.setText(habitName);

        ParseUser user = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = user.getRelation("habits");
        ParseQuery<ParseObject> query = relation.getQuery();

        query.getFirstInBackground(new GetCallback<ParseObject>() {

            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                } else {
                    Log.d("score", "Retrieved the object.");
                }
            }

        });


        return v;
    }
}