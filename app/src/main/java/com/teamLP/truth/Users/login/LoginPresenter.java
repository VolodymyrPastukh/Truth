package com.teamLP.truth.Users.login;

import android.util.Log;
import android.view.View;

import com.teamLP.truth.Users.SessionManager;
import com.teamLP.truth.Users.model.UserData;
import com.teamLP.truth.Users.model.UserModel;

import java.util.HashMap;

public class LoginPresenter {
    private UserModel model;
    private Login view;
    private String correctPhone;

    public LoginPresenter(UserModel model) {
        this.model = model;
    }

    public void setView(Login view) {
        this.view = view;
    }

    public void loginUser() {

        view.progresBar.setVisibility(View.VISIBLE);
        final String password = view.enteredPassword.getEditText().getText().toString().trim();

        if(!validatePhone()){
            return;
        }
        Log.i("Phone length:", String.valueOf(correctPhone.length()));

        view.progresBar.setVisibility(View.VISIBLE);
        model.loginUser(correctPhone, password, new UserModel.LoginUserCallback() {
            @Override
            public void onLogin(int isLogin, UserData userData) {
                switch (isLogin) {
                    case 0:
                        view.progresBar.setVisibility(View.GONE);
                        view.enteredPhone.setError(null);
                        view.enteredPhone.setErrorEnabled(false);
                        view.enteredPassword.setError(null);
                        view.enteredPassword.setErrorEnabled(false);
                        loginSession(userData);
                        rememberMe();
                        view.enterTheVoid(userData.getUsername(), correctPhone);
                        break;
                    case 1:
                        view.progresBar.setVisibility(View.GONE);
                        view.enteredPassword.setError("Bad password");
                        view.enteredPassword.setErrorEnabled(true);
                        break;
                    case 2:
                        view.progresBar.setVisibility(View.GONE);
                        view.enteredPhone.setError("User doesn`t exist");
                        view.enteredPhone.setErrorEnabled(true);
                        break;
                    case 3:
                        view.progresBar.setVisibility(View.GONE);
                        view.enteredPhone.setError("Login error");
                        view.enteredPhone.setErrorEnabled(false);
                        view.enteredPassword.setError("Login error");
                        view.enteredPassword.setErrorEnabled(false);
                        break;
                }
            }
        });
    }

    private void loginSession(UserData _userData){
        SessionManager sessionManager = new SessionManager(view, SessionManager.USERLOGIN_SESSION);
        sessionManager.createUserLoginSession(_userData);
    }

    private void rememberMe(){
        if(view.rememberMe.isChecked()){
            final String phone = view.enteredPhone.getEditText().getText().toString().trim();
            final String password = view.enteredPassword.getEditText().getText().toString().trim();
            SessionManager sessionManager = new SessionManager(view, SessionManager.REMEMBERME_SESSION);
            sessionManager.createRememberMeSession(phone,password);
        }
    }

    public void startLoginSession(){
        SessionManager sessionManager = new SessionManager(view, SessionManager.REMEMBERME_SESSION);
        HashMap<String, String> loginData = sessionManager.getRememberMeDataFromSession();
        view.editPhone.setText(loginData.get(SessionManager.KEY_PHONE));
        view.editPassword.setText(loginData.get(SessionManager.KEY_PASSWORD));
    }


    private boolean validatePhone() {
        String val = view.enteredPhone.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            view.enteredPhone.setError("Fill in the field!");
            return false;
        } else if (val.length() < 10 | val.length() > 12) {
            view.enteredPhone.setError("Incorrect number");
            return false;
        } else if (val.length() == 10) {
            correctPhone = "+38" + val;
            return true;
        } else if (val.length() == 12) {
            correctPhone = "+" + val;
            return true;
        } else {
            correctPhone = val;
            view.enteredPhone.setError(null);
            view.enteredPhone.setErrorEnabled(false);
            return true;
        }
    }
}

