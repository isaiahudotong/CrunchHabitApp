package eie.android.crunch;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Created by emilyw on 7/13/15.
 * <p>
 * NOTE: need to have a default value OR something else
 * currently app will crash if you don't input the right types of values
 */
public class Identify_Cues extends Fragment {

    public static Fragment newInstance(Context context) {
        Identify_Cues f = new Identify_Cues();

        return f;
    }

    private static final int REQUEST_CONTACT = 1;

    private Button nextButton;
    private Button where;
    private Button when;
    private Button withWho;
    private Button feels;

    public Button getWhereButton() {
        return where;
    }

    public Button getWhenButton() {
        return when;
    }

    public Button getFeelsButton() {
        return feels;
    }

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

        View v = inflater.inflate(R.layout.fragment_bad_cue, container, false);

        where = (Button) v.findViewById(R.id.where);
        withWho = (Button) v.findViewById(R.id.withWho);
        when = (Button) v.findViewById(R.id.when);
        feels = (Button) v.findViewById(R.id.feels);

        Button[] buttons = {where, withWho, when, feels};

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    switch (v.getId()) {

                        case R.id.where:

                            Cue_Where fm2 = new Cue_Where();
                            fm2.onAttach(mActivity);
                            fragmentTransaction
                                    .replace(R.id.fragment_container, fm2)
                                    .commit();
                            break;

                        case R.id.when:
                            Cue_When fm3 = new Cue_When();
                            fm3.onAttach(mActivity);
                            fragmentTransaction
                                    .replace(R.id.fragment_container, fm3)
                                    .commit();
                            break;

                        case R.id.withWho:

                            final Intent pickContact = new Intent(Intent.ACTION_PICK,
                                    ContactsContract.Contacts.CONTENT_URI);

                            startActivityForResult(pickContact, REQUEST_CONTACT);

                            break;

                        case R.id.feels:

                            Cue_Feels fm4 = new Cue_Feels();
                            fm4.onAttach(mActivity);
                            fragmentTransaction
                                    .replace(R.id.fragment_container, fm4)
                                    .commit();


                    }


                }
            });
        }
        // Clicking the next button should save the bad habit
        nextButton = (Button) v.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                Identify_Plan fm2 = new Identify_Plan();
                fm2.onAttach(mActivity);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            // Specify which fields you want your query to return
            // values for.
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME,
            };
            // Perform your query - the contactUri is like a "where"
            // clause here
            ContentResolver resolver = getActivity().getContentResolver();
            Cursor c = resolver
                    .query(contactUri, queryFields, null, null, null);

            try {
                // Double-check that you actually got results
                if (c.getCount() == 0) {
                    return;
                }

                // Pull out the first column of the first row of data -
                // that is your person's name
                c.moveToFirst();

                String person = c.getString(0);

                IndividualHabit individualHabit = ((NewIndividualHabitActivity) getActivity())
                        .getIndividualHabit();

                individualHabit.setPerson(person);

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

                withWho.setText(individualHabit.getPerson());

            } finally {
                c.close();
            }
        }

    }
}