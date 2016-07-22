package com.example.jyheo.materialtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        // Initialize contacts
        contacts = Contact.createContactsList(20);
        // Create adapter passing in the sample user data
        ContactsAdapter adapter = new ContactsAdapter(this, contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new
                MarginItemDecoration(20);
        rvContacts.addItemDecoration(itemDecoration);

        // optimizations if all item views are of the same height and width for significantly smoother scrolling:
        rvContacts.setHasFixedSize(true);

        // Add a new contact
        contacts.add(0, new Contact("Barney", true));
        // Notify the adapter that an item was inserted at position 0
        adapter.notifyItemInserted(0);
    }

    ArrayList<Contact> contacts;


}


