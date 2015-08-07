package eie.android.crunch;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by iudotong on 7/13/15.
 */
public class LaunchService extends IntentService {

    public LaunchService(String name) {
        super(name);

    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("eie.android.crunch");
        startActivity(LaunchIntent);
    }

}
