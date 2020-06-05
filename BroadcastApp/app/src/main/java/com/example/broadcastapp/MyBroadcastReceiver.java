package com.example.broadcastapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String log = "Action: " + intent.getAction() + "\n" +
                "URI: " + intent.toUri(Intent.URI_INTENT_SCHEME);
        Log.d(TAG, log);
    }
}
