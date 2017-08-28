package com.jyheo.debugthislab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnOK = (Button)findViewById(R.id.OK);
        setContentView(R.layout.activity_main);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etName = (EditText)findViewById(R.id.Name);
                EditText etPhone = (EditText)findViewById(R.id.Phone);
                if (etName.getText().toString() == "john") {
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
