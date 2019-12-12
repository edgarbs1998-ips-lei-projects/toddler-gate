package com.example.toddlergate12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import bd_related.ActivitySetPassword;
import bd_related.DataBaseHelper;

import static bd_related.ActivitySetPassword.PASSWORD_PATTERN;

public class ActivityEditPassword extends AppCompatActivity {

    DataBaseHelper myDB;

    BottomNavigationView bottomNav;
    EditText editText_newPW, editText_confirmNewPW;
    String newPW, confNewPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        bottomNav = findViewById(R.id.map_bottom_nav);
        myDB = new DataBaseHelper(this);

        UpdateData();

    }

    public void UpdateData(){
        //set bottom nav "click" listener
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                editText_newPW = (EditText) findViewById(R.id.editText_newPW);
                editText_confirmNewPW = (EditText) findViewById(R.id.editText_confirmNewPW);


                newPW = editText_newPW.getText().toString();
                confNewPW = editText_confirmNewPW.getText().toString();
                Log.e("bot nav","------------carreguie para salvar-----------------");

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ActivityEditPassword.this);

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

                Log.e("bot nav", "------------ newPW -> " + newPW);
                Log.e("bot nav", "------------ confNewPW -> " + confNewPW);

                if(newPW.isEmpty() || !PASSWORD_PATTERN.matcher(newPW).matches() || confNewPW.isEmpty() || !PASSWORD_PATTERN.matcher(confNewPW).matches()){

                    // set message
                    alertDialog.setMessage("Password precisa de 1 digito e no minimo 6 caracteres");
                    // show it
                    alertDialog.show();
                    Log.e("regex", "-----------------algo nao está bem-------------------");


                }else{
                    if (confNewPW.equals(newPW)){
                        Log.e("bot nav","------------- TENTAR FAZER O UPDATE -----------------");
                        boolean isUpdated = myDB.updateData("1", confNewPW);
                        Log.e("bot nav","------------- isUpdate -> " + isUpdated);
                        //boolean isInserted = myDB.insertData(newConfPW);
                        if(isUpdated) {
                            Toast.makeText(ActivityEditPassword.this, "Password Inserted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivityEditPassword.this, MainActivity.class));
                        }else {
                            Toast.makeText(ActivityEditPassword.this, "Password Not Inserted!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ActivityEditPassword.this, "Password nao sao iguais!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;

            }
        });
    }

}
