package eie.android.crunch;

/**
 * Created by emilyw on 7/31/15.
 */


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

public class CrunchCentralMainFragment extends Fragment  {


    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_crunch_world_pager, container, false);

        tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        tabs.setIndicatorColor(Color.parseColor("#00CCCC"));
        pager = (ViewPager) v.findViewById(R.id.pager2);
        adapter = new MyPagerAdapter(getFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());

        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);


        return v;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
////        switch (item.getItemId()) {
////
////            case R.id.action_contact:
////                QuickContactFragment dialog = new QuickContactFragment();
////                dialog.show(getSupportFragmentManager(), "QuickContactFragment");
////                return true;
////
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    private void changeColor(int newColor) {
//
//        tabs.setIndicatorColor(newColor);
//
//        // change ActionBar color just if an ActionBar is available
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//
//            Drawable colorDrawable = new ColorDrawable(newColor);
//            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
//            LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
//
//            if (oldBackground == null) {
//
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    ld.setCallback(drawableCallback);
//                } else {
//                    getActionBar().setBackgroundDrawable(ld);
//                }
//
//            } else {
//
//                TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
//
//                // workaround for broken ActionBarContainer drawable handling on
//                // pre-API 17 builds
//                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    td.setCallback(drawableCallback);
//                } else {
//                    getActionBar().setBackgroundDrawable(td);
//                }
//
//                td.startTransition(200);
//
//            }
//
//            oldBackground = ld;
//
//            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
////            getActionBar().setDisplayShowTitleEnabled(false);
//            getActionBar().setDisplayShowTitleEnabled(true);
//
//        }
//
//        currentColor = newColor;
//
//    }

//    public void onColorClicked(View v) {
//
//        int color = Color.parseColor(v.getTag().toString());
//        changeColor(color);
//
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("currentColor", currentColor);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        currentColor = savedInstanceState.getInt("currentColor");
//        changeColor(currentColor);
//    }
//
//    private Drawable.Callback drawableCallback = new Drawable.Callback() {
//        @Override
//        public void invalidateDrawable(Drawable who) {
//            getActionBar().setBackgroundDrawable(who);
//        }
//
//        @Override
//        public void scheduleDrawable(Drawable who, Runnable what, long when) {
//            handler.postAtTime(what, when);
//        }
//
//        @Override
//        public void unscheduleDrawable(Drawable who, Runnable what) {
//            handler.removeCallbacks(what);
//        }
//    };

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] TITLES = {"Crunch World", "Lifestyle", "Exercise", "Nutrition",
                "Learning", "Crunched"
                };

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = Fragment.instantiate(getActivity(),
                            CrunchCentralIntroFragment.class.getName());
                    break;
                case 1:
                    fragment = Fragment.instantiate(getActivity(),
                            CrunchCentralLifestyleFragment.class.getName());
                    break;

                case 2:
                    fragment = Fragment.instantiate(getActivity(),
                            CrunchCentralExerciseFragment.class.getName());
                    break;
                case 3:
                    fragment = Fragment.instantiate(getActivity(),
                            CrunchCentralNutritionFragment.class.getName());
                    break;
                case 4:
                    fragment = Fragment.instantiate(getActivity(),
                           CrunchCentralLearningFragment.class.getName());
                    break;
                case 5:
                    fragment = Fragment.instantiate(getActivity(),
                            CrunchCentralHabitsFragment.class.getName());


            }

            return fragment;


        }

    }

}
