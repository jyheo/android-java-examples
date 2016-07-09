package com.jyheo.activityintent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "ActivityLifeCycle";
    private static final int request_code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Log.i(TAG, getLocalClassName() + ".onCreate");

        Button btn = (Button)findViewById(R.id.buttonSecondActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        btn = (Button)findViewById(R.id.buttonDialActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent implicit_intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:114"));
                startActivity(implicit_intent);
            }
        });

        btn = (Button)findViewById(R.id.buttonThirdActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("UserDefinedExtra", "Hello");
                startActivityForResult(intent, request_code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != request_code || data == null)
            return;
        String msg = data.getStringExtra("ResultString");
        Log.i(TAG, "ActivityResult:" + resultCode + " " + msg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, getLocalClassName() + ".onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, getLocalClassName() + ".onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, getLocalClassName() + ".onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, getLocalClassName() + ".onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, getLocalClassName() + ".onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getLocalClassName() + ".onDestroy");
    }
}
