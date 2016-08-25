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

public class AddNewContact extends Activity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneEditText;
    private ImageView avatarEditPhoto;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;


    Bitmap imageBitmap;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_contact);

        firstNameEditText = (EditText) findViewById(R.id.first_name);
        lastNameEditText = (EditText) findViewById(R.id.last_name) ;
        phoneEditText = (EditText) findViewById(R.id.phone_number);
        avatarEditPhoto = (ImageView) findViewById(R.id.avatar);


    }


    public  void takePicture(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {


                Bundle extras = data.getExtras();
                 imageBitmap = (Bitmap) extras.get("data");
                avatarEditPhoto.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap,400,400,false));
                // here you will get the image as bitmap

            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
            }
        }

    }




    public void addNewContact(View view) {

        Contact contact = new Contact();

        contact.setFirstName(firstNameEditText.getText().toString());
        contact.setLastName(lastNameEditText.getText().toString());

        contact.setPhoneNumber(phoneEditText.getText().toString());

        byte[] avatarArray = DbBitmapUtility.getBytes(imageBitmap);
        contact.setAvatar(avatarArray);

        DatabaseAsyncMethod databaseAsyncMethod = new DatabaseAsyncMethod(this);
        databaseAsyncMethod.execute("addNewContact", contact);

        this.callMainActivity(view);
    }


    public void callMainActivity(View view) {
        Intent theIntent = new Intent(getApplication(), MainActivity.class);
        startActivity(theIntent);
    }



}
