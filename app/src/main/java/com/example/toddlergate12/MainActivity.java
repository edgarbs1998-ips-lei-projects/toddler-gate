package com.example.toddlergate12;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import bd_related.DataBaseHelper;

import static bd_related.ActivitySetPassword.PASSWORD_PATTERN;

public class MainActivity extends AppCompatActivity {

    /*
    ViewPager vp;
    PagerAdapter adapter;
    */
    ImageView imageView_Close;
    ImageView imageView_Options;

    Dialog dialog;
    EditText editText_PasswordExitModal;
    String pwExitModal;
    Button btExitModal;


    Context ctx = this;

    String[] labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);




        dialog = new Dialog(this);

        /*
        adapter=new PagerAdapter(this);
        vp = (ViewPager)findViewById(R.id.myViewPager);
        vp.setAdapter(adapter);
        */

        imageView_Close = (ImageView)findViewById(R.id.imageView_Close_Icon);
        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("modal", "--------------------CARREGUEI NO BOTAO PARA A BRIR O MODAL --------------------");

                ShowExitModal();

            }
        });

        imageView_Options = (ImageView)findViewById(R.id.imageView_Options_Icon);
        imageView_Options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, ActivityEditPassword.class));
                Log.e("modal", "--------------------CARREGUEI NO BOTAO PARA A BRIR O MODAL --------------------");
                ShowEditPWModal();
            }
        });
    }

    public void ShowEditPWModal(){
        dialog.setContentView(R.layout.custom_managepw_modal);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    public void ShowExitModal(){
        dialog.setContentView(R.layout.custom_exit_modal);
        btExitModal = (Button) dialog.findViewById(R.id.bt_ExitModal);
        editText_PasswordExitModal = (EditText) dialog.findViewById(R.id.editText_PasswordExitModal);

        btExitModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("modal", "Carreguei no botao para sair");
                DataBaseHelper myDB = new DataBaseHelper(ctx);

                pwExitModal = editText_PasswordExitModal.getText().toString();
                Log.e("modal", "------------- pwExitModal -> " + pwExitModal);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);

                // set dialog message
                alertDialogBuilder
                        .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                if(pwExitModal.isEmpty() || !PASSWORD_PATTERN.matcher(pwExitModal).matches()){
                    Log.e("bd", "------------ myDB.getAllData -> " + myDB.getAllData());
                    Log.e("bd", "------------ myDB.getAllData.count() -> " + myDB.getAllData().getCount());



                    Cursor cursor = myDB.getAllData();
                    cursor.moveToFirst();
                    Log.e("bd","------------------- cursor.getString(0) -> " + cursor.getString(0));
                    Log.e("bd","------------------- cursor.getString(1) -> " + cursor.getString(1));
                    //boolean status = false;
                    //String pw = "";
                    //while (cursor.moveToNext()){



                    if(pwExitModal.equals(cursor.getString(1))){

                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }else{
                        // set message
                        alertDialog.setMessage("Password incorreta");
                        // show it
                        alertDialog.show();
                    }
                }else{

                    // set message
                    alertDialog.setMessage("Password inválida");
                    // show it
                    alertDialog.show();

                }








            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    //botao de back no tlm
    @Override
    public void onBackPressed(){
        Toast toast = Toast.makeText(this, "BabyDog GOAT", Toast.LENGTH_SHORT);
        toast.show();
    }
    //reconhece ao carregar os 3 botoes do tlm
    @Override
    protected void onUserLeaveHint(){
        Toast.makeText(getApplicationContext(), "BabyDog not GOAT", Toast.LENGTH_SHORT).show();
        super.onUserLeaveHint();
    }



}
