package com.jyheo.fragmentexample;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements TitlesFragment.OnTitleSelectedListener{

    boolean mDetailsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TitlesFragment titlesFragment = (TitlesFragment)getSupportFragmentManager().findFragmentById(R.id.titles);
        titlesFragment.setOnTitleSelectedListener(this);
        mDetailsEnabled = findViewById(R.id.details) != null;
    }

    public void onTitleSelected(int i, boolean restoreSaved) {

        if (mDetailsEnabled) {
            getSupportFragmentManager().beginTransaction().replace(R.id.details, new DetailsFragment(i)).commit();
        } else {
            if (!restoreSaved) {
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        }
    }
}
