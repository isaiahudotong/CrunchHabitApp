package eie.android.crunch;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class CrunchApplication extends Application {
    private static SharedPreferences preferences;

    private static ConfigHelper configHelper;
    public static final boolean APPDEBUG = false;

    // Debugging tag for the application
    public static final String APPTAG = "AnyWall";

    // Used to pass location from MainActivity to PostActivity
    public static final String INTENT_EXTRA_LOCATION = "location";

    // Key for saving the search distance preference
    private static final String KEY_SEARCH_DISTANCE = "searchDistance";

    private static final float DEFAULT_SEARCH_DISTANCE = 250.0f;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());

        // Required - Initialize the Parse SDK
        Parse.initialize(this,
                "BnTP6MXyhpdhba9pMDw7KD9lrpraSlczO1LGI9nG",
                "GpitdT0RlxyDFBzXxNwIFZGusjJyoCSrp6GH1JAD");

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        ParseFacebookUtils.initialize(this);




        ParseObject.registerSubclass(GoodHabit.class);
        ParseObject.registerSubclass(BadHabit.class);
        ParseObject.registerSubclass(IndividualHabit.class);
        ParseObject.registerSubclass(Consequence.class);
        ParseObject.registerSubclass(Obstacle.class);
        ParseObject.registerSubclass(Reward.class);
        ParseObject.registerSubclass(Challenge.class);


        preferences = getSharedPreferences("eie.android.crunch", Context.MODE_PRIVATE);

        configHelper = new ConfigHelper();
        configHelper.fetchConfigIfNeeded();
		/*
		 * This app lets an anonymous user create and save photos of meals
		 * they've eaten. An anonymous user is a user that can be created
		 * without a username and password but still has all of the same
		 * capabilities as any other ParseUser.
		 * 
		 * After logging out, an anonymous user is abandoned, and its data is no
		 * longer accessible. In your own app, you can convert anonymous users
		 * to regular users so that data persists.
		 * R
		 * Learn more about the ParseUser class:
		 * https://www.parse.com/docs/android_guide#users
		 */
//        ParseUser.enableAutomaticUser();

		/*
		 * For more information on app security and Parse ACL:
		 * https://www.parse.com/docs/android_guide#security-recommendations
		 */
        ParseACL defaultACL = new ParseACL();

		/*
		 * If you would like all objects to be private by default, remove this
		 * line
		 */
        defaultACL.setPublicReadAccess(true);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public static float getSearchDistance() {
        return preferences.getFloat(KEY_SEARCH_DISTANCE, DEFAULT_SEARCH_DISTANCE);
    }

    public static ConfigHelper getConfigHelper() {
        return configHelper;
    }

    public static void setSearchDistance(float value) {
        preferences.edit().putFloat(KEY_SEARCH_DISTANCE, value).commit();
    }

}
