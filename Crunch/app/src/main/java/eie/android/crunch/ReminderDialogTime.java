package eie.android.crunch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.LinkedList;


/**
 * Created by emilyw on 7/22/15.
 */
public class ReminderDialogTime extends DialogFragment {


    ToggleButton sunday;
    ToggleButton monday;
    ToggleButton tuesday;
    ToggleButton wednesday;
    ToggleButton thursday;
    ToggleButton friday;
    ToggleButton saturday;

    final ToggleButton buttons[] = new ToggleButton[7];

    public TimePicker timePicker;

    CheckBox optRepeat;

    private ReminderDialogListener callback;


    public TimePicker getTimePicker() {
        return timePicker;
    }

    public CheckBox getRepeat() {
        return optRepeat;
    }

    public ToggleButton[] getButtons() {
        return buttons;
    }

    public interface ReminderDialogListener {
        public void onDialogPositiveClick(ReminderDialogTime dialog);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (ReminderDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DialogClickListener interface");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View v = inflater.inflate(R.layout.fragment_bad_time, null);

        timePicker = (TimePicker) v.findViewById(R.id.time_picker);
        optRepeat = (CheckBox) v.findViewById(R.id.option_repeat);
        sunday = (ToggleButton) v.findViewById(R.id.sunday);
        monday = (ToggleButton) v.findViewById(R.id.monday);
        tuesday = (ToggleButton) v.findViewById(R.id.tuesday);
        wednesday = (ToggleButton) v.findViewById(R.id.wednesday);
        thursday = (ToggleButton) v.findViewById(R.id.thursday);
        friday = (ToggleButton) v.findViewById(R.id.friday);
        saturday = (ToggleButton) v.findViewById(R.id.saturday);


        buttons[0] = sunday;
        buttons[1] = monday;
        buttons[2] = tuesday;
        buttons[3] = wednesday;
        buttons[4] = thursday;
        buttons[5] = friday;
        buttons[6] = saturday;

        // setting up the listeners for the day of the week buttons

        for (int i = 0; i < buttons.length; i++) {
            final ToggleButton btn = buttons[i];
            btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        btn.setTypeface(null, Typeface.BOLD);
                    } else {
                        btn.setTypeface(null, Typeface.NORMAL);
                    }
                }
            });
        }

        // save user's preferences & set reminder


        builder.setView(v);

        builder.setMessage("Set new time")
                .setTitle("Habit Time")
                .setPositiveButton("Set Reminder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        callback.onDialogPositiveClick(ReminderDialogTime.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ReminderDialogTime.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

}

