package com.boardcafe.boardcafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Reservation_pay extends AppCompatActivity {
    Intent _intent;
    String table_id;
    TextView _table_id,calender_text,time_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        _intent = getIntent();
        table_id = _intent.getStringExtra("table_id");
        _table_id = (TextView)findViewById(R.id.table_id);
        calender_text = (TextView)findViewById(R.id.calender_text);
        time_text = (TextView)findViewById(R.id.time_text);
        _table_id.setText(table_id + "번 테이블 예약 설정");

    }
}
