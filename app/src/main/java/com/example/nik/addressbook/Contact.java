package com.example.nik.addressbook;

/**
 * Created by LSmacmini on 8/19/16.
 */
import android.graphics.Bitmap;

//This class is used to store in database.
public class Contact {
    //private variables
    String _id;
    String _name_first;
    String _name_last;
    String _phone_number;
    byte[] _avatar;

    public Contact(){

    }

    // constructor
    public Contact(String id,String first_name, String last_name, String _phone_number,byte[] _avatar){
        this._id = id;
        this._name_first = first_name;
        this._name_last = last_name;
        this._phone_number = _phone_number;
        this._avatar = _avatar;
    }



    public Contact(String id){
        this._id = id;
    }


    public String getID(){
        return this._id;
    }

    public void setID(String id){
        this._id = id;
    }



    public String getFirstName(){
        return this._name_first;
    }
    public void setFirstName(String name){
        this._name_first = name;
    }

    public String getLastName(){
        return this._name_last;
    }
    public void setLastName(String name){
        this._name_last = name;
    }

    public String getPhoneNumber(){
        return this._phone_number;
    }
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    public byte[] getAvatar(){
        return this._avatar;
    }

    public void setAvatar(byte[] _avatar){
        this._avatar = _avatar;
    }

}
