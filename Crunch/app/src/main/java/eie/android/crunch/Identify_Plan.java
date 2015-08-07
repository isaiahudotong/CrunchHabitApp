package eie.android.crunch;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Identify_Plan extends Fragment implements ReminderDialog.ReminderDialogListener {

    public static Fragment newInstance(Context context) {
        Identify_Plan f = new Identify_Plan();

        return f;
    }

    private Button mNextButton;
    private Button mReminderButton;
    private EditText plan;

    private Activity mActivity;

    Button save;
    Button buttonCancelAlarm;
    TextView textAlarmPrompt;

    final static int TIME_DIALOG_ID = 999;

    final static int RQS_1 = 1;

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



        Toast.makeText(getActivity().getBaseContext(), "The habit time and days is " +
                "selected!", Toast.LENGTH_SHORT).show();

        setAlarm(calSet, dialog.getRepeat().isChecked(), daysForAlarm);


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        plan = (EditText) v.findViewById(R.id.plan);

        mNextButton = (Button) v.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (plan.getEditableText() == null || plan.getEditableText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Oops! You didn't enter your habit information", Toast.LENGTH_SHORT).show();

                } else {

                    BadHabit badHabit = ((NewIndividualHabitActivity) mActivity)
                            .getBadHabit();

                    GoodHabit goodHabit = ((NewIndividualHabitActivity) mActivity).getGoodHabit();

                    goodHabit.setGoodHabitShort(plan.getText().toString());

                    goodHabit.setBadHabits(badHabit.getObjectId());

                    goodHabit.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e != null) {

                                // The save failed.
                                Toast.makeText(
                                        mActivity.getApplicationContext(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    IndividualHabit individualHabit = ((NewIndividualHabitActivity) mActivity)
                            .getIndividualHabit();

                    individualHabit.setGoodHabit(goodHabit);

                    individualHabit.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e != null) {

                                // The save failed.
                                Toast.makeText(
                                        mActivity.getApplicationContext(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    badHabit.setGoodHabits(goodHabit.getObjectId());

                    badHabit.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e != null) {

                                // The save failed.
                                Toast.makeText(
                                        mActivity.getApplicationContext(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    Identify_Obstacle fm2 = new Identify_Obstacle();
                    fragmentTransaction
                            .replace(R.id.fragment_container, fm2)
                            .commit();
                }
            }
        });

        mReminderButton = (Button) v.findViewById(R.id.reminder_button);
        mReminderButton.setOnClickListener(new View.OnClickListener()

        {

            // upon clicking "Set Reminder" button on Lens Page

            @Override
            public void onClick(View v) {
                showReminderDialog();
            }

        });


        return v;
    }


    /**
     * Called when the activity is first created.
     */
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