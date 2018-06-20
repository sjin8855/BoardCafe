package com.boardcafe.boardcafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<Integer> img;
    LayoutInflater inf;

    MyAdapter(Context context , int layout , List<Integer> img){
        this.context = context;
        this.layout = layout;
        this.img = img;
        inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return img.size();
    }

    @Override
    public Object getItem(int position) {
        return img.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inf.inflate(layout,null);
        }
        ImageView iv = (ImageView)convertView.findViewById(R.id._table);
        iv.setImageResource(img.get(position));
        return convertView;
    }
}