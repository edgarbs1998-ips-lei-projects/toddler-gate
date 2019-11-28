package com.example.toddlergate12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private Integer[] petImages = {
            R.drawable.icon_camera,
            R.drawable.ic_stopwatch_icon,
            R.drawable.ic_launcher_foreground
    };

    private String[] imageLabels;
    private LayoutInflater thisInflater;

    public GridAdapter(Context context, String[] labs) {
        this.thisInflater = LayoutInflater.from(context);
        this.imageLabels = labs;
    }

    @Override
    public int getCount() {
        return petImages.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = thisInflater.inflate(R.layout.grid_item, parent, false);
            TextView textHeading = (TextView)convertView.findViewById(R.id.petHeading);
            ImageView thumbnailImage = (ImageView)convertView.findViewById(R.id.petImage);
            textHeading.setText(imageLabels[position]);
            thumbnailImage.setImageResource(petImages[position]);
        }
        return convertView;
    }
}
