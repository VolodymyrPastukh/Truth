package com.teamLP.truth.Users.Verification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.chaos.view.PinView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamLP.truth.R;
import com.teamLP.truth.Users.model.UserData;
import com.teamLP.truth.Users.model.UserModel;

public class Verification extends AppCompatActivity {

    PinView userPin;
    String username, fullname, email, password, phoneNumber, gender, ownInfo, age;
//    String codeBySystem;

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


    public void addUser() {
        UserData newUser = new UserData(username,fullname,email,password,phoneNumber,gender,ownInfo,age);
        presenter.addUser(newUser);
    }

    public void finishSignUp(){
        presenter.verifyCode();
    }
}