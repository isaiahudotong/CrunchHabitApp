package eie.android.crunch;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by elliekang on 7/23/15.
 */
public class IntroductionActivity extends Activity {
    @Override
    public void onCreate(Bundle onSaveInstanceState) {
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.activity_step);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        Button start = (Button)findViewById(R.id.button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NewIndividualHabitActivity.class);
                startActivity(i);
            }
        });
    }
}
