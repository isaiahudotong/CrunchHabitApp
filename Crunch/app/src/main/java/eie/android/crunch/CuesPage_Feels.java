package eie.android.crunch;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by iudotong on 7/30/15.
 */
public class CuesPage_Feels extends Fragment {

    private Activity mActivity;
    private TextView emotionView;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cuespage_feels, container, false);

        emotionView = (TextView) v.findViewById(R.id.feeling_title);


        ParseUser user = ParseUser.getCurrentUser();
        ParseObject habit = (ParseObject) user.get("current_habit");
        if (habit != null) {
            try {
                habit.fetchIfNeeded();
                String em = (String) habit.get("bad_emotion_before");

                if (em != null) {
                    emotionView.setText(em);

                    if (em.compareTo("angry") == 0) {
                        ImageView image1 = (ImageView) v.findViewById(R.id.imageView1);
                        image1.setVisibility(View.VISIBLE);

                    } else {
                        ImageView image1 = (ImageView) v.findViewById(R.id.imageView1);
                        image1.setVisibility(View.INVISIBLE);
                    }
                    if (em.compareTo("embarrassed") == 0) {
                        ImageView image2 = (ImageView) v.findViewById(R.id.imageView2);
                        image2.setVisibility(View.VISIBLE);

                    } else {
                        ImageView image2 = (ImageView) v.findViewById(R.id.imageView2);
                        image2.setVisibility(View.INVISIBLE);
                    }
                    if (em.compareTo("excited") == 0) {
                        ImageView image1 = (ImageView) v.findViewById(R.id.imageView3);
                        image1.setVisibility(View.VISIBLE);

                    } else {
                        ImageView image3 = (ImageView) v.findViewById(R.id.imageView3);
                        image3.setVisibility(View.INVISIBLE);
                    }
                    if (em.compareTo("happy") == 0) {
                        ImageView image4 = (ImageView) v.findViewById(R.id.imageView4);
                        image4.setVisibility(View.VISIBLE);

                    } else {
                        ImageView image4 = (ImageView) v.findViewById(R.id.imageView4);
                        image4.setVisibility(View.INVISIBLE);
                    }
                    if (em.compareTo("sad") == 0) {
                        ImageView image5 = (ImageView) v.findViewById(R.id.imageView5);
                        image5.setVisibility(View.VISIBLE);

                    } else {
                        ImageView image5 = (ImageView) v.findViewById(R.id.imageView5);
                        image5.setVisibility(View.INVISIBLE);
                    }
                    if (em.compareTo("sleepy") == 0) {
                        ImageView image6 = (ImageView) v.findViewById(R.id.imageView6);
                        image6.setVisibility(View.VISIBLE);

                    } else {
                        ImageView image6 = (ImageView) v.findViewById(R.id.imageView6);
                        image6.setVisibility(View.INVISIBLE);
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();

            }


            Button mReturnButton = (Button) v.findViewById(R.id.return_button);
            mReturnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    android.support.v4.app.FragmentManager fm = getFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    DisplayCueFragment fm4 = new DisplayCueFragment();
                    fm4.onAttach(mActivity);
                    fragmentTransaction
                            .replace(R.id.fragment_container, fm4)
                            .addToBackStack(null)
                            .commit();
                }


            });
        }
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}

// Use SET VISIBILTY OF IMAGE


