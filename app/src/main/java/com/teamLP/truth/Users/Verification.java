package com.teamLP.truth.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamLP.truth.R;
import com.teamLP.truth.Users.model.User;

import java.util.concurrent.TimeUnit;

public class Verification extends AppCompatActivity {

    PinView userPin;
    String username, fullname, email, password, phoneNumber, gender, ownInfo, age;
    String codeBySystem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

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
        sendVerificationCodeToUser(phoneNumber);

    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if(code != null){
                        userPin.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("USER_VERIFICATION", "signInWithCredential:success");
                            Toast.makeText(Verification.this, "Verification completed!)", Toast.LENGTH_SHORT).show();
                            storeNewUser();
                            Intent completeSignUp = new Intent( Verification.this, Login.class);
                            startActivity(completeSignUp);
                            
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Verification.this, "Verification not completed....", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void storeNewUser() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("User");

        User newUser = new User(username,fullname,email,password,phoneNumber,gender,ownInfo,age);
        reference.child(phoneNumber).setValue(newUser);

    }

    public void finishSignUp(View view){
        String code = userPin.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }
}