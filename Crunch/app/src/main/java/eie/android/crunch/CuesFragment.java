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
public class CuesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cues_frag, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tv2);
        tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static CuesFragment newInstance(String text) {

        CuesFragment f = new CuesFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}