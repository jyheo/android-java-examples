package com.jyheo.fragmentexample;

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements TitlesFragment.OnTitleSelectedListener{

    boolean mbDetailsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.details) != null)
            mbDetailsVisible = true;
        else
            mbDetailsVisible = false;
    }

    public void onTitleSelected(int i, boolean restoreSaved) {
        if (mbDetailsVisible) {
            DetailsFragment detailsFragment = DetailsFragment.newInstance(i);
            getFragmentManager().beginTransaction().replace(R.id.details, detailsFragment).commit();
        } else {
            if (restoreSaved == false) {
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        }
    }
}
