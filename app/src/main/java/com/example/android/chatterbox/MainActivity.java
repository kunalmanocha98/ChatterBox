package com.example.android.chatterbox;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText eml,pass;
    Button signin;
    FirebaseAuth mAuth;
    Dbhelper mydb;
    String emaildb,passdb;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb=new Dbhelper(this);
        eml = (EditText) findViewById(R.id.email_login);
        pass = (EditText) findViewById(R.id.password_login);
        signin = (Button) findViewById(R.id.signin_login);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        mAuth=FirebaseAuth.getInstance();
        checksql();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email=eml.getText().toString();
                String password=pass.getText().toString();
                signIn(email,password);
            }
        });
    }
// check sql for previously stored data
    private void checksql() {
        Cursor res=mydb.getalldata();
        if(res.getCount()==0){
            return;
        }else{
            progressBar.setVisibility(View.VISIBLE);
            while (res.moveToNext()){
                emaildb=res.getString(0);
                passdb=res.getString(1);
            }
            signIn(emaildb,passdb);
        }
    }
// sign in for chatterbox
    private void signIn(final String email, final String password) {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user =mAuth.getCurrentUser();
                        Log.d("signin","signin successful");
                        checkVerification(user,email,password);
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Log.d("signin","authentication failed");

                    }
                }
            });
    }
// checking if email is verified
    private void checkVerification(FirebaseUser user, String email, String password) {
        if(user.isEmailVerified()){
            mydb.enteruserinfo(email,password);
            Intent i=new Intent(MainActivity.this,Options.class);
            String name=user.getDisplayName();
            i.putExtra("username",name);
            startActivity(i);
            progressBar.setVisibility(View.GONE);
            finish();
        }
        else
        {
            sendEmailVerification(user);

        }
    }

    private void sendEmailVerification(final FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "first verify your email \n Verification email sent to\n " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("sendEmailVerification", String.valueOf(task.getException()));
                            Toast.makeText(MainActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
    }
// register button listener
    public void register(View view) {
        Intent i=new Intent(MainActivity.this,FormReg.class);
        startActivity(i);
    }
}
