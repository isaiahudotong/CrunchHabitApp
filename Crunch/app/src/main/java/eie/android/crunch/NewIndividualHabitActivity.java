package eie.android.crunch;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import com.parse.Parse;
import com.parse.ParseUser;

/*
 * NewIndividualHabitActivity contains a fragments that handle
 * data entry.
 * The Activity manages the overall habit data.
 */
public class NewIndividualHabitActivity extends FragmentActivity {


    private IndividualHabit individualHabit;
    private BadHabit badHabit;
    private GoodHabit goodHabit;
    private Reward reward;
    private Consequence consequence;
    private Obstacle obstacle;
    private static Context sContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        individualHabit = new IndividualHabit();

        badHabit = new BadHabit();

        goodHabit = new GoodHabit();

        reward = new Reward();

        consequence = new Consequence();

        obstacle = new Obstacle();

        sContext = getApplicationContext();


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);


        // Starts by identifying the bad habit in LensQ_1
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Recipe_Overview fm2 = new Recipe_Overview();
        fm2.onAttach(this);
        fragmentTransaction
                .replace(R.id.fragment_container, fm2)
                .commit();


    }


    public BadHabit getBadHabit() {
        return badHabit;
    }

    public GoodHabit getGoodHabit() {
        return goodHabit;
    }

    public IndividualHabit getIndividualHabit() {
        return individualHabit;
    }

    public Reward getReward() { return reward; }

    public Consequence getConsequence() { return consequence; }

    public Obstacle getObstacle() { return obstacle; }

    public static Context getContext() {
        return sContext;
    }


}
