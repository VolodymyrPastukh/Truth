package com.teamLP.truth.Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.teamLP.truth.R;

public class SignUp extends AppCompatActivity implements Sign1.ToNextSignListener, Sign2.completeSignIn{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    String[] userMainData;
    String[] userOtherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, new Sign1());
        fragmentTransaction.commit();
    }

    @Override
    public void toNextSign(String[] arr) {
        userMainData = arr;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new Sign2());
        fragmentTransaction.commit();
    }

    @Override
    public void toComleteSignIn(String[] arr) {
        userOtherData = arr;
        Intent intent = new Intent(this, Verification.class);
        intent.putExtra("userMainData", userMainData);
        intent.putExtra("userOtherData", userOtherData);
        startActivity(intent);
    }
}