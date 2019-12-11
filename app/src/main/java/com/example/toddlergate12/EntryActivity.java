package com.example.toddlergate12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import bd_related.ActivitySetPassword;
import bd_related.DataBaseHelper;

public class EntryActivity extends AppCompatActivity {

    DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDB = new DataBaseHelper(this);

        //setContentView(R.layout.activity_entry);

        //launch a different activity
        Intent launchIntent = new Intent();
        Class<?> launchActivity;
        try{

            String className = getScreenClassName();
            launchActivity = Class.forName(className);
        }catch(ClassNotFoundException e){
            launchActivity = ActivitySetPassword.class;
        }
        launchIntent.setClass(getApplicationContext(), launchActivity);
        startActivity(launchIntent);

        finish();


    }

    private String getScreenClassName(){
        Cursor res = myDB.getAllData();
        String activity;
        if(res.getCount() == 0){

            //startActivity(new Intent(this, ActivitySetPassword.class));

            activity = ActivitySetPassword.class.getName();
            return activity;

        }else{
            //startActivity(new Intent(this, MainActivity.class));
            activity = MainActivity.class.getName();
            return activity;
        }
    }

}
