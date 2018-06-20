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
    Reservation User_reservation;
    static List<Table_information> table_info = new ArrayList<>();
    private DatabaseReference mTableReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference table_check;
    List<Integer> _img = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        table_view = (GridView) findViewById(R.id.table_list);
        User_reservation = new Reservation();


        adapter = new MyAdapter(this, R.layout.table, _img);
        table_view.setAdapter(adapter);

        table_check = mTableReference.child("Table");
        table_check.addListenerForSingleValueEvent(new ValueEventListener() {
            DatabaseReference re_check;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot parent : dataSnapshot.getChildren()) {
                    re_check = table_check.child(parent.getKey());
                    re_check.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Table_information ta = dataSnapshot.getValue(Table_information.class);
                            if (ta.getReservation().equals("true")) {
                                _img.add(R.drawable.ic_icon_cafe); // 테이블이 예약 되있을 경우 이미지로 대체
                                Log.i("ima", String.valueOf(_img.get(0)));
                            } else {
                                _img.add(R.drawable.ic_icon_logout); // 예약 안되있을때 이미지로 대체
                                Log.i("ima", String.valueOf(_img.get(0)));
                            }
                            table_info.add(ta);
                            adapter.notifyDataSetChanged();
                            Log.i("table", table_info.get(0).getTable_id());
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
            Toast.makeText(Reservation.this, "예약하러 갑시다", Toast.LENGTH_SHORT).show();
            _intent.putExtra("table_id",String.valueOf(position+1));
            startActivity(_intent);
        } else {
            Toast.makeText(Reservation.this, "이미 예약이 되있는 테이블입니다. ", Toast.LENGTH_LONG).show();
        }
    }
    });
}

}
