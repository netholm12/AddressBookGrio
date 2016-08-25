package com.example.nik.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by LSmacmini on 8/22/16.
 */


//This class is for the EditContact functionality and screen.
public class EditContact extends Activity  {

    EditText firstName;
    EditText phoneNumber;
    EditText lastName;
    ImageView avatar;


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    Bitmap imageBitmap;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);

        phoneNumber = (EditText) findViewById(R.id.phone_number);
        avatar = (ImageView) findViewById(R.id.avatar);

        Intent theIntent = getIntent();

        String contactId = theIntent.getStringExtra("contactId");


        //GetContact from Database
        //set parameters to be filled in
        DatabaseAsyncMethod task = (DatabaseAsyncMethod) new DatabaseAsyncMethod(getBaseContext(), new DatabaseAsyncMethod.AsyncResponse() {
            @Override
            public void processFinish(Contact output) {
                if(output != null) {//Check to make sure Contact is returned.
                    firstName.setText(output.getFirstName());
                    lastName.setText(output.getLastName());
                    phoneNumber.setText(output.getPhoneNumber());
                    Bitmap temp = DbBitmapUtility.getImage(output.getAvatar());
                    avatar.setImageBitmap(Bitmap.createScaledBitmap(temp,400,400,false));


                }
            }
        }).execute("getContact",contactId);
    }

    public  void editPicture(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Take the Picture!
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                //Get data from editPicture
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                avatar.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap,400,400,false));
                // save data to Bitmap, Resize to fit screen.
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
            }
        }

    }
    public void editContact(View view){

        Contact contact = new Contact();

        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        avatar = (ImageView) findViewById(R.id.avatar);

        Intent theIntent = getIntent();

        String contactId = theIntent.getStringExtra("contactId");

        contact.setID((contactId));
        contact.setFirstName(firstName.getText().toString());
        contact.setLastName(lastName.getText().toString());
        contact.setPhoneNumber(phoneNumber.getText().toString());

        byte[] avatarArray = DbBitmapUtility.getBytes(imageBitmap);
        contact.setAvatar(avatarArray);

        //Above get all new data from input Fields


        //Put new data into database.
        DatabaseAsyncMethod databaseAsyncMethod = new DatabaseAsyncMethod(this);
        databaseAsyncMethod.execute("editContact", contact);


        this.callMainActivityView(view);
    }

    public void deleteContact(View view){
        Intent theIntent = getIntent();
        String contactId = theIntent.getStringExtra("contactId");

        DatabaseAsyncMethod databaseAsyncMethod = new DatabaseAsyncMethod(this);
        databaseAsyncMethod.execute("deleteContact", contactId);

        this.callMainActivityView(view);
    }

    public void callMainActivityView(View view){
        Intent objIntent = new Intent(getApplication(), MainActivity.class);
        startActivity(objIntent);
    }

}
