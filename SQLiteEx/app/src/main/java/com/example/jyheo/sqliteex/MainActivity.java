package com.example.jyheo.sqliteex;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private MyDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);

        //ListView lv = (ListView)findViewById(R.id.listview);
        //lv.setOnItemClickListener(this);

        //helper = new MyDBHelper(this);
        //listUpdate();

        //helper.getWritableDatabase().execSQL("INSERT INTO mydata (_id, name, number)\n"+
         //       "VALUES (NULL, 'TEST', '000')");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_action:
                startItemActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startItemActivity();
    }

    private void listUpdate() {
        String sql = "Select * FROM mydata";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.item, cursor, new String[]{"name", "number"},
                new int[]{R.id.textview1, R.id.textview2}, 0);
        ListView lv = (ListView)findViewById(R.id.listview);
        lv.setAdapter(adapter);
    }

    private void startItemActivity() {
        Intent intent = new Intent(this, ItemActivity.class);
        startActivity(intent);
    }
}
