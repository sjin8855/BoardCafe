package com.boardcafe.boardcafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mUserReference;
    private DatabaseReference mItemReference;
    private String googleUid;
    static public String mUserKey;   // 자기 유저키
    private User Im;
    private int myAccount;
    //ArrayList<item> itemList;


    @BindView(R.id.account)
    TextView account;
    //public static List<Diary> diaries = new ArrayList<>();

    //대여하기
    @OnClick(R.id.rentbanner)
    public void rent () {
        Intent intent = new Intent(MainActivity.this, CafeActivity.class/*다음으로 이동할 곳*/);
        intent.putExtra("googleid", googleUid);
        //intent.putExtra("itemlist", itemList);
        startActivity(intent);
    }



    @OnClick(R.id.confirmitem)
    public void confirmitem () {
        Intent intent = new Intent(MainActivity.this, BoardActivity.class/*다음으로 이동할 곳*/);
        intent.putExtra("googleid", googleUid);
        //intent.putExtra("itemlist", itemList);
        startActivity(intent);
    }

    //광고배너
    @OnClick(R.id.advertise)
    public void advertise() {
        Intent intent = new Intent(MainActivity.this, MapActivity.class/*다음으로 이동할 곳*/);
        intent.putExtra("googleid", googleUid);
        //intent.putExtra("itemlist", itemList);
        startActivity(intent);
    }
    //로그아웃
    @OnClick(R.id.logout)
    public void logoutClick () {
        // Firebase sign out
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirmation Logout!").
                setMessage("You sure, that you want to logout?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Firebase sign out
                        mAuth.signOut();
                        // Google sign out
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        //    updateUI(null);
                                        Toast.makeText(MainActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        Intent i = new Intent(getApplicationContext(),
                                LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //itemList = new ArrayList<item>();

        googleUid = getIntent().getStringExtra("id");
        mUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(googleUid);

        mAuth = FirebaseAuth.getInstance();
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, null /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot imdataSnapshot : dataSnapshot.getChildren()) {
                    Im = imdataSnapshot.getValue(User.class);
                }
                String myaccount = String.valueOf(Im.getuserAccount());
                account.setText(myaccount+"원");
                if (dataSnapshot.hasChildren()) {

                    mItemReference = FirebaseDatabase.getInstance().getReference().child("Items");


                    mItemReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot itemdataSnapshot : dataSnapshot.getChildren()) {
                                //item tempitem = itemdataSnapshot.getValue(item.class);
                                //itemList.add(tempitem);
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

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.action_enrall:
                        Intent intent_enrall = new Intent(MainActivity.this, LoginActivity.class/*다음으로 이동할 곳*/);
                        intent_enrall.putExtra("googleid", googleUid);
                        //intent_enrall.putExtra("itemlist", itemList);
                        startActivity(intent_enrall);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.action_withdraw:
                        Intent intent_withdraw = new Intent(MainActivity.this, CafeActivity.class/*다음으로 이동할 곳*/);
                        intent_withdraw.putExtra("googleid", googleUid);
                        //intent_withdraw.putExtra("itemlist", itemList);
                        startActivity(intent_withdraw);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.action_report:
                        Intent intent_report = new Intent(MainActivity.this, LoginActivity.class/*다음으로 이동할 곳*/);
                        intent_report.putExtra("googleid", googleUid);
                        //intent_report.putExtra("itemlist", itemList);
                        startActivity(intent_report);
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
    }
}