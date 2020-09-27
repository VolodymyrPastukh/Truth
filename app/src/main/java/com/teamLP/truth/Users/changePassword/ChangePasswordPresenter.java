package com.teamLP.truth.Users.changePassword;

import com.teamLP.truth.Users.model.UserModel;

public class ChangePasswordPresenter {
    UserModel model;
    ChangePassword view;

    public ChangePasswordPresenter(UserModel model) {
        this.model = model;
    }

    public void setView(ChangePassword view) {
        this.view = view;
    }


    public void changePassword() {
        if (!validatePassword1()) {
            return;
        }

        String password = view.repeatPassword.getEditText().getText().toString().trim();
        model.changePassword(password, new UserModel.ChangePasswordCallback() {
            @Override
            public void onChange() {
                view.changeCompleted();
            }
        });
    }

    private boolean validatePassword1() {
        String password1 = view.enteredPassword.getEditText().getText().toString().trim();
        String password2 = view.repeatPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[0-9])" +
                "(?=.*[A-Z])" +
                "(?=.*[a-z])" +
                //"(?=.*[a-zA-Z])" +
                "(?=\\S+$)" +
                ".{8,}" +
                "$";

        if (password1.isEmpty()) {
            view.enteredPassword.setError("Fill in the field!");
            return false;
        } else if (!password1.matches(checkPassword)) {
            view.enteredPassword.setError("Invalid password \n Sample: qweRTy123");
            return false;
        } else if (!password2.equals(password1)) {
            view.repeatPassword.setError("Different passwords");
            view.repeatPassword.setErrorEnabled(true);
            view.enteredPassword.setErrorEnabled(false);
            return false;
        } else {
            view.enteredPassword.setError(null);
            view.enteredPassword.setErrorEnabled(false);
            view.repeatPassword.setErrorEnabled(false);
            return true;
        }
    }
}



