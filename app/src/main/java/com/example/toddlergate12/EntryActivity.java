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
        Log.e("EA", "--------- criei a bd (supostamente) -------------");
        //setContentView(R.layout.activity_entry);
        Log.e("EA","------------ entrou no EntryACtivity --------------");
        Log.e("EA", "----------- myDB.getAllData()2 -> " + myDB.getAllData().toString());
        //launch a different activity
        Intent launchIntent = new Intent();
        Class<?> launchActivity;
        try{
            Log.e("EA","---------------- entrou no try ----------------");
            String className = getScreenClassName();
            launchActivity = Class.forName(className);
            Log.e("EA","----------------- saiu do try ------------------");
        }catch(ClassNotFoundException e){
            Log.e("EA","------------- entrou no catch ----------------");
            launchActivity = ActivitySetPassword.class;
        }
        launchIntent.setClass(getApplicationContext(), launchActivity);
        startActivity(launchIntent);

        finish();


    }

    private String getScreenClassName(){
        Log.e("EA", "----------- myDB.getAllData()1 -> " + myDB.getAllData().toString());
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

    public void ViewAllData(){

        /*
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("Id: " + res.getString(0) + "\n");
            buffer.append("Password: " + res.getString(1) + "\n\n");
        }

        show all data
        showMessage("Data", buffer.toString());

         */
    }

    /*
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
    }

     */
}
