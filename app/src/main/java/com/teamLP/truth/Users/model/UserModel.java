package com.teamLP.truth.Users.model;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.teamLP.truth.Users.login.Login;
import com.teamLP.truth.art.Content;
import com.teamLP.truth.art.model.ArticleData;
import com.teamLP.truth.art.model.ArticleModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

    public void loginUser(final String phoneNumber, final String password, final LoginUserCallback callback){

        Query checkUser = referenceDB.orderByChild("phoneNumber").equalTo(phoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String systemPassword = snapshot.child(phoneNumber).child("password").getValue(String.class);
                    if(systemPassword.equals(password)){
                        String username = snapshot.child(phoneNumber).child("username").getValue(String.class);
                        UserData userData = snapshot.child(phoneNumber).getValue(UserData.class);
                        callback.onLogin(0, userData);
                    }
                    else{
                        callback.onLogin(1,  null);
                    }
                }else{
                    callback.onLogin(2, null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onLogin(3,  null);
            }
        });
    }

    public void changePassword(final String password, final ChangePasswordCallback callback){

        referenceDB.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                String lk = currentData.getValue(String.class);
                if (lk == null) {
                    return Transaction.success(currentData);
                }
                currentData.setValue(password);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                if (error == null) {
                    callback.onChange();
                } else {
                    Log.e("Password change: ", "error" + error);
                }
            }
        });
    }

    public void loadUsers(final LoadUserDataCallback callback){
        final List<UserData> users = new ArrayList<>();
        referenceDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (users.size() > 0) {
                    users.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()) {
                    UserData user = ds.getValue(UserData.class);
                    assert user != null;
                    users.add(user);
                    callback.onLoadUsers(users);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onLoadUsers(null);
                Log.e("Users Load error: ", error.toString());
            }
        });
    }



    public interface VerificationCallback{
        void onVerified(String codeBySys, String code);
    }

    public interface SignUpCallback{
        void onSignUp(String name);
    }

    public interface LoginUserCallback{
        void onLogin(int isLogin, UserData userData);
    }

    public interface ChangePasswordCallback{
        void onChange();
    }

    public interface LoadUserDataCallback{
        void onLoadUsers(List<UserData> users);
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
