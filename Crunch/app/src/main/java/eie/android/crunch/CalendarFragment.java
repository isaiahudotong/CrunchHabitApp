package eie.android.crunch;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by elliekang on 7/9/15.
 */
public class CalendarFragment extends DialogFragment {

    public static CalendarFragment newInstance(int num){

        CalendarFragment dialogFragment = new CalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("num", num);
        dialogFragment.setArguments(bundle);

        return dialogFragment;

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_did_you_do)
                .setPositiveButton(R.string.yes_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mListener.onDialogPositiveClick(CalendarFragment.this);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
                    }
                })
                .setNegativeButton(R.string.no_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mListener.onDialogNegativeClick(CalendarFragment.this);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, getActivity().getIntent());
                    }
                });
        return builder.create();
    }


}
