package eie.android.crunch;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;

/**
 * Created by elliekang on 7/24/15.
 */
public class LaunchingActivity extends Activity {
    AnimationDrawable cookieAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        /*// Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, HabitListActivity.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, WelcomeActivity.class));
        }*/
        setContentView(R.layout.activity_launching);

        /*ImageView cookieImage = (ImageView)findViewById(R.id.imageView3);
        cookieImage.setBackgroundResource(R.drawable.cookie);
        cookieAnimation = (AnimationDrawable) cookieImage.getBackground();*/


        ActionBar actionBar = getActionBar();
        actionBar.hide();

        final Button started = (Button) findViewById(R.id.button);
        started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DispatchActivity.class);
                startActivity(i);
            }
        });
    }

    /*public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            cookieAnimation.start();
            return true;
        }
        return super.onTouchEvent(event);
    }*/


}
