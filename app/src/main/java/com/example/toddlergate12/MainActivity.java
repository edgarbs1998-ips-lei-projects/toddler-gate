package com.example.toddlergate12;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;


import bd_related.DataBaseHelper;
import custom_folder.CustomFolder;

import static bd_related.ActivitySetPassword.PASSWORD_PATTERN;

public class MainActivity extends AppCompatActivity {


    ViewPager vp;
    PagerAdapter adapter;

    ConstraintLayout button_piano;
    ImageView imageView_Camera;
    ImageView imageView_Close;
    ImageView imageView_Options;
    ImageView imageView_CustomFolder;

    Dialog dialogExitModal, dialogManagePWModal;
    EditText editText_PasswordExitModal;
    String pwExitModal;
    Button btExitModal;

    EditText editText_PasswordManagePW_Modal;
    String  pwManagePWModal;
    Button btManagePWModal;


    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finishAffinity();
            finish();
        }else{

            if (getIntent().getBooleanExtra("backgroundMap", true)) {
                Intent intent = new Intent(MainActivity.this, parents_area.localization_history.class);
                intent.putExtra("BACKGROUND", true);
                startActivity(intent);
            }

        }
        dialogExitModal = new Dialog(this);
        dialogManagePWModal = new Dialog(this);

        adapter=new PagerAdapter(this);
        vp = findViewById(R.id.myViewPager);
        vp.setAdapter(adapter);

        button_piano = findViewById(R.id.button_piano);
        button_piano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PianoActivity.class));
            }
        });

        imageView_Camera = findViewById(R.id.imageView_Camera);
        imageView_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CameraMain.class));
            }
        });

        imageView_CustomFolder = findViewById(R.id.imageView_CustomFolder);
        imageView_CustomFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CustomFolder.class));
            }
        });

        imageView_Close = findViewById(R.id.imageView_Close_Icon);
        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowExitModal();
            }
        });

        imageView_Options = findViewById(R.id.imageView_Options_Icon);
        imageView_Options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowEditPWModal();
            }
        });
    }

    public void ShowEditPWModal(){
        dialogManagePWModal.setContentView(R.layout.custom_managepw_modal);

        btManagePWModal = dialogManagePWModal.findViewById(R.id.bt_ManagePWModal);
        editText_PasswordManagePW_Modal = dialogManagePWModal.findViewById(R.id.editText_PasswordManagePW_Modal);

        btManagePWModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        btExitModal = dialogExitModal.findViewById(R.id.bt_ExitModal);
        editText_PasswordExitModal = dialogExitModal.findViewById(R.id.editText_PasswordExit_Modal);

        btExitModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper myDB = new DataBaseHelper(ctx);
                pwExitModal = editText_PasswordExitModal.getText().toString();

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
                    Cursor cursor = myDB.getAllData();
                    cursor.moveToFirst();
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

}
