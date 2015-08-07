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
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by iudotong on 7/30/15.
 */
public class CuesPage_Who extends Fragment {

    private Activity mActivity;
    private TextView whoView;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cuespage_who, container, false);

        whoView = (TextView) v.findViewById(R.id.who_name);


        ParseUser user = ParseUser.getCurrentUser();
        ParseObject habit = (ParseObject) user.get("current_habit");
        if (habit != null) {
            try {
                habit.fetchIfNeeded();
                String em = (String) habit.get("person");
                whoView.setText(em);

            } catch (ParseException e) {
                e.printStackTrace();

            }

        }

        Button mReturnButton = (Button) v.findViewById(R.id.return_button);
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                DisplayCueFragment fm5 = new DisplayCueFragment();
                fm5.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm5)
                        .addToBackStack(null)
                         .commit();
            }

        });

        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
