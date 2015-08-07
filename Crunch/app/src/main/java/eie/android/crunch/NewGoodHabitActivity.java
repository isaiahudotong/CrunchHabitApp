package eie.android.crunch;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

/*
 * NewIndividualHabitActivity contains a fragments that handle
 * data entry.
 * The Activity manages the overall habit data.
 */
public class NewGoodHabitActivity extends FragmentActivity {

    private GoodHabit goodHabit;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        goodHabit = new GoodHabit();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        // Starts by identifying the bad habit in LensQ_1
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        LensQ_1 fm2 = new LensQ_1();
        fragmentTransaction
                .replace(R.id.fragment_container, fm2)
                .commit();
    }

    public GoodHabit getGoodHabit() {
        return goodHabit;
    }

}
