package eie.android.crunch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by iudotong on 7/28/15.
 */
public class RoutineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.routine_frag, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tv1);
        tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static RoutineFragment newInstance(String text) {

        RoutineFragment f = new RoutineFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}