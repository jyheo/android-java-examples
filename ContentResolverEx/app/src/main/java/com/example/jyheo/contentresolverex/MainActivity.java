package com.example.jyheo.contentresolverex;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_CODE_PERM_CALL_LOG = 1;
    String[] PERMISSIONS_CALL_LOG = {Manifest.permission.READ_CALL_LOG,
        Manifest.permission.WRITE_CALL_LOG};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            updateCallLog();
        } else {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS_CALL_LOG, REQUEST_CODE_PERM_CALL_LOG);
        }

    }

    private void updateCallLog() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED)
            return;
        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls.NUMBER}, null, null, null);
        int number_index = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        ArrayList<String> call_list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String num = cursor.getString(number_index);
            call_list.add(num);
        }
        cursor.close();

        ListView lv = (ListView)findViewById(R.id.calllog);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                call_list);
        lv.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERM_CALL_LOG) {
            if (grantResults.length != PERMISSIONS_CALL_LOG.length)
                return;
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateCallLog();
            } else {
                // permission for READ_CALL_LOG rejected!
            }
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // permission for WRITE_CALL_LOG was accepted
            }
        }
    }
}
