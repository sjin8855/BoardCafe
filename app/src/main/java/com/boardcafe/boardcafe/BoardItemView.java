package com.boardcafe.boardcafe;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BoardItemView extends LinearLayout{
    TextView _id;
    TextView _contents;

    public BoardItemView(Context context){
        super(context);
        init(context);
    }

    public BoardItemView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.board_item,this, true);

        _id = (TextView)findViewById(R.id._id);
        _contents = (TextView)findViewById(R.id._contents);
    }

    public void setId(String id) {
        _id.setText(id);
    }

    public void setContents(String contents){
        _contents.setText(contents);
    }
}
