package com.example.toddlergate12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import bd_related.ActivitySetPassword;

public class MainActivity extends AppCompatActivity {

    /*
    ViewPager vp;
    PagerAdapter adapter;
    */
    ConstraintLayout button_piano;
    ImageView imageView_Close;
    ImageView imageView_Options;

    String[] labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        /*
        adapter=new PagerAdapter(this);
        vp = (ViewPager)findViewById(R.id.myViewPager);
        vp.setAdapter(adapter);
        */

        button_piano = (ConstraintLayout)findViewById(R.id.button_piano);
        button_piano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PianoActivity.class));
            }
        });

        imageView_Close = (ImageView)findViewById(R.id.imageView_Close_Icon);
        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        imageView_Options = (ImageView)findViewById(R.id.imageView_Options_Icon);
        imageView_Options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivitySetPassword.class));
            }
        });
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
