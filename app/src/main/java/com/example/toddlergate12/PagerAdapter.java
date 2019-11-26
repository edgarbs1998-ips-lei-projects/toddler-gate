package com.example.toddlergate12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    Context c;
    PagerAdapter(Context c)
    {
        this.c = c;
    }
    private int[] images = {R.drawable.bg1_tentativa,R.drawable.bg4_recortado,R.drawable.bg2_tentativa,R.drawable.bg3_tentativa,R.drawable.photo};

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_pager_layout,container,false);
        ImageView iv = (ImageView)v.findViewById(R.id.imageView);
        iv.setImageResource(images[position]);
        container.addView(v);
        return v;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
