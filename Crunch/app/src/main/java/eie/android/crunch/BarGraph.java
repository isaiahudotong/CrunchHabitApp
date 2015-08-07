package eie.android.crunch;

/**
 * Created by iudotong on 7/13/15.
 */
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.common.GooglePlayServicesUtil;


public class BarGraph extends Fragment {

    private GraphicalView view;
    private int totalLength = 100;
    private int userInputLength;
    private ProgressBar mProgressBar;
    private int mProgressStatus;
    private Handler mHandler = new Handler();

    private CategorySeries series = new CategorySeries("Accelerometer");
    private XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    private XYSeriesRenderer renderer = new XYSeriesRenderer(); //used for customizing
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();


    @SuppressWarnings("deprecation")
    public BarGraph() {


        int[] y = {10, 2, 3, 4, 5, 6, 7, 8, 9, 100};

        for (int i = 0; i < 10; i++) {
            series.add("Bar" + (i + 1), y[i]);
        }


        dataset.addSeries(series.toXYSeries());


        //below are just customization codes
        renderer.setChartValuesSpacing((float) 2);
        renderer.setColor(Color.RED);


        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setChartTitle("Days Each GoodHabit performed");
        mRenderer.setXTitle("Number GoodHabit");
        mRenderer.setYTitle("Days");

        float textSize = 50, textSmaller = 20, angle = 270;
        mRenderer.setAxisTitleTextSize(textSize);

        mRenderer.setYLabelsAngle(angle);
        mRenderer.setLabelsTextSize(textSize);
        mRenderer.setChartTitleTextSize(textSize);
        mRenderer.setLegendTextSize(textSmaller);
        mRenderer.setBarSpacing(1);
        mRenderer.setChartTitleTextSize(textSize);
        mRenderer.setAxesColor(Color.WHITE);
        mRenderer.setGridColor(Color.WHITE);
        mRenderer.setLabelsColor(Color.rgb(112, 128, 144));

        //adjusting margin width of y-axis (so y axis labels can be seen)
        int[] i = mRenderer.getMargins();
        i[0] += 50;//top
        i[1] += 50;//left (increment here)
        //i[2] = 0;//bottom
        //i[3] = 0;//right
        mRenderer.setMargins(i);
    }

    public GraphicalView getBarView(Context context) {
        view = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);
        return view;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bargraph, container, false);

        userInputLength = 10; //need to get data from creation flow
        final int progressLength = totalLength / userInputLength;
        mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus += progressLength;

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);
                            //progressText.setText(mProgressStatus + "/" + mProgressBar.getMax());
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
        return v;
    }

}
