package com.spencer.wille.recycleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        // Lookup the recyclerview in activity layout
        Button button = (Button) findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonPress();
            }
        });

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        // Initialize contacts
        contacts = new ArrayList<Contact>();

        Contact contact1 = new Contact("Will", "Hi");
        contacts.add(contact1);
        Contact contact2 = new Contact("Owen", "ONLY COMMUNISTS SAY HI");
        contacts.add(contact2);
        Contact contact3 = new Contact("Mihir", "Spooky Meme");
        contacts.add(contact3);
        Contact contact4 = new Contact("Adam", "AIME FIFA FOR LIFE");
        contacts.add(contact4);
        Contact contact5 = new Contact("Mihir", "Sweet Meme");
        contacts.add(contact5);
        Contact contact6 = new Contact("Mihir", "Will, are you a bad player?");
        contacts.add(contact6);
        Contact contact7 = new Contact("Will", "?????");
        contacts.add(contact7);
        for(int x = 0; x < 5; x++){
            contacts.add(contact6);
            contacts.add(contact7);
        }


        // Create adapter passing in the sample user data
        ContactsAdapter adapter = new ContactsAdapter(this, contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }
    public void buttonPress(){
        EditText nameText = (EditText) findViewById(R.id.nameInput);
        EditText messageText = (EditText) findViewById(R.id.messageInput);
        contacts.add(0, new Contact(nameText.getText().toString(), messageText.getText().toString()));
        nameText.setText("");
        messageText.setText("");


        ContactsAdapter adapter = new ContactsAdapter(this, contacts);
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }
}
