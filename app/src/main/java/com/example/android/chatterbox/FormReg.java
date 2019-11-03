package com.example.android.chatterbox;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormReg extends AppCompatActivity {
    EditText fname, lname, reg, eml, pass, cpass;
    private String TAG = "add to database";
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = mFirebaseDatabase.getReference();
    private FirebaseAuth mAuth;
    Button signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_reg);
        fname = (EditText) findViewById(R.id.form_firstname);
        lname = (EditText) findViewById(R.id.form_lastname);
        reg = (EditText) findViewById(R.id.form_registrationnumber);
        eml = (EditText) findViewById(R.id.form_email);
        pass = (EditText) findViewById(R.id.form_password);
        cpass = (EditText) findViewById(R.id.form_confirmpassword);
        mAuth=FirebaseAuth.getInstance();
        signup=(Button) findViewById(R.id.form_buttonsignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Submit pressed.");
                String email = eml.getText().toString();
                String password = pass.getText().toString();
                createAccount(email,password);
            }
        });


    }

    private void createAccount(String email2,String password2) {
        mAuth.createUserWithEmailAndPassword(email2, password2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            addreg(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(FormReg.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        // [END create_user_with_email]
    }

    private void addreg(final FirebaseUser user) {
        if(user!=null)
        {
            UserProfileChangeRequest profileUpdate=new UserProfileChangeRequest.Builder().setDisplayName(reg.getText().toString()).build();
            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d("testing","profile updated");
                        addtodb(user);
                    }
                }
            });
        }
    }

    private void addtodb(FirebaseUser user) {
        Log.d(TAG, "onClick: Submit pressed.");
        String firstname = fname.getText().toString();
        String lastname = lname.getText().toString();
        String registrationnumber = reg.getText().toString();
        String email = eml.getText().toString();
        String password = pass.getText().toString();
        String cpassword = cpass.getText().toString();

        //handle the exception if the EditText fields are null
        if (!firstname.equals("") && !email.equals("") && !password.equals("") && !registrationnumber.equals("") && !lastname.equals("") && !cpassword.equals("")) {
            if (password.equals(cpassword)) {
                UserInformation userInformation = new UserInformation(firstname, lastname, registrationnumber, email, password);
                myRef.child("users").child(user.getUid()).setValue(userInformation);
                fname.setText("");
                lname.setText("");
                eml.setText("");
                reg.setText("");
                pass.setText("");
                cpass.setText("");
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FormReg.this, "password mismatch", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(FormReg.this, "fill out all the fields", Toast.LENGTH_SHORT).show();
        }
    }


    public void jumptosignin(View view) {
        Intent i = new Intent(FormReg.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
