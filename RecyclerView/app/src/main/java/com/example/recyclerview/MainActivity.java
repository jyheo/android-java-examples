package com.example.recyclerview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recyclerview.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Email> mEmails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        makeListOfEmails();

        EmailAdapter adapter = new EmailAdapter(this, mEmails);
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setHasFixedSize(true);


        mEmails.add(0, new Email("Friend", "Hello! Do you have ..."));
        adapter.notifyItemInserted(0);
    }

    private void makeListOfEmails() {
        String[] names = getResources().getStringArray(R.array.names);
        for (String name : names)
            mEmails.add(new Email(name, "Hello, friend. Do you have any idea about ..."));
        for (String name : names)
            mEmails.add(new Email(name, "Welcome home, friend. How was your trip?"));
    }
}
