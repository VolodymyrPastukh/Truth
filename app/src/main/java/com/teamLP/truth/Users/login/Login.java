package com.teamLP.truth.Users.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamLP.truth.R;
import com.teamLP.truth.Users.CheckPhone;
import com.teamLP.truth.Users.SessionManager;
import com.teamLP.truth.Users.WelcomeScreen;
import com.teamLP.truth.Users.model.UserModel;
import com.teamLP.truth.art.Content;

public class Login extends AppCompatActivity {

    TextInputLayout enteredPhone, enteredPassword;
    TextInputEditText editPhone, editPassword;
    RelativeLayout progresBar;
    CheckBox rememberMe;

    UserModel model;
    LoginPresenter presenter;
    DatabaseReference referenceDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        referenceDB = FirebaseDatabase.getInstance().getReference("User");
        model = new UserModel(referenceDB);
        presenter = new LoginPresenter(model);
        presenter.setView(this);

        rememberMe = findViewById(R.id.remember_me);
        enteredPhone = findViewById(R.id.enter_phone);
        enteredPassword = findViewById(R.id.enter_password);
        editPhone = findViewById(R.id.edit_phone);
        editPassword = findViewById(R.id.edit_password);
        progresBar = findViewById(R.id.progressBar);
        progresBar.setVisibility(View.GONE);

        presenter.startLoginSession();

        findViewById(R.id.forgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, CheckPhone.class);
                startActivity(intent);
            }
        });
    }

    public void logIn(View view) {
        presenter.loginUser();
    }

    public void enterTheVoid(String username, String phoneNumber) {
        Intent login = new Intent(getApplicationContext(), Content.class);
        login.putExtra("username", username);
        login.putExtra("startKey", 1);
        login.putExtra("phone", phoneNumber);
        startActivity(login);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
    }


}