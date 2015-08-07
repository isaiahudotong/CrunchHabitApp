package eie.android.crunch;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by iudotong on 7/29/15.
 */
public class CuesPage_When extends Fragment implements ReminderDialog.ReminderDialogListener {




    private Button mButtton;
    private Button mReminderButton;
    private Activity mActivity;
    final static int RQS_1 = 1;
    private TextView whenView;
    private EditText data;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }



    public void showReminderDialog() {
        DialogFragment dialog = new ReminderDialog();
        dialog.setTargetFragment(this, 0);
        dialog.show(getFragmentManager(), "ReminderDialog");
    }

    @Override
    public void onDialogPositiveClick(ReminderDialog dialog) {

        Calendar calNow = Calendar.getInstance();

        // saving the times the user indicated

        Calendar calSet = (Calendar) calNow.clone();

        TimePicker tp = dialog.getTimePicker();

        ToggleButton[] buttons = dialog.getButtons();

        calSet.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
        calSet.set(Calendar.MINUTE, tp.getCurrentMinute());
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if (calSet.compareTo(calNow) <= 0) {
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }

        // add in the days the user selects to set a reminder, e.g. Calendar.MONDAY
        // correspond with whether a button was clicked for each one

        LinkedList<Integer> daysForAlarm = new LinkedList();

        for (int i = 0; i < buttons.length; i++) {

            if (buttons[i].isChecked()) {

                if (buttons[i].getId() == R.id.monday) {
                    daysForAlarm.add(Calendar.MONDAY);
                } else if (buttons[i].getId() == R.id.tuesday) {
                    daysForAlarm.add(Calendar.TUESDAY);
                } else if (buttons[i].getId() == R.id.wednesday) {
                    daysForAlarm.add(Calendar.WEDNESDAY);
                } else if (buttons[i].getId() == R.id.thursday) {
                    daysForAlarm.add(Calendar.THURSDAY);
                } else if (buttons[i].getId() == R.id.friday) {
                    daysForAlarm.add(Calendar.FRIDAY);
                } else if (buttons[i].getId() == R.id.saturday) {
                    daysForAlarm.add(Calendar.SATURDAY);
                } else {
                    daysForAlarm.add(Calendar.SUNDAY);
                }
            }
        }



        Toast.makeText(getActivity().getBaseContext(), "The habit time " +
                "selected!", Toast.LENGTH_SHORT).show();

        setAlarm(calSet, dialog.getRepeat().isChecked(), daysForAlarm);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.cuespage_when, container, false);

        whenView = (TextView) v.findViewById(R.id.when_name);

        ParseUser user = ParseUser.getCurrentUser();
        ParseObject habit = (ParseObject) user.get("current_habit");
        if (habit != null) {
            try {
                habit.fetchIfNeeded();
                String em = (String) habit.get("bad_when_trigger");
                whenView.setText(em);

            } catch (ParseException e) {
                e.printStackTrace();

            }

        }



        mReminderButton = (Button) v.findViewById(R.id.reminder_button);
        mReminderButton.setOnClickListener(new View.OnClickListener() {

            // upon clicking "Set Reminder" button on Lens Page

            @Override
            public void onClick(View v) {
                showReminderDialog();
            }
        });



        Button mReturnButton = (Button) v.findViewById(R.id.return_button);
        mReturnButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                ImageView imageView = (ImageView) v.findViewById(R.id.imageView4);


                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                DisplayCueFragment fm2 = new DisplayCueFragment();
                fm2.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
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


    private void setAlarm(Calendar targetCal, boolean repeat, LinkedList<Integer> dayList) {


        // error: not an activity


        Calendar calendar = Calendar.getInstance();

        // this LinkedList stores the days the reminder should occur at

        LinkedList<Integer> selectedDays = dayList;


        Intent intent = new Intent(getActivity().getBaseContext(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getBaseContext(),
                RQS_1, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context
                .ALARM_SERVICE);

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        for (int i = 0; i < selectedDays.size(); i++) {
            if (day == selectedDays.get(i)) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        targetCal.getTimeInMillis(),
                        // one days worth of time
                        24 * 60 * 60 * 1000,
                        pendingIntent);

            }
        }

        if (repeat) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    targetCal.getTimeInMillis(),
                    TimeUnit.MINUTES.toMillis(5),
                    pendingIntent);

        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    targetCal.getTimeInMillis(),
                    pendingIntent);


        }
    }


    private void cancelAlarm() {


        Intent intent = new Intent(getActivity().getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getBaseContext(),
                RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context
                .ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }



}
