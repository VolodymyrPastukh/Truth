package com.teamLP.truth.Users.model;

import android.util.Log;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.TimeUnit;

public class UserModel {

    DatabaseReference referenceDB;

    public UserModel(DatabaseReference referenceDB) {
        this.referenceDB = referenceDB;
    }

    public void verificationPhone(String phoneNumber, VerificationCallback callback){
        VerificationUser verificationUser = new VerificationUser(callback);
        verificationUser.sendVerificationCodeToUser(phoneNumber);
    }

    public void signUp(UserData user, SignUpCallback callback){
        referenceDB.child(user.phoneNumber).setValue(user);
        callback.onSignUp(user.username);
    }



    public interface VerificationCallback{
        void onVerified(String codeBySys, String code);
    }

    public interface SignUpCallback{
        void onSignUp(String name);
    }

    class VerificationUser{
        VerificationCallback callback;
        String codeBySystem;

        public VerificationUser(VerificationCallback callback) {
            this.callback = callback;
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
                           callback.onVerified(codeBySystem,code);

                        }
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Log.e("Auth Error ---", e.toString());
                    }
                };
    }
}
