package com.teamLP.truth.Users.Verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamLP.truth.R;
import com.teamLP.truth.Users.changePassword.ChangePassword;
import com.teamLP.truth.Users.login.Login;
import com.teamLP.truth.Users.model.UserData;
import com.teamLP.truth.Users.model.UserModel;

public class Verification extends AppCompatActivity {

    PinView userPin;
    String action;
    String username, fullname, email, password, phoneNumber, gender, ownInfo, age;


    UserModel model;
    VerificationPresenter presenter;
    DatabaseReference referenceDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        referenceDB = FirebaseDatabase.getInstance().getReference("User");
        model = new UserModel(referenceDB);
        presenter = new VerificationPresenter(model);
        presenter.setView(this);

        userPin = findViewById(R.id.userPin);

        Bundle intent = getIntent().getExtras();
        String query = intent.getString("query");
        assert query != null;
        if (query.equalsIgnoreCase("sign_up")) {
            action = query;
            initSignUp();
        }else if(query.equalsIgnoreCase("forgot_password")){
            action = query;
            initForgotPassword();
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.verificationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.verifyCode();
            }
        });

    }


    private void initForgotPassword() {
        Bundle changePassword = getIntent().getExtras();
        phoneNumber = changePassword.getString("phoneNumber");
        presenter.verificationPhone(phoneNumber);
    }

    private void initSignUp() {
        Bundle userData = getIntent().getExtras();
        String[] userMainData = userData.getStringArray("userMainData");
        String[] userOtherData = userData.getStringArray("userOtherData");
        username = userMainData[0];
        fullname = userMainData[1];
        email = userMainData[2];
        password = userMainData[3];
        phoneNumber = userMainData[4];
        gender = userOtherData[0];
        ownInfo = userOtherData[1];
        age = userOtherData[2];

        Log.i("Phone", phoneNumber);
        presenter.verificationPhone(phoneNumber);
    }

    public void changePassword(){
        Intent changePassword = new Intent(this, ChangePassword.class);
        changePassword.putExtra("phone", phoneNumber);
        startActivity(changePassword);
    }

    public void signIn() {
        UserData newUser = new UserData(username,fullname,email,password,phoneNumber,gender,ownInfo,age);
        presenter.addUser(newUser);
        Intent completeSignUp = new Intent( this, Login.class);
        startActivity(completeSignUp);
    }


}