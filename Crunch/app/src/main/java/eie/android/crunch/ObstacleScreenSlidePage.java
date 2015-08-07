package eie.android.crunch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by elliekang on 7/30/15.
 */
public class ObstacleScreenSlidePage extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_obstacle_slide_page, container, false);
        TextView t = (TextView) rootView.findViewById(R.id.obstacleText);
        t.setText("Don't be tempted by that Princess cupcake");
        return rootView;
    }
}
