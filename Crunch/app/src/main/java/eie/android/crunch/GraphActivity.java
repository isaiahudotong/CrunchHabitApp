package eie.android.crunch;

/**
 * Created by iudotong on 7/13/15.
 */
import org.achartengine.GraphicalView;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.app.Activity;



public class GraphActivity extends Activity {

    private BarGraph bar = new BarGraph();
    private GraphicalView barView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bargraph);

        //bar graph view initialize
        barView = bar.getBarView(this);
        LinearLayout barLayout = (LinearLayout) findViewById(R.id.barchart);
        barLayout.addView(barView);


    }

    public void buttonHandler(View view) {

        LinearLayout barLayout = (LinearLayout) findViewById(R.id.barchart);
        barLayout.removeView(barView);
        barView.repaint();
    }

}