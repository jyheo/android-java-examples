package com.example.fileexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.example.fileexample.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final String filename = "appfile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnReadIn.setOnClickListener(v -> readData(new File(getFilesDir(), filename)));
        binding.btnWriteIn.setOnClickListener(v -> writeData(new File(getFilesDir(), filename)));

        if (isExternalStorageMounted()) {
            binding.btnReadExt.setOnClickListener(v -> readData(new File(getExternalFilesDir(null), filename)));
            binding.btnWriteExt.setOnClickListener(v -> writeData(new File(getExternalFilesDir(null), filename)));
        } else {
            binding.btnReadExt.setOnClickListener(v ->
                    Toast.makeText(this, "External Stroage is not mounted.", Toast.LENGTH_LONG).show());
            binding.btnWriteExt.setOnClickListener(v ->
                    Toast.makeText(this, "External Stroage is not mounted.", Toast.LENGTH_LONG).show());
        }
    }

    void readData(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            binding.editText.setText(new String(data));
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void writeData(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(binding.editText.getText().toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExternalStorageMounted() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}