package eie.android.crunch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;

import android.provider.ContactsContract;

import com.parse.ParseUser;

/**
 * Created by emilyw on 7/13/15.
 * <p/>
 * NOTE: need to have a default value OR something else
 * currently app will crash if you don't input the right types of values
 */
//This is the magnifying class Lens Page
public class DisplayCueFragment extends Fragment {

    private static final int REQUEST_CONTACT = 1;

    private ImageButton when;
    private ImageButton where;
    private ImageButton emotion;
    private ImageButton withWho;
    //    private Button feels;
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

        View v = inflater.inflate(R.layout.fragment_display_cue, container, false);
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


        ImageButton emotion = (ImageButton) v.findViewById(R.id.emotion);
        ImageButton where = (ImageButton) v.findViewById(R.id.where);
        ImageButton when = (ImageButton) v.findViewById(R.id.when);
        ImageButton withWho = (ImageButton) v.findViewById(R.id.withWho);

        ImageButton[] buttons = {where, when, emotion, withWho};




        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    switch (v.getId()) {

                        case R.id.when:

                            CuesPage_When fm2 = new CuesPage_When();
                            fm2.onAttach(mActivity);
                            fragmentTransaction
                                    .replace(R.id.fragment_container, fm2)
                                    .commit();
                            break;

                        case R.id.where:
                            CuesPage_Where fm3 = new CuesPage_Where();
                            fm3.onAttach(mActivity);
                            fragmentTransaction
                                    .replace(R.id.fragment_container, fm3)
                                    .commit();
                            break;

                        case R.id.emotion:

                            CuesPage_Feels fm4 = new CuesPage_Feels();
                            fm4.onAttach(mActivity);
                            fragmentTransaction
                                    .replace(R.id.fragment_container, fm4)
                                    .commit();

                            break;

                        case R.id.withWho:

                            CuesPage_Who fm5 = new CuesPage_Who();
                            fm5.onAttach(mActivity);
                            fragmentTransaction
                                    .replace(R.id.fragment_container, fm5)
                                    .commit();

                            break;

                    }


                }
            });
        }

        return v;
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    // some code
    public void onClick(View v){
        v.startAnimation(buttonClick);
    }


}


//    public static DisplayCueFragment newInstance(String text) {
//
//        DisplayCueFragment f = new DisplayCueFragment();
//        Bundle b = new Bundle();
//        b.putString("msg", text);
//
//        f.setArguments(b);
//
//        return f;
//    }
