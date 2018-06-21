
package com.boardcafe.boardcafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class CafeActivity extends AppCompatActivity {

    private DatabaseReference mCafeReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference cafe_check;
    Button game, menu, reservation;
    AlertDialog.Builder game_info, menu_info;
    ArrayAdapter game_adapter, menu_adapter;
    List<String> game_list = new ArrayList<>();
    List<String> menu_list = new ArrayList<>();
    ListView list;
    static String list_check = "";
    String google;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);
        game = (Button) findViewById(R.id.game);
        menu = (Button) findViewById(R.id.menu);
        reservation = (Button) findViewById(R.id.reservation);
        list = (ListView) findViewById(R.id.list);
        list.setVisibility(View.INVISIBLE);
        game_adapter = new ArrayAdapter<String>(this, R.layout.parent_list, R.id.parent_text, game_list);
        menu_adapter = new ArrayAdapter<String>(this, R.layout.parent_list, R.id.parent_text, menu_list);
        google = getIntent().getStringExtra("googleid");

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setAdapter(game_adapter);
                cafe_check = mCafeReference.child("Hero_game");
                list_check = "game";
                menu_adapter.clear();
                list.setVisibility(View.INVISIBLE);

                cafe_check.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String key = snapshot.getKey();
                            game_list.add(key);
                        }
                        game_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                list.setVisibility(View.VISIBLE);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setAdapter(menu_adapter);
                list_check = "menu";
                game_adapter.clear();
                list.setVisibility(View.INVISIBLE);
                cafe_check = mCafeReference.child("menu");
                cafe_check.addValueEventListener(new ValueEventListener() {
                    DatabaseReference menu_check;

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot parent : dataSnapshot.getChildren()) {
                            menu_check = cafe_check.child(parent.getKey());
                            menu_check.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Hero_menu cate = dataSnapshot.getValue(Hero_menu.class);
                                    menu_list.add(cate.getname());
                                    menu_adapter.notifyDataSetChanged();
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
                list.setVisibility(View.VISIBLE);


            }
        });


        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = new Intent(CafeActivity.this, Reservation.class);
                _intent.putExtra("googleId",google);
                startActivity(_intent);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i("확인", list_check);
                if (list_check.equals("game")) {
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View _view = inflater.inflate(R.layout.game_information, null);
                    //View view = inflater.inflate(R.layout.game_information, null);
                    final TextView name = (TextView) _view.findViewById(R.id.game_name);
                    final TextView intro = (TextView) _view.findViewById(R.id.intro);
                    final TextView time = (TextView) _view.findViewById(R.id.game_time_);
                    final TextView number = (TextView) _view.findViewById(R.id.number);
                    ImageView _image = (ImageView) _view.findViewById(R.id.game_image);
                    //_image.set

                    cafe_check = mCafeReference.child("Hero_game");
                    cafe_check.addValueEventListener(new ValueEventListener() {
                        DatabaseReference game_check;

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                game_check = cafe_check.child(snapshot.getKey());
                                game_check.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (game_list.get(position).equals(dataSnapshot.getKey())) {
                                            Hero_game game = dataSnapshot.getValue(Hero_game.class);
                                            name.setText(dataSnapshot.getKey());
                                            time.setText(game.gettime());
                                            number.setText(game.getperson_number());
                                            intro.setText(game.getintro());
                                        }

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


                    game_info = new AlertDialog.Builder(CafeActivity.this);
                    game_info.setView(_view);
                    game_info.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = game_info.create();
                    dialog.show();
                } else {
                    Log.i("확인", list_check);
                    Log.i("과연", menu_list.get(10));
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View _view = inflater.inflate(R.layout.menu_information, null);
                    final TextView category = (TextView) _view.findViewById(R.id.menu_category);
                    final TextView menu_name = (TextView) _view.findViewById(R.id.menu_name_);
                    final TextView cost = (TextView) _view.findViewById(R.id.menu_cost);
                    ImageView _image = (ImageView) _view.findViewById(R.id.game_image);
                    //_image.set

                    cafe_check = mCafeReference.child("menu");
                    cafe_check.addValueEventListener(new ValueEventListener() {
                        DatabaseReference menu_check;

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                menu_check = cafe_check.child(snapshot.getKey());
                                menu_check.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Hero_menu menu = dataSnapshot.getValue(Hero_menu.class);
                                        if(menu_list.get(position).equals(menu.getname())) {
                                            Log.i("확인", menu.getCategory());
                                            category.setText(menu.getCategory());
                                            menu_name.setText(menu.getname());
                                            cost.setText(String.valueOf(menu.getcost()));
                                        }
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


                    menu_info = new AlertDialog.Builder(CafeActivity.this);
                    menu_info.setView(_view);
                    menu_info.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = menu_info.create();
                    dialog.show();

                }

            }
        });


    }

}