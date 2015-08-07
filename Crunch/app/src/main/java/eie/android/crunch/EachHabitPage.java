package eie.android.crunch;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.yalantis.guillotine.animation.GuillotineAnimation;

/**
 * Created by elliekang on 7/24/15.
 */
public class EachHabitPage extends AppCompatActivity implements
        EachHabitPageFragment.NoticeDialogListener {

    Toolbar toolbar;

    FrameLayout root;

    View contentHamburger;

    private static final long RIPPLE_DURATION = 250;
    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPurgeable = true; // Tell to garbage collector that whether it needs free memory, the Bitmap can be cleared
        opts.inTempStorage = new byte[32 * 1024];


        // setting up the new menu
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null); }


        root = (FrameLayout) findViewById(R.id.root);
        contentHamburger = findViewById(R.id.content_hamburger);

        final View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        final GuillotineAnimation animation = new GuillotineAnimation.GuillotineBuilder
                (guillotineMenu,
                        guillotineMenu
                                .findViewById(R
                                        .id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        // making the pager for obstacles and motivation

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        TextView c_c = (TextView) guillotineMenu.findViewById(R.id.c_c);
        TextView my_habits = (TextView) guillotineMenu.findViewById(R.id.my_habits);
        TextView cues = (TextView) guillotineMenu.findViewById(R.id.cues);
        TextView logout = (TextView) guillotineMenu.findViewById(R.id.logout_button);
        TextView challenge = (TextView) guillotineMenu.findViewById(R.id.challenges);


        c_c.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                animation.close();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                CrunchCentralMainFragment fm2 = new CrunchCentralMainFragment();
                fm2.onAttach(EachHabitPage.this);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();


            }
        });

        my_habits.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                animation.close();

                Intent i = new Intent(getApplicationContext(), EachHabitPage.class);
                startActivity(i);


            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                animation.close();

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                ChallengeIntro fm2 = new ChallengeIntro();
                fm2.onAttach(EachHabitPage.this);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();

            }
        });

        cues.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                animation.close();

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                DisplayCueFragment fm2 = new DisplayCueFragment();
                fm2.onAttach(EachHabitPage.this);
                fragmentTransaction
                        .replace(R.id.fragment_container, fm2)
                        .commit();


            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user
                ParseUser.logOut();
                finish();
            }
        });




    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = Fragment.instantiate(EachHabitPage.this,
                            HomePage.class.getName());
                    break;

                case 1:
                    fragment = Fragment.instantiate(EachHabitPage.this,
                            Home_Motivation.class.getName());
                    break;
                case 2:
                    fragment = Fragment.instantiate(EachHabitPage.this,
                            Home_Obstacle.class.getName());
                    break;


            }

            return fragment;


        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }


    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

}

