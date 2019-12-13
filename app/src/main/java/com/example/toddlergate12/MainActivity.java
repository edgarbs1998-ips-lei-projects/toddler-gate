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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;


import bd_related.DataBaseHelper;

import static bd_related.ActivitySetPassword.PASSWORD_PATTERN;

public class MainActivity extends AppCompatActivity {


    ViewPager vp;
    PagerAdapter adapter;

    ConstraintLayout button_piano;
    ImageView imageView_Camera;
    ImageView imageView_Close;
    ImageView imageView_Options;

    Dialog dialogExitModal, dialogManagePWModal;
    EditText editText_PasswordExitModal;
    String pwExitModal;
    Button btExitModal;

    EditText editText_PasswordManagePW_Modal;
    String  pwManagePWModal;
    Button btManagePWModal;


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




        dialogExitModal = new Dialog(this);
        dialogManagePWModal = new Dialog(this);


        adapter=new PagerAdapter(this);
        vp = (ViewPager)findViewById(R.id.myViewPager);
        vp.setAdapter(adapter);

        button_piano = (ConstraintLayout)findViewById(R.id.button_piano);
        button_piano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PianoActivity.class));
            }
        });

        imageView_Camera = (ImageView)findViewById(R.id.imageView_Camera);
        imageView_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CameraMain.class));
            }
        });

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
        dialogManagePWModal.setContentView(R.layout.custom_managepw_modal);

        btManagePWModal = (Button) dialogManagePWModal.findViewById(R.id.bt_ManagePWModal);
        editText_PasswordManagePW_Modal = (EditText) dialogManagePWModal.findViewById(R.id.editText_PasswordManagePW_Modal);

        btManagePWModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("modal", "------------------Carreguei no botao para editar password ---------------");
                DataBaseHelper myDB = new DataBaseHelper(ctx);

                pwManagePWModal = editText_PasswordManagePW_Modal.getText().toString();

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

                if(!pwManagePWModal.isEmpty() && PASSWORD_PATTERN.matcher(pwManagePWModal).matches()){
                    Cursor cursor = myDB.getAllData();
                    cursor.moveToFirst();
                    if(pwManagePWModal.equals(cursor.getString(1))){
                        dialogManagePWModal.dismiss();
                        startActivity(new Intent(MainActivity.this, parents_area.parents_area_main.class));
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

        dialogManagePWModal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogManagePWModal.show();
    }


    public void ShowExitModal(){
        dialogExitModal.setContentView(R.layout.custom_exit_modal);
        btExitModal = (Button) dialogExitModal.findViewById(R.id.bt_ExitModal);
        editText_PasswordExitModal = (EditText) dialogExitModal.findViewById(R.id.editText_PasswordExit_Modal);

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

                if(!pwExitModal.isEmpty() && PASSWORD_PATTERN.matcher(pwExitModal).matches()){
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

                        dialogExitModal.dismiss();
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
        dialogExitModal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogExitModal.show();
    }

    //botao de back no tlm
    @Override
    public void onBackPressed(){
        ShowExitModal();
    }
//    //reconhece ao carregar os 3 botoes do tlm
//    @Override
//    protected void onUserLeaveHint(){
//        Toast.makeText(getApplicationContext(), "BabyDog not GOAT", Toast.LENGTH_SHORT).show();
//        super.onUserLeaveHint();
//    }

}
