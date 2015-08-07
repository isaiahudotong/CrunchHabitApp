package eie.android.crunch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.internal.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by iudotong on 7/17/15.
 */

public class DrawerItemClickListener extends CrunchMainActivity implements ListViewCompat.OnItemClickListener {

    Context context;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long idNumber) {
        selectItem(position);
    }

    private void selectItem(int position) {

        switch (position) {

            case 0:

                Intent crunchIntent = new Intent(getBaseContext(),CrunchMainActivity.class);
                startActivity(crunchIntent);
                break;
            case 1:
                Intent habitMakingIntent = new Intent(getApplicationContext(), NewIndividualHabitActivity.class);
                context.startActivity(habitMakingIntent);
                break;

            case 2:
                Intent habitListIntent = new Intent(getApplicationContext(), HabitListActivity.class);
                context.startActivity(habitListIntent);
                break;
            case 3:
                Intent challengeIntent = new Intent(this, ChallengePage.class);
                startActivity(challengeIntent);
                break;
            case 4:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            default:
                break;


        }

    }
}