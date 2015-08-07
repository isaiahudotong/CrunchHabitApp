package eie.android.crunch;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by iudotong on 7/27/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context _context;
    public static int totalPage=5;
    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context=context;

    }
    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        switch(position){
            case 0:
                f=Identify_BadHabit.newInstance(_context);
                break;
            case 1:
                f=Identify_Reward.newInstance(_context);
                break;
            case 2:
                f=Identify_Cues.newInstance(_context);
                break;
            case 3:
                f=Identify_Plan.newInstance(_context);
                break;
            case 4:
                f=Routine_Summary.newInstance(_context);
                break;

        }
        return f;
    }
    @Override
    public int getCount() {
        return totalPage;
    }

}