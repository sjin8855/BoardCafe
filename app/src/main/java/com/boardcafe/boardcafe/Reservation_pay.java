package com.boardcafe.boardcafe;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reservation_pay extends AppCompatActivity {
    Intent _intent;
    String table_id, table_reservation;
    TextView _table_id, calender_text, time_text, play_time, My_money, total_cost;
    Button calender, time, pay_check, cancel, input_playtime;
    EditText input_number;
    String date, output_time, number, output_date, playtime;
    List<Reservation_information> user_reservation = new ArrayList<>();
    List<Table_information> table_information = new ArrayList<>();
    List<String> user_information = new ArrayList<>();
    List<String> key_save = new ArrayList<>();
    int Myear, Mmonth, Mday, Mhour, Mminute;
    private DatabaseReference mReserveReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference reserve_check;
    private DatabaseReference table_check;
    private DatabaseReference user_check;
    DatabaseReference user_check2;
    DatabaseReference user_check3;
    Map<String, Object> update = new HashMap<String, Object>();
    AlertDialog.Builder time_list;
    String[] time_select = {"1시간", "2시간", "3시간", "4시간", "5시간", "6시간", "7시간", "8시간"};
    int len, count;
    int location = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Calendar cal = new GregorianCalendar();
        _intent = getIntent();
        _table_id = (TextView) findViewById(R.id.table_id);
        calender_text = (TextView) findViewById(R.id.calender_text);
        time_text = (TextView) findViewById(R.id.time_text);
        calender = (Button) findViewById(R.id.input_calender);
        time = (Button) findViewById(R.id.input_time);
        pay_check = (Button) findViewById(R.id.pay_check);
        cancel = (Button) findViewById(R.id.cancel);
        input_number = (EditText) findViewById(R.id.input_number);
        play_time = (TextView) findViewById(R.id.play_time);
        input_playtime = (Button) findViewById(R.id.input_playtime);
        time_list = new AlertDialog.Builder(this);
        My_money = (TextView) findViewById(R.id.My_money);
        total_cost = (TextView) findViewById(R.id.total_cost);


        Myear = cal.get(Calendar.YEAR);
        Mmonth = cal.get(Calendar.MONTH);
        Mday = cal.get(Calendar.DAY_OF_MONTH);
        Mhour = cal.get(Calendar.HOUR_OF_DAY);
        Mminute = cal.get(Calendar.MINUTE);
        table_id = _intent.getStringExtra("table_id");
        table_reservation = _intent.getStringExtra("reservation");
        _table_id.setText(table_id + "번 테이블 예약 설정");
        My_money.setText(LoginActivity.currentUser.getuserAccount() + "원");


        key_save.clear();
        reserve_check = mReserveReference.child("Reservation");
        table_check = mReserveReference.child("Table");
        user_check = mReserveReference.child("Users");
        key_save.add("null");

        reserve_check.addValueEventListener(new ValueEventListener() {
            DatabaseReference check;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot parent : dataSnapshot.getChildren()) {
                    check = reserve_check.child(parent.getKey());
                    check.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Reservation_information re_info = dataSnapshot.getValue(Reservation_information.class);
                            user_reservation.add(re_info);
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
        table_check.addValueEventListener(new ValueEventListener() {
            DatabaseReference check;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot parent : dataSnapshot.getChildren()) {
                    check = table_check.child(parent.getKey());
                    check.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Table_information Ti = dataSnapshot.getValue(Table_information.class);
                            table_information.add(Ti);
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
        user_check.addValueEventListener(new ValueEventListener() {
            String key_check;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot parent1 : dataSnapshot.getChildren()) {
                    user_check2 = user_check.child(parent1.getKey());
                    key_save.add(parent1.getKey());
                    key_check = parent1.getKey();
                    user_check2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            for (DataSnapshot parent2 : dataSnapshot2.getChildren()) {
                                key_save.add(parent2.getKey());
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

        calender.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Reservation_pay.this, input_date, Myear, Mmonth, Mday).show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Reservation_pay.this, input_time, Mhour, Mminute, false).show();
            }
        });
        input_playtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_list.setTitle("시간 선택").setItems(time_select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playtime = time_select[which];
                        play_time.setText(playtime);
                    }
                });
                time_list.create();
                time_list.show();

            }
        });
        pay_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                len = (key_save.size() - 1) / 2;
                Log.i("dd", String.valueOf(key_save.size()));
                for (count = 1; count <= len; count++) {
                    user_check3 = mReserveReference.child("Users").child(key_save.get(count)).child(key_save.get(count + len));
                    user_check3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            user_information.add(user.getUserEmail());
                            if (LoginActivity.currentUser.getUserEmail().equals(user.getUserEmail())) {
                                location++;
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    Log.i("location",String.valueOf(location));
                }

                _intent = new Intent(getApplicationContext(), CafeActivity.class);
                boolean reserve_check = false;
                int total = 0;
                number = input_number.getText().toString();
                Log.i("이것도", key_save.get(4));
                Log.i("이것도", key_save.get(1));

                for (int i = 0; i < table_information.size(); i++) {
                    String temp;
                    temp = table_information.get(i).getNumber();
                    temp = temp.substring(0, temp.length() - 1);
                    String sp1[] = temp.split("~");
                    for (int j = 0; j < sp1.length; j++) {
                        if (number.equals(sp1[j]) || number.equals("2")) {
                            reserve_check = true;
                        }
                    }
                    if (!reserve_check)
                        Toast.makeText(Reservation_pay.this, "테이블에 맞는 인원수가 아닙니다.", Toast.LENGTH_LONG).show();
                }
                if (reserve_check) {
                    String sp_time[] = output_time.split(":");
                    if (Integer.parseInt(sp_time[0]) > 12) {
                        reserve_check = true;
                    } else {
                        reserve_check = false;
                        Toast.makeText(Reservation_pay.this, "영업시간 이외에 시간입니다.", Toast.LENGTH_LONG).show();
                    }
                }
                if (reserve_check) {
                    total = Integer.parseInt(number) * Integer.parseInt(playtime.substring(0, 1)) * 2400; // 가격 설정
                    total_cost.setText(total + "원");
                    if (LoginActivity.currentUser.getuserAccount() < total) {
                        Toast.makeText(Reservation_pay.this, "잔액이 부족합니다.", Toast.LENGTH_LONG).show();
                        reserve_check = false;
                    } else {
                        update.clear();
                        LoginActivity.currentUser.setuserAccount(LoginActivity.currentUser.getuserAccount() - total);
                        Log.i("잔액", String.valueOf(LoginActivity.currentUser.getuserAccount()));
                        User up_user = new User(LoginActivity.currentUser.getUserEmail(), LoginActivity.currentUser.getuserAccount());
                        DatabaseReference user_update;
                        Log.i("설마",key_save.get(location));
                        user_update = mReserveReference.child("Users").child(key_save.get(location));
                        update.put(key_save.get(location + len), up_user);
                        user_update.updateChildren(update);
                        Toast.makeText(Reservation_pay.this, "예약되었습니다.", Toast.LENGTH_LONG).show();
                        startActivity(_intent);
                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _intent = new Intent(getApplicationContext(), CafeActivity.class);
                startActivity(_intent);
            }
        });


    }

    DatePickerDialog.OnDateSetListener input_date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Myear = year;
            Mmonth = month + 1;
            Mday = dayOfMonth;
            output_date = String.format("%d/%d/%d", Myear, Mmonth, Mday);
            calender_text.setText(output_date);
        }
    };

    TimePickerDialog.OnTimeSetListener input_time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Mhour = hourOfDay;
            Mminute = minute;
            output_time = String.format("%d:%d", Mhour, Mminute);
            time_text.setText(output_time);
        }
    };


}


