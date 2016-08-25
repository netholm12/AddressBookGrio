package com.example.nik.addressbook;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.app.Activity;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    Intent intent;
    TextView contactID;
    ListView list;

    ContactListAdapter adapter;

    EditText inputsearch;
    ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.list);

        inputsearch = (EditText) findViewById(R.id.inputSearch);

        getListFromDatabase backgroundTask = new getListFromDatabase();
        backgroundTask.execute("returnAll");


        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contact contactData = (Contact) listview.getItemAtPosition(position);

                Intent theIntent = new Intent(getApplication(), EditContact.class);

                theIntent.putExtra("contactId", contactData.getID());

                startActivity(theIntent);
            }
        });


    }

    public void searchForContact(View view) {

        getListFromDatabase backgroundTask = new getListFromDatabase();
        backgroundTask.execute("returnSearched");


    }


    public void showAddContact(View view) {
        Intent theIntent = new Intent(getApplication(), AddNewContact.class);
        startActivity(theIntent);
    }



    public class getListFromDatabase extends AsyncTask<Object, Void, List<Contact>> {

        DatabaseConnector dbTools = new DatabaseConnector(getApplicationContext());

        String text = inputsearch.getText().toString();

        List<Contact> contactList = new ArrayList<Contact>();

        @Override
        protected List<Contact> doInBackground(Object... params) {
            String method = (String) params[0];

            if (method.equals("returnAll")) {
                contactList = dbTools.getAllContacts();
                return contactList;
            } else if (method.equals("returnSearched")) {
                Contact contact = dbTools.getContactbyName(text);
                contactList.add(contact);
            }
            return contactList;
        }

        protected void onPostExecute(List<Contact> contacts){

            //Check to see if something is returned before setting listview.
            if(contactList.size() > 0) {
                listview.setAdapter(new ContactListAdapter(getApplicationContext(), contacts));
            }

        }
    }
}
