package com.example.nik.addressbook;

/**
 * Created by LSmacmini on 8/19/16.
 */
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.AsyncTask;

public class DatabaseConnector extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contactsManager";

    private static final int DATABASE_VERSION = 1;


        public DatabaseConnector(Context context){
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            String query = "CREATE TABLE contacts(contactId INTEGER PRIMARY KEY,firstName TEXT, " +
                    "lastName TEXT, phoneNumber TEXT, avatar BLOB)";
            db.execSQL(query);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            String query = "DROP TABLE IF EXISTS " + DATABASE_NAME;
            db.execSQL(query);
            onCreate(db);
        }




    void insertNewContact(Contact contact) throws SQLiteException{

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("firstName", contact.getFirstName());
        values.put("lastName", contact.getLastName());
        values.put("phoneNumber", contact.getPhoneNumber());
        values.put("avatar", contact.getAvatar());

        db.insert("contacts", null, values);
        db.close();
    }


    // Getting single contact
    Contact getContact(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Contact contact = new Contact();
        String selectQuery = "SELECT * FROM contacts WHERE contactId='" + id + "'";

        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            do {
                contact = new Contact(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4));

            } while (cursor.moveToNext());
        }

        db.close();
        return contact;
    }

    Contact getContactbyName(String firstName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Contact contact = new Contact();
        String selectQuery = "SELECT * FROM contacts WHERE firstName='" + firstName + "'";

        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            do {
                contact = new Contact(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4));

            } while (cursor.moveToNext());
        }

        db.close();
        return contact;
    }


    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM contacts";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID((cursor.getString(0)));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setPhoneNumber(cursor.getString(3));
                contact.setAvatar(cursor.getBlob(4));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return contactList;
    }




    // Updating single contact
    public int editContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("firstName", contact.getFirstName());
        values.put("lastName", contact.getLastName());
        values.put("phoneNumber", contact.getPhoneNumber());
        values.put("avatar", contact.getAvatar());
        return db.update("contacts", values, "contactId = ?",
                new String[] { String.valueOf(contact.getID()) });
    }



    // Deleting single contact
    public void deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String delete = "DELETE FROM contacts WHERE contactId='" + id + "'";

        db.execSQL(delete);

        db.close();
    }



}

