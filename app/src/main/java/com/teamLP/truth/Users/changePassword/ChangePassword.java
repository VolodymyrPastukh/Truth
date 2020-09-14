package com.teamLP.truth.Users.changePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamLP.truth.R;
import com.teamLP.truth.Users.login.Login;
import com.teamLP.truth.Users.model.UserModel;

public class ChangePassword extends AppCompatActivity {

    DatabaseReference referenceDB;
    UserModel model;
    ChangePasswordPresenter presenter;

    TextInputLayout enteredPassword, repeatPassword;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Bundle _phone = getIntent().getExtras();
        phoneNumber = _phone.getString("phone");

        enteredPassword = findViewById(R.id.change_password_1);
        repeatPassword = findViewById(R.id.change_password_2);

        referenceDB = FirebaseDatabase.getInstance()
                .getReference("User")
                .child(phoneNumber)
                .child("password");

        model = new UserModel(referenceDB);
        presenter = new ChangePasswordPresenter(model);
        presenter.setView(this);

        findViewById(R.id.change_password_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changePassword();
            }
        });
    }

    public void changeCompleted(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}