package com.boardcafe.boardcafe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {

    ArrayList<BoardItem> items = new ArrayList();
    Context _context;

    BoardAdapter(Context context){
        this._context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(BoardItem item){
        items.add(item);
    }

    @Override
    public Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        BoardItemView view = null;

        if(convertView == null){
            view = new BoardItemView(_context.getApplicationContext());
        }
        else{
            view = (BoardItemView) convertView;
        }

        BoardItem item = items.get(position);
        view.setId(item.getId());
        view.setContents(item.getContents());

        return view;
    }
}
