package eie.android.crunch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Calendar;

/**
 * Created by elliekang on 7/28/15.
 */
public class HomePage extends Fragment  {
    private TextView habitName;
    private Button yesButton;
    private Button noButton;
    private Button demoStatusButton;
    private int clickCount = 0;

    private static final int MOTIVATION_NUM_PAGES = 2;
    private static final int OBSTACLE_NUM_PAGES = 5;

    private FragmentTransaction fragmentTransaction;
    private CurrentUserProgress progressFragment;

    private Activity mActivity;

    private boolean firstTimeClicked = false;
    private String firstTimeClickedKey="FIRST_TIME";

    private String sharedPreferencesKey = "MY_PREF";
    private String buttonClickedKey = "ALREADY_STATUS_UPDATED";
    private SharedPreferences mPrefs;
    private long savedDate = 0;
    private SharedPreferences.Editor mEditor;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_page, container, false);

        ImageView icon = new ImageView(mActivity);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));

        final FloatingActionButton actionButton = new FloatingActionButton.Builder(mActivity)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(mActivity);


        // repeat for all the icons you want to set

        ImageView itemIcon = new ImageView(mActivity);
        itemIcon.setImageDrawable(getResources().getDrawable(R.drawable.new_icon));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();
        button1.getLayoutParams().height = 180;
        button1.getLayoutParams().width = 180;

        ImageView itemIcon2 = new ImageView(mActivity);
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.calendar2));
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();

        ImageView itemIcon3 = new ImageView(mActivity);
        itemIcon3.setImageDrawable(getResources().getDrawable(R.drawable.microscope));
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();


        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(mActivity)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(actionButton)
                .build();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mActivity, NewIndividualHabitActivity.class);
                startActivity(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                CalendarPage fm2 = new CalendarPage();
                fm2.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();


            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                DisplayCueFragment fm2 = new DisplayCueFragment();
                fm2.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();
            }
        });

        habitName = (TextView) v.findViewById(R.id.habit_title);
        yesButton = (Button) v.findViewById(R.id.yesbutton);
        noButton = (Button) v.findViewById(R.id.nobutton);

        demoStatusButton = (Button) v.findViewById(R.id.demo_progress);

        mPrefs = getActivity().getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE);
        savedDate = mPrefs.getLong(buttonClickedKey, 0);
        firstTimeClicked = mPrefs.getBoolean(firstTimeClickedKey, true);
        checkPreferences();

        ParseUser user = ParseUser.getCurrentUser();
        final ParseObject habit = (ParseObject) user.get("current_habit");

        if (habit != null) {
            try {
                habit.fetchIfNeeded();
                ParseObject goodHabit = (ParseObject) habit.get("good_habit");
                if (goodHabit != null) {
                    try {
                        goodHabit.fetchIfNeeded();
                        habitName.setText((String) goodHabit.get("good_habit_short"));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        FragmentManager fm = getActivity().getSupportFragmentManager();

        fragmentTransaction = fm.beginTransaction();

        progressFragment = new CurrentUserProgress();

        progressFragment.onAttach(getActivity());

        fragmentTransaction
                .replace(R.id.progress_bar, progressFragment ,"fragment")
                .commit();


        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickCount < 1) {
                    progressFragment.setProgressIncrease();

                    if (progressFragment.getProgress() >= progressFragment.getMax()){
                        Toast.makeText(getActivity().getApplicationContext(), "Congrats on " +
                                "finishing! Create a new habit!", Toast.LENGTH_LONG).show();
                        habitName = (TextView) getView().findViewById(R.id.habit_title);
                        habitName.setText("Congratulations on your new habit!");


                    }
                




                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "You can only update " +
                            "once per day!", Toast.LENGTH_SHORT).show();
                }
                saveClickTime();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickCount < 1) {
                    progressFragment.setProgressDecrease();

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "You can only update " +
                            "once per day!", Toast.LENGTH_SHORT).show();
                }
                saveClickTime();



            }
        });

        demoStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.putBoolean(firstTimeClickedKey, true);
                yesButton.setClickable(true);
                noButton.setClickable(true);
                clickCount = 0;
            }
        });



        return v;

    }

    public void checkPreferences() {
        if(firstTimeClicked==false){
            if(savedDate>0){

                Calendar currentCal = Calendar.getInstance();
                currentCal.set(Calendar.MINUTE,1);
                currentCal.set(Calendar.HOUR,1);
                currentCal.set(Calendar.SECOND,1);
                currentCal.set(Calendar.MILLISECOND,1);

                Calendar savedCal = Calendar.getInstance();
                savedCal.setTimeInMillis(savedDate); //set the time in millis from saved in sharedPrefs
                savedCal.set(Calendar.MINUTE,1);
                savedCal.set(Calendar.HOUR,1);
                savedCal.set(Calendar.SECOND,1);
                savedCal.set(Calendar.MILLISECOND,1);

                if(currentCal.getTime().after(savedCal.getTime())){

                    yesButton.setClickable(true);
                    noButton.setClickable(true);
                } else if(currentCal.getTime().equals(savedCal.getTime())){
                    yesButton.setClickable(false);
                    noButton.setClickable(false);
                }

            }
        } else{

            //just set the button visible if app is used the first time

            yesButton.setVisibility(View.VISIBLE);
            noButton.setVisibility(View.VISIBLE);

        }
    }

    private void saveClickTime(){

        mEditor = mPrefs.edit();
        Calendar cal = Calendar.getInstance();
        long millis = cal.getTimeInMillis();
        mEditor.putLong(buttonClickedKey, millis);
        mEditor.putBoolean(firstTimeClickedKey, false); //the button is clicked first time, so set the boolean to false.
        mEditor.commit();
        clickCount++;


    }



}
