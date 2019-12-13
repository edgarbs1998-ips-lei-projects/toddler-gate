package com.example.toddlergate12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

                if(newPW.isEmpty() || !PASSWORD_PATTERN.matcher(newPW).matches() || confNewPW.isEmpty() || !PASSWORD_PATTERN.matcher(confNewPW).matches()){
                    // set message
                    alertDialog.setMessage("Password precisa de 1 digito e no minimo 6 caracteres");
                    // show it
                    alertDialog.show();
                }else{
                    if (confNewPW.equals(newPW)){
                        boolean isUpdated = myDB.updateData("1", confNewPW);
                        if(isUpdated) {
                            Toast.makeText(ActivityEditPassword.this, "Password Inserted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivityEditPassword.this, MainActivity.class));
                        }else {
                            Toast.makeText(ActivityEditPassword.this, "Password Not Inserted!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ActivityEditPassword.this, "Password do not match!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }
}
