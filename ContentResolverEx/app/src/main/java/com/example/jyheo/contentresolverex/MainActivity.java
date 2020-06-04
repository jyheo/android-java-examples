package com.example.jyheo.contentresolverex;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.jyheo.contentresolverex.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_CODE_PERM_READ_CALL_LOG = 1;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(v -> updateCallLog());
    }

    private void updateCallLog() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CODE_PERM_READ_CALL_LOG);
            return;
        }

        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,
                new String[]{CallLog.Calls._ID, CallLog.Calls.NUMBER},  // _ID is required for cursoradapter
                null, null, null);
        binding.calllog.setAdapter(new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, cursor,
                new String[]{CallLog.Calls.NUMBER}, new int[]{android.R.id.text1}, 0));

        /* // by CursorLoader
         LoaderManager.getInstance(this).initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                return new CursorLoader(MainActivity.this, CallLog.Calls.CONTENT_URI,
                        new String[]{CallLog.Calls._ID, CallLog.Calls.NUMBER},
                        null, null, null);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
                binding.calllog.setAdapter(new SimpleCursorAdapter(MainActivity.this,
                        android.R.layout.simple_list_item_1, data,
                        new String[]{CallLog.Calls.NUMBER}, new int[]{android.R.id.text1}, 0));
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {
                binding.calllog.setAdapter(null);
            }
        });*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERM_READ_CALL_LOG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateCallLog();
            } else {
                // permission for READ_CALL_LOG rejected!
                new AlertDialog.Builder(this).setTitle("Permission!")
                        .setMessage("READ_CALL_LOG permission is required to list the call log.\nPress OK to grant the permission.")
                        .setPositiveButton("OK", ((dialog, which) -> ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CODE_PERM_READ_CALL_LOG)))
                        .setNegativeButton("Cancel", null)
                        .create().show();
            }
        }
    }
}
