package eie.android.crunch;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;



/**
 * Created by iudotong on 7/10/15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private Context mNotificationManager;
    private String context;


    @Override
    public void onReceive(Context arg0, Intent arg1) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(arg0)
                        .setSmallIcon(R.drawable.cookieimage) //Can create a place our icon here
                        .setContentTitle("Crunch")
                        .setContentText("Make sure to do your new GoodHabit!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(arg0, EachHabitPage.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(arg0);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(EachHabitPage.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(hashCode(), mBuilder.build());
    }

}