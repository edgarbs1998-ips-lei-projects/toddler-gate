package com.example.toddlergate12;

import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    /*
    ViewPager vp;
    PagerAdapter adapter;
    */
    ImageView imageView_Close;
    ImageView imageView_Options;

    String[] labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);

        setTheme(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        labels = res.getStringArray(R.array.headings);
        GridView grid = (GridView)findViewById(R.id.petsGrid);
        GridAdapter gAdapter = new GridAdapter(getApplicationContext(), labels);
        grid.setAdapter(gAdapter);

        /*
        adapter=new PagerAdapter(this);
        vp = (ViewPager)findViewById(R.id.myViewPager);
        vp.setAdapter(adapter);
        */

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

    @Override
    public void onBackPressed(){
        Toast toast = Toast.makeText(this, "BabyDog GOAT", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onUserLeaveHint(){
        Toast.makeText(getApplicationContext(), "BabyDog not GOAT", Toast.LENGTH_SHORT).show();
        super.onUserLeaveHint();
    }



}
