package eie.android.crunch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by elliekang on 7/28/15.
 */
public class MotivationScreenSlidePage extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_motivation_slide_page, container, false);
        TextView t = (TextView) rootView.findViewById(R.id.motivationText);
        t.setText("You can do it!");
        return rootView;
    }

}
