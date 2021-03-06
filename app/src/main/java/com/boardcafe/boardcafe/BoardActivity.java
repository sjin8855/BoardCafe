package com.boardcafe.boardcafe;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BoardActivity extends AppCompatActivity{
    BoardAdapter adapter;

    EditText editText1;
    EditText editText2;
    SoftKeyboard softKeyboard;
    LinearLayout ll;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        mData = FirebaseDatabase.getInstance().getReference();

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        final ListView listView = (ListView) findViewById(R.id._listView);
        adapter = new BoardAdapter(getApplicationContext());

        ll = (LinearLayout)findViewById(R.id.linearLayout);

        InputMethodManager controlManager = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
        softKeyboard = new SoftKeyboard(ll, controlManager);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged() {
                                                 @Override
                                                 public void onSoftKeyboardHide() {
                                                     new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             Toast.makeText(getApplication(), "키보드 내려감", Toast.LENGTH_SHORT).show();
                                                         }
                                                     });
                                                 }

                                                 @Override
                                                 public void onSoftKeyboardShow() {
                                                     new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             Toast.makeText(getApplication(), "키보드 올라감", Toast.LENGTH_SHORT).show();
                                                             listView.setVisibility(View.INVISIBLE);
                                                         }
                                                     });
                                                 }
                                             });

        listView.setAdapter(adapter);

        mData.child("Contents").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String _id = dataSnapshot.getValue(BoardItem.class).getId();
                String _content = dataSnapshot.getValue(BoardItem.class).getContents();
                BoardItem _item = new BoardItem(_id, _content);
                adapter.addItem(_item);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                BoardItem item = (BoardItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 :" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        Button button = (Button)findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editText1.getText().toString();
                String contents = editText2.getText().toString();
                    BoardItem _item = new BoardItem(id, contents);
                    //adapter.addItem(_item);
                    mData.child("Contents").push().setValue(_item);
                    adapter.notifyDataSetChanged();
                    editText1.setText(null);
                    editText2.setText(null);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText1.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(editText2.getWindowToken(), 0);
                }
        });
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        softKeyboard.unRegisterSoftKeyboardCallback();
    }

    public float dpToPx(float value){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
    }
}
