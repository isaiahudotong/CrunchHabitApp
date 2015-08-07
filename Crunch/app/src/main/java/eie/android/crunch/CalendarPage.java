package eie.android.crunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CalendarPage extends Fragment {
    private static final String ARG_HABIT_ID = "habit_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int RESULT_OK = 1;

    private CalendarView mCalendarView;
    private boolean completedTask = false;
    private CaldroidFragment caldroidFragment;
    private Date minDate;
    private Date maxDate;
    private Date currentDate;
    private int checked;
    private int missed;
    private static TextView checkedCount;
    private static TextView missedCount;
    private static TextView goodHabitText;
    private ArrayList<Date> checkdates = new ArrayList<>();
    private ArrayList<Date> missdates = new ArrayList<>();

    private ParseUser currentUser;

    private int mStackLevel = 0;
    public static final int DIALOG_FRAGMENT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mStackLevel = savedInstanceState.getInt("level");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.activity_calendar_page, container, false);

        /*ParseObject habit = (ParseObject) currentUser.get("current_habit");
        if (habit != null) {
            try {
                habit.fetchIfNeeded();
                setChecked((Integer) habit.get("progress_status"));
                setMissed((Integer) habit.get("progress_missed"));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }*/

        if (savedInstanceState != null) {

            checked = savedInstanceState.getInt("progresscheck");
            missed = savedInstanceState.getInt("progressmiss");
            ArrayList<Date> cdates = (ArrayList<Date>)savedInstanceState.getSerializable("checkdates");
            ArrayList<Date> mdates = (ArrayList<Date>)savedInstanceState.getSerializable("missdates");

            for(Date d : cdates) {
                caldroidFragment.setBackgroundResourceForDate(R.color.background_color, d);
            }

            for(Date m:mdates) {
                caldroidFragment.setBackgroundResourceForDate(R.color.Red, m);
            }

            caldroidFragment.refreshView();
            checkedCount.setText(String.valueOf(checked));

            // ... update your views


        } else {
            // no previous state, start fresh
        }

        checkedCount = (TextView) v.findViewById(R.id.checkCount);
        missedCount = (TextView) v.findViewById(R.id.missCount);

        ParseUser currentUser = ParseUser.getCurrentUser();

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

        caldroidFragment.setArguments(args);

        //int lengthOfHabit = currentUser.getInt("days");
        int lengthOfHabit = 30;

        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        int todayYear = today.getYear();
        int todayMonth = today.getMonth();
        int todayDate = today.getDate();
        int newDate = todayDate+lengthOfHabit;
        int newMonth = todayMonth;
        int newYear = todayYear;
        if(newDate > 31) {
            newMonth++;
            newDate = newDate - 31;
            if(newMonth > 11) {
                newYear++;
                newMonth = newMonth - 11;
            }
        }
        setMax(newYear, newMonth, newDate);

        caldroidFragment.setMinDate(today);

        caldroidFragment.setMaxDate(maxDate);

        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.replace(R.id.calendar, caldroidFragment);
        t.commit();

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                currentDate = date;
                showCalendarDialog(DIALOG_FRAGMENT);

                //String currentDate = (String) android.text.format.DateFormat.format("yyyy-MM-dd", date);
                //Toast.makeText(getApplicationContext(), currentDate, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onChangeMonth(int month, int year) {
                //month changed
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                /*showCalendarDialog();
                String currentDate = (String) android.text.format.DateFormat.format("yyyy-MM-dd", date);
                if(isCompletedTask()) {
                    caldroidFragment.setBackgroundResourceForDate(R.color.background_color,date);
                    caldroidFragment.refreshView();
                }*/
                currentDate = date;
                showCalendarDialog(DIALOG_FRAGMENT);
            }

            @Override
            public void onCaldroidViewCreated() {
                //created Caldroid calendar
            }

        };

        caldroidFragment.setCaldroidListener(listener);

        return v;
    }

    public void showCalendarDialog(int type) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("CalendarFragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        switch (type) {

            case DIALOG_FRAGMENT:

                DialogFragment dialogFrag = CalendarFragment.newInstance(123);
                dialogFrag.setTargetFragment(this, DIALOG_FRAGMENT);
                dialogFrag.show(getFragmentManager().beginTransaction(), "CalendarFragment");

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case DIALOG_FRAGMENT:

                if (resultCode == Activity.RESULT_OK) {
                    completedTask = true;
                    checked++;
                    checkdates.add(currentDate);
                    caldroidFragment.setBackgroundResourceForDate(R.color.background_color, currentDate);
                    caldroidFragment.refreshView();
                    checkedCount.setText(String.valueOf(checked));

                } else if (resultCode == Activity.RESULT_CANCELED){
                    completedTask = false;
                    missed++;
                    missdates.add(currentDate);
                    caldroidFragment.setBackgroundResourceForDate(R.color.Red, currentDate);
                    caldroidFragment.refreshView();
                    missedCount.setText(String.valueOf(missed));

                }

                break;
        }
    }

    public void addMissed() {
        missed++;
    }

    public void addMissDates(Date current) {
        missdates.add(current);
    }

    public void setBackGroundToPositive() {
        caldroidFragment.setBackgroundResourceForDate(R.color.background_color, currentDate);
        caldroidFragment.refreshView();
        checkedCount.setText(String.valueOf(checked));
    }

    public void setBackGroundToNegative() {
        caldroidFragment.setBackgroundResourceForDate(R.color.Red, currentDate);
        caldroidFragment.refreshView();
        missedCount.setText(String.valueOf(missed));
    }

    public void addCheckDates(Date current) {
        checkdates.add(currentDate);
    }

    public void addChecked() {
        checked++;
    }

    public boolean isCompletedTask() {
        return completedTask;
    }

    public void setCompletedTask(boolean completedTask) {
        this.completedTask = completedTask;
    }

    public void setMin(int year, int month, int day) {
        Date temp = new Date(year,month,day);
        minDate = temp;
    }

    public void setMax(int year, int month, int day) {
        Date temp = new Date(year,month,day);
        maxDate = temp;
    }

//    @Override
//    public void onBackPressed() {
//        Intent i = new Intent();
//        i.putExtra("progresscheck", checked);
//        i.putExtra("progressmiss", missed);
//        getActivity().setResult(RESULT_OK, i);
//    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        /*savedInstanceState.putInt("progresscheck", checked);
        savedInstanceState.putInt("progressmiss", missed);
        savedInstanceState.putSerializable("checkdates", checkdates);
        savedInstanceState.putSerializable("missdates",missdates);*/


    }

    public void setChecked(int s) {
        checked = s;
    }

    public void setMissed(int m) {
        missed = m;
    }
}