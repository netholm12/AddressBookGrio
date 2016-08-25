package com.example.nik.addressbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.text.Layout;
import android.widget.ArrayAdapter;

/**
 * Created by LSmacmini on 8/22/16.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.List;

public class ContactListAdapter extends BaseAdapter {


    private  List<Contact> contactList;
    private static LayoutInflater inflater;



    public ContactListAdapter(Context context, List<Contact> objects){

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contactList = objects;
    }

    @Override
    public int getCount(){
        return contactList.size();
    }
    @Override
    public Contact getItem(int position){
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if(convertView == null){
            view = inflater.inflate(R.layout.mylist, null);
        }else{
            view = convertView;
        }



        TextView txtTitle = (TextView) view.findViewById(R.id.first_name);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        TextView extraTxt = (TextView) view.findViewById(R.id.textView1);

        Contact contact = new Contact();
        contact = contactList.get(position);



        txtTitle.setText(contact.getFirstName().toString());
        extraTxt.setText(contact.getPhoneNumber().toString());
        Bitmap bmp = DbBitmapUtility.getImage(contact.getAvatar());
        imageView.setImageBitmap(bmp);

        //extraTxt.setText();
        return view;
    }



}
