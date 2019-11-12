package com.example.android.chatterbox;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Options extends AppCompatActivity {
    RelativeLayout chatroom,settings,info,logout;

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private String name;
    String fourthyear = "CSE fourth year";
    String thirdyear = "CSE third year";
    String secondyear = "CSE second year";
    String firstyear = "CSE first year";
    Dbhelper mydb;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        mydb=new Dbhelper(this);
        chatroom=(RelativeLayout)findViewById(R.id.btn_chatroom);
        info=(RelativeLayout)findViewById(R.id.btn_info);
        settings=(RelativeLayout)findViewById(R.id.btn_settings);
        logout=(RelativeLayout)findViewById(R.id.btn_logout);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        name = getIntent().getExtras().getString("username");
        builder=new AlertDialog.Builder(this);
        chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                room();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Options.this,SampleActivity.class);
                startActivity(i);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Options.this,SampleActivity.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Logout").setMessage("Are you sure you want to Logout").
                        setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mydb.deleteall();
                                mAuth.signOut();
                                Intent i=new Intent(Options.this,MainActivity.class);
                                startActivity(i);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setIcon(R.drawable.alert).show();


            }
        });
    }

    private void room() {
        if (name.charAt(1) == '4') {
//            Toast.makeText(this, "Welcome to chat room \n 4th YEAR", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Options.this, MainChat.class);
            i.putExtra("room_name", fourthyear);
            i.putExtra("user_name", name);
            i.putExtra("year",4);
            startActivity(i);
        }
        if (name.charAt(1) == '5') {
//            Toast.makeText(this, "Welcome to chat room \n 3rd YEAR", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Options.this, MainChat.class);
            i.putExtra("room_name", thirdyear);
            i.putExtra("user_name", name);
            i.putExtra("year",3);
            startActivity(i);
        }
        if (name.charAt(1) == '6') {
//            Toast.makeText(this, "Welcome to chat room \n 2nd YEAR", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Options.this, MainChat.class);
            i.putExtra("room_name", secondyear);
            i.putExtra("user_name", name);
            i.putExtra("year",2);
            startActivity(i);
        }
        if (name.charAt(1) == '7') {
//            Toast.makeText(this, "Welcome to chat room \n 1st YEAR", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Options.this, MainChat.class);
            i.putExtra("room_name", firstyear);
            i.putExtra("user_name", name);
            i.putExtra("year",1);
            startActivity(i);
        }
    }
}
