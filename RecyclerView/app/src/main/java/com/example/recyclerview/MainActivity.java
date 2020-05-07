package com.example.recyclerview;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        contacts = Contact.createContactsList(20);
        ContactsAdapter adapter = new ContactsAdapter(this, contacts);
        rvContacts.setAdapter(adapter);

        // Set layout manager to position the items
        //rvContacts.setLayoutManager(new LinearLayoutManager(this));

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvContacts.setLayoutManager(gridLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new MarginItemDecoration(20);
        rvContacts.addItemDecoration(itemDecoration);

        // optimizations if all item views are of the same height and width for significantly smoother scrolling:
        rvContacts.setHasFixedSize(true);

        // Add a new contact
        contacts.add(0, new Contact("Barney", true));
        // Notify the adapter that an item was inserted at position 0
        adapter.notifyItemInserted(0);
    }
}


