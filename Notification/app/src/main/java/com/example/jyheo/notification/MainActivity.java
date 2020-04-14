package com.example.jyheo.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    public final static int MY_NOTIFICATION_ID = 1;
    final private String CHANNEL_ID = "default";
    private NotificationManagerCompat mNotificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManagerCompat = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // API 26
            mNotificationManagerCompat.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, "default channel",
                            NotificationManager.IMPORTANCE_DEFAULT));
        }
    }

    public void onSimpleNotification(View v) {
        if (!mNotificationManagerCompat.areNotificationsEnabled())
            return;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        mBuilder.setContentText(getResources().getString(R.string.notif_body));

        // MY_NOTIFICATION_ID allows you to update the notification later on.
        mNotificationManagerCompat.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onActionNotificationSpecial(View v) {
        if (!mNotificationManagerCompat.areNotificationsEnabled())
            return;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        mBuilder.setContentText(getResources().getString(R.string.notif_body));

        // pendingIntent
        Intent intent = new Intent(this, TempActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pIntent);
        mBuilder.setAutoCancel(true);

        mNotificationManagerCompat.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onActionNotificationRegular(View v) {
        if (!mNotificationManagerCompat.areNotificationsEnabled())
            return;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        mBuilder.setContentText(getResources().getString(R.string.notif_body));

        // pendingIntent
        Intent intent = new Intent(this, SecondActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SecondActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pIntent);
        mBuilder.setAutoCancel(true);

        mNotificationManagerCompat.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onActionButtonNotification(View v) {
        if (!mNotificationManagerCompat.areNotificationsEnabled())
            return;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        mBuilder.setContentText(getResources().getString(R.string.notif_body));
        mBuilder.setAutoCancel(true);

        Intent callintent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1234"));
        PendingIntent pIntent = PendingIntent.getActivity(this,
                0, callintent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.addAction(R.drawable.ic_phone_black_24dp, "Call", pIntent);

        mNotificationManagerCompat.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onExpandableNotification(View v) {
        if (!mNotificationManagerCompat.areNotificationsEnabled())
            return;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        mBuilder.setContentText(getResources().getString(R.string.notif_body));
        mBuilder.setAutoCancel(true);

        //NotificationCompat.BigTextStyle btStyle =
        //        new NotificationCompat.BigTextStyle().bigText(getResources().getString(R.string.long_notification_body));
        //mBuilder.setStyle(btStyle);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);
        NotificationCompat.BigPictureStyle bpStyle =
                new NotificationCompat.BigPictureStyle().bigPicture(largeIcon);
        mBuilder.setStyle(bpStyle);

        mNotificationManagerCompat.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onProgressNotification(View v) {
        if (!mNotificationManagerCompat.areNotificationsEnabled())
            return;
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle("Download");
        mBuilder.setContentText("Download in progress");
        mBuilder.setAutoCancel(true);

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        for (incr = 0; incr <= 100; incr += 5) {
                            mBuilder.setProgress(100, incr, false);
                            mNotificationManagerCompat.notify(MY_NOTIFICATION_ID, mBuilder.build());
                            try {
                                Thread.sleep(5 * 1000); // Sleep for 5 seconds
                            } catch (InterruptedException e) {
                                Log.d("NOTI", "sleep failure");
                            }
                        }
                        mBuilder.setContentText("Download complete")
                                .setProgress(0, 0, false); // Removes the progress bar
                        mNotificationManagerCompat.notify(MY_NOTIFICATION_ID, mBuilder.build());
                    }
                }
        ).start();
    }
}

