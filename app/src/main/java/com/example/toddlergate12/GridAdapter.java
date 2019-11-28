package com.example.toddlergate12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GridAdapter extends BaseAdapter {

    private Integer[] petImages = {
            R.drawable.dax,
            R.drawable.icon_folder,
            R.drawable.ic_profiles_icon,
            R.drawable.icon_piano,
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
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
