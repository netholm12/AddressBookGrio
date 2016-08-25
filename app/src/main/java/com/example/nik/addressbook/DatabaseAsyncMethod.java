package com.example.nik.addressbook;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LSmacmini on 8/24/16.
 */

public class DatabaseAsyncMethod extends AsyncTask<Object, Void, Contact> {

    Context context;

    //Interface to retreive data from Async.
    public interface AsyncResponse{
        void processFinish(Contact output);
    }

    public AsyncResponse delegate = null;

    //This constructor for Returning Data
    DatabaseAsyncMethod(Context context, AsyncResponse delegate)
    {
        this.delegate = delegate;
        this.context = context;
    }
    //This constructor for Inserting data.
    DatabaseAsyncMethod(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Contact doInBackground(Object... params) {

            String method = (String) params[0];
            DatabaseConnector dbTools = new DatabaseConnector(context);
            Contact contact = new Contact();
        //Async for AddNewContact
            if (method.equals("addNewContact")) {
                 contact = (Contact) params[1];
                dbTools.insertNewContact(contact);
                contact = null;
                //Insert, return nothing.
            } else if (method.equals("editContact")) {
                contact = (Contact) params[1];
                dbTools.editContact(contact);
                contact = null;
                //Edit, return nothing
            } else if (method.equals("deleteContact")) {
                String id = (String) params[1];
                dbTools.deleteContact(id);
                contact = null;
                //Delete, return nothing
            }
            else if(method.equals("getContact")){
                String id = (String) params[1];
                contact = dbTools.getContact(id);
                //get from Database, return contact
            }

        return contact;
    }

    @Override
        protected void onPostExecute(Contact contact){
        //Send contact to outer class
        if(contact != null){
            delegate.processFinish(contact);
        }
    }

}
