package eie.android.crunch;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.DatePicker;

/**
 * Created by iudotong on 8/1/15.
 */
public class EachHabitPageFragment extends DialogFragment {



    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    public static final String EXTRA_DATE = "eie.android.crunch";
    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (NoticeDialogListener) activity;

        } catch(ClassCastException ex) {
            throw new ClassCastException(activity.toString() + "must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_did_you_do)
                .setPositiveButton(R.string.yes_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(EachHabitPageFragment.this);
                    }
                })
                .setNegativeButton(R.string.no_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(EachHabitPageFragment.this);
                    }
                });
        return builder.create();
    }


}