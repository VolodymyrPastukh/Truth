package com.teamLP.truth.Users.Verification;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.teamLP.truth.Users.login.Login;
import com.teamLP.truth.Users.model.UserData;
import com.teamLP.truth.Users.model.UserModel;

public class VerificationPresenter {
    private UserModel model;
    private Verification view;
    private String systemCode;

    public VerificationPresenter(UserModel model) {
        this.model = model;
    }

    public void setView(Verification view) {
        this.view = view;
    }


    public void verificationPhone(String phone){
        model.verificationPhone(phone, new UserModel.VerificationCallback() {
            @Override
            public void onVerified(String codeBySystem, String code) {
                view.userPin.setText(code);
                systemCode = codeBySystem;
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(systemCode,code);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }

    public void verifyCode(){
        String code = view.userPin.getText().toString();
        if(!code.isEmpty()){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(systemCode,code);
            signInWithPhoneAuthCredential(credential);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("USER_VERIFICATION", "signInWithCredential:success");
                            Toast.makeText(view, "Verification completed!)", Toast.LENGTH_SHORT).show();
                            if(view.action.equalsIgnoreCase("sign_up")){
                                view.signIn();
                            }else{
                                view.changePassword();
                            }
                            Log.e("Verification: ", " unknown operation");


                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(view, "Verification not completed....", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void addUser(UserData user){
        model.signUp(user, new UserModel.SignUpCallback() {
            @Override
            public void onSignUp(String username) {
                Log.i("User" + username + ":", " was created !");
            }
        });
    }

}
