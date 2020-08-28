package com.teamLP.truth.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamLP.truth.R;
import com.teamLP.truth.art.Content;

public class Login extends AppCompatActivity {

    TextInputLayout enteredPhone, enteredPassword;
    RelativeLayout progresBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enteredPhone = findViewById(R.id.enter_phone);
        enteredPassword = findViewById(R.id.enter_password);
        progresBar = findViewById(R.id.progressBar);
        progresBar.setVisibility(View.GONE);
    }

    public void logIn(View view){
        if(!validatePassword()){
            return;
        }

        progresBar.setVisibility(View.VISIBLE);


        final String _phoneNumber = enteredPhone.getEditText().getText().toString().trim();
        final String _password = enteredPassword.getEditText().getText().toString().trim();

        Query checkUser = FirebaseDatabase.getInstance().getReference("User").orderByChild("phoneNumber").equalTo(_phoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    enteredPhone.setError(null);
                    enteredPhone.setErrorEnabled(false);

                    String systemPassword = snapshot.child(_phoneNumber).child("password").getValue(String.class);
                    if(systemPassword.equals(_password)){
                        enteredPassword.setError(null);
                        enteredPassword.setErrorEnabled(false);

                        String username = snapshot.child(_phoneNumber).child("username").getValue(String.class);
                        Intent login = new Intent(Login.this, Content.class);
                        login.putExtra("username", username);
                        login.putExtra("startKey", 1);
                        login.putExtra("phone", _phoneNumber);
                        startActivity(login);

                    }
                    else{
                        progresBar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Bad password!!!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "User is not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progresBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, "??????", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private boolean validatePassword() {
        String val = enteredPassword.getEditText().getText().toString().trim();
        String checkPassword = "^"+
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";

        if (val.isEmpty()) {
            enteredPassword.setError("Fill in the field!");
            return false;
        } else if (!val.matches(checkPassword)) {
            enteredPassword.setError("Invalid password");
            return false;
        } else {
            enteredPassword.setError(null);
            enteredPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone() {
        String val = enteredPhone.getEditText().getText().toString().trim();


        if (val.isEmpty()) {
            enteredPhone.setError("Fill in the field!");
            return false;
        }else if(val.length() != 11){
            enteredPhone.setError("Incorrect number");
            return false;
        } else {
            enteredPhone.setError(null);
            enteredPhone.setErrorEnabled(false);
            return true;
        }
    }
}