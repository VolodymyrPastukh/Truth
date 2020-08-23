package com.teamLP.truth.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamLP.truth.art.Content;
import com.teamLP.truth.R;

public class WelcomeScreen extends AppCompatActivity {

    Button login, signIn, asGuest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        login = findViewById(R.id.loginButton);
        signIn = findViewById(R.id.signUpButton);
        asGuest = findViewById(R.id.asGuestButton);

    }

    public void welcomeClick(View view){
        switch (view.getId()){

            case R.id.loginButton:
                Intent login = new Intent(this, Login.class);
                startActivity(login);
                break;

            case R.id.signUpButton:
                Intent signIn = new Intent(this, SignUp.class);
                startActivity(signIn);
                break;

            case R.id.asGuestButton:
                Intent guest = new Intent(this, Content.class);
                guest.putExtra("startKey", 0);
                startActivity(guest);
                break;
        }

    }


}