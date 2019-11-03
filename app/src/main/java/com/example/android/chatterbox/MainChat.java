package com.example.android.chatterbox;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by kunal on 6/28/2017.
 */

public class MainChat extends AppCompatActivity {

    private ImageButton btn_send_msg;
    private EditText input_msg;

    private String user_name, room_name,yr;
    private int year;
    private DatabaseReference root;
    private String temp_key;

    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter customAdapter;
    List<Values> list=new ArrayList<>();
    List<Values>list2=new ArrayList<>();
    CustomDialog cd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        btn_send_msg = (ImageButton) findViewById(R.id.btn_send);
        input_msg = (EditText) findViewById(R.id.msg_input);
        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        yr=getIntent().getExtras().get("year").toString();
//        year=Integer.parseInt(yr);
//        cd=new CustomDialog(this,year);
//        cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        cd.show();
        setTitle(" Room - " + room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);
        rv=(RecyclerView)findViewById(R.id.rv);
        layoutManager=new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> map = new HashMap<>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String, Object> map2 = new HashMap<>();
                map2.put("name", user_name);
                map2.put("msg", input_msg.getText().toString());
                message_root.updateChildren(map2);
                input_msg.setText("");
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
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

    }

    private String chat_msg, chat_user_name;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();
        list2.clear();
        while (i.hasNext()) {
            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot) i.next()).getValue();
            Values v=new Values();
            v.setRegnumber(chat_user_name);
            v.setMessage(chat_msg);
            list.add(v);
//            chat_conversation.append(chat_user_name + " : " + chat_msg + " \n");
        }
        list2.addAll(list);
        customAdapter=new CustomAdapter(this,list2,user_name);
        rv.setAdapter(customAdapter);
        int position=rv.getAdapter().getItemCount()-1;
        rv.scrollToPosition(position);
//        rv.smoothScrollToPosition(position);
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
