package com.teamLP.truth.Users;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.teamLP.truth.R;
import com.teamLP.truth.Users.Verification.Verification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckPhone extends AppCompatActivity {

    String correctPhone;
    TextInputLayout phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone);

        phoneNumber = findViewById(R.id.phone);

        findViewById(R.id.verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validatePhone()){
                    return;
                }
                Intent verify = new Intent(CheckPhone.this, Verification.class);
                verify.putExtra("query", "forgot_password");
                verify.putExtra("phoneNumber", correctPhone);
                startActivity(verify);
            }
        });

    }

    private boolean validatePhone() {
        String val = phoneNumber.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumber.setError("Fill in the field!");
            return false;
        } else if (val.length() < 10 | val.length() > 12) {
            phoneNumber.setError("Incorrect number");
            return false;
        } else if (val.length() == 10) {
            correctPhone = "+38" + val;
            return true;
        } else if (val.length() == 12) {
            correctPhone = "+" + val;
            return true;
        } else {
            correctPhone = val;
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}