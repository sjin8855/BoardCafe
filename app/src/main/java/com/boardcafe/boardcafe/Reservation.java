package com.boardcafe.boardcafe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Reservation extends AppCompatActivity {
    GridView table_view;
    static List<Table_information> table_info = new ArrayList<>();
    private DatabaseReference mTableReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference table_check;
    List<Integer> _img = new ArrayList<>();
    MyAdapter adapter;
    String google_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        table_view = (GridView) findViewById(R.id.table_list);
        google_id = getIntent().getStringExtra("googleId");

        adapter = new MyAdapter(this, R.layout.table, _img);
        table_view.setAdapter(adapter);

        table_check = mTableReference.child("Table");
        table_check.addValueEventListener(new ValueEventListener() {
            DatabaseReference re_check;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot parent : dataSnapshot.getChildren()) {
                    re_check = table_check.child(parent.getKey());
                    re_check.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Table_information ta = dataSnapshot.getValue(Table_information.class);
                            if (ta.getReservation().equals("true") && ta.getNumber().equals("3~4명")) {
                                _img.add(R.drawable.three); // 테이블이 예약 되있을 경우 이미지로 대체(3~4명테이블)
                            }
                            else if(ta.getReservation().equals("true") && ta.getNumber().equals("5~6명")) {
                                _img.add(R.drawable.five); // 테이블이 예약 되있고 5~6명 테이블
                            }
                            else if(ta.getReservation().equals("false") && ta.getNumber().equals("3~4명")){
                                _img.add(R.drawable.ic_icon_cafe); // 테이블이 예약 되있을 경우 이미지로 대체(3~4인용)
                            }
                            else{
                                _img.add(R.drawable.ic_icon_logout); // 예약 안되있을때 이미지로 대체 5~6인용
                            }
                            table_info.add(ta);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        table_view.setOnItemClickListener(new AdapterView.OnItemClickListener()

    {
        @Override
        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
        Intent _intent = new Intent(getApplicationContext(), Reservation_pay.class);

        if (table_info.get(position).getReservation().equals("true")) {
            Toast.makeText(Reservation.this, "예약하러 갑시다", Toast.LENGTH_LONG).show();
            _intent.putExtra("table_id",table_info.get(position).getTable_id());
            _intent.putExtra("reservation",table_info.get(position).getReservation());
            _intent.putExtra("google_id",google_id);

            startActivity(_intent);
        } else {
            Toast.makeText(Reservation.this, "이미 예약이 되있는 테이블입니다. ", Toast.LENGTH_LONG).show();
        }
    }
    });
}

}
