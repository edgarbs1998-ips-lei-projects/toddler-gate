package com.example.toddlergate12;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager vp;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter=new PagerAdapter(this);
        vp = (ViewPager)findViewById(R.id.myViewPager);
        vp.setAdapter(adapter);


        ImageView imageView_Close = (ImageView)findViewById(R.id.imageView_Close_Icon);
        imageView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
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
