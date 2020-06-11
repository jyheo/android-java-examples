package com.example.preferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.preferences.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final String pref_name = "pref";
    private final String pref_key_name = "name";
    private final String pref_key_address = "address";
    private final String pref_key_airplane = "airplane";
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences pref = getSharedPreferences(pref_name, MODE_PRIVATE);
        binding.editName.setText(pref.getString(pref_key_name, ""));
        binding.editAddress.setText(pref.getString(pref_key_address, ""));
        binding.switchMode.setChecked(pref.getBoolean(pref_key_airplane, false));

        /*binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });*/
        binding.button.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String str = "signature: " + pref.getString("signature", "") + "\n" +
            "reply: " + pref.getString("reply", "") + "\n" +
            "sync: " + pref.getBoolean("sync", false) + "\n" +
            "attachment: " + pref.getBoolean("attachment", false);
        binding.textSettings.setText(str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor pref = getSharedPreferences(pref_name, MODE_PRIVATE).edit();
        pref.putString(pref_key_name, binding.editName.getText().toString());
        pref.putString(pref_key_address, binding.editAddress.getText().toString());
        pref.putBoolean(pref_key_airplane, binding.switchMode.isChecked());
        pref.apply();
    }
}