package com.example.jyheo.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
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

    public void onActionNotification(View v) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        mBuilder.setContentText(getResources().getString(R.string.notif_body));

        // pendingIntent
        Intent intent = new Intent(this, TempActivity.class);
        int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
        PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);
        mBuilder.setContentIntent(pIntent);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onActionButtonNotification(View v) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        mBuilder.setContentText(getResources().getString(R.string.notif_body));
        mBuilder.setAutoCancel(true);

        // pendingIntent
        Intent intent = new Intent(this, TempActivity.class);
        int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
        PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);
        mBuilder.setContentIntent(pIntent);

        Intent callintent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1234"));
        requestID++; //unique requestID to differentiate between various notification with same NotifId
        flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
        pIntent = PendingIntent.getActivity(this, requestID, callintent, flags);
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
}

