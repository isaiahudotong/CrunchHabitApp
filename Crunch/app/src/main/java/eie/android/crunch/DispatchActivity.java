package eie.android.crunch;

import android.os.Bundle;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.facebook.FacebookSdk;
import com.parse.ParseFacebookUtils;
import com.parse.ui.ParseLoginDispatchActivity;


/**
 * Created by elliekang on 7/17/15.
 */
public class DispatchActivity extends ParseLoginDispatchActivity {

    // app forces people to go through habit creation flow if they've never made a habit before!
    

    //method used for returning members who's logged in
    @Override
    protected Class<?> getTargetClass() {
        Log.d("test", "here");

        return EachHabitPage.class;
//
//        ParseObject habit = (ParseObject) ParseUser.getCurrentUser().get("current_habit");
//
//        if (habit != null) {
//            return EachHabitPage.class;
//        } else {
//            return NewIndividualHabitActivity.class;
//        }

    }




}
