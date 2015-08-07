package eie.android.crunch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Obstacle2 extends Fragment {

    private GoodHabit mHabit;
    private EditText mMotivationField;
    private static final String ARG_HABIT_ID = "habit_id";
    private Button mFinishButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHabit = new GoodHabit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_obstacle2, container, false);

        mMotivationField = (EditText) v.findViewById(R.id.obstacle2);
        mMotivationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getActivity() == null) {
                    return;
                }
                mHabit.setMotivation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFinishButton = (Button) v.findViewById(R.id.finish_button);
        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();

            }
        });

        return v;
    }

}