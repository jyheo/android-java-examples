package com.example.jyheo.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public final static int MY_NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSimpleNotification(View v) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        mBuilder.setContentText(getResources().getString(R.string.notif_body));

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // MY_NOTIFICATION_ID allows you to update the notification later on.
        mNotificationManager.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onActionNotificationSpecial(View v) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
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

        ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onActionNotificationRegular(View v) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
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

        ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onActionButtonNotification(View v) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        mBuilder.setContentText(getResources().getString(R.string.notif_body));
        mBuilder.setAutoCancel(true);

        Intent callintent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1234"));
        PendingIntent pIntent = PendingIntent.getActivity(this,
                0, callintent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.addAction(R.drawable.ic_phone_black_24dp, "Call", pIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onExpandableNotification(View v) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
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

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onProgressNotification(View v) {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle("Download");
        mBuilder.setContentText("Download in progress");
        mBuilder.setAutoCancel(true);

        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        for (incr = 0; incr <= 100; incr += 5) {
                            mBuilder.setProgress(100, incr, false);
                            mNotificationManager.notify(MY_NOTIFICATION_ID, mBuilder.build());
                            try {
                                Thread.sleep(5 * 1000); // Sleep for 5 seconds
                            } catch (InterruptedException e) {
                                Log.d("NOTI", "sleep failure");
                            }
                        }
                        mBuilder.setContentText("Download complete")
                                .setProgress(0, 0, false); // Removes the progress bar
                        mNotificationManager.notify(MY_NOTIFICATION_ID, mBuilder.build());
                    }
                }
        ).start();
    }
}

