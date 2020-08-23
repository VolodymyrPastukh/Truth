package com.teamLP.truth.Users;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.teamLP.truth.R;
import com.teamLP.truth.art.TopArticles;


public class Sign1 extends Fragment implements View.OnClickListener {

    Button nextButton;
    ToNextSignListener mListener;
    TextInputLayout username, fullname, phoneNumber, email, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sign1, container, false);


        username = rootView.findViewById(R.id.signup_username);
        fullname = rootView.findViewById(R.id.signup_fullname);
        phoneNumber = rootView.findViewById(R.id.signup_phone);
        email = rootView.findViewById(R.id.signup_email);
        password = rootView.findViewById(R.id.signup_password);

        nextButton = rootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        return rootView;
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspace = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError("Fill in the field!");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspace)) {
            username.setError("No White spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFullname() {
        String val = fullname.getEditText().getText().toString().trim();


        if (val.isEmpty()) {
            fullname.setError("Fill in the field!");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Fill in the field!");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^"+
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";

        if (val.isEmpty()) {
            password.setError("Fill in the field!");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Invalid password");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone() {
        String val = phoneNumber.getEditText().getText().toString().trim();


        if (val.isEmpty()) {
            phoneNumber.setError("Fill in the field!");
            return false;
        }else if(val.length() != 11){
            phoneNumber.setError("Incorrect number");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }


        @Override
        public void onClick (View v){
            if(!validateUsername() | !validateFullname() | !validateEmail() | !validatePassword()){
                return;
            }

            String[] userMainData = new String[5];
            userMainData[0] = username.getEditText().getText().toString().trim();
            userMainData[1] = fullname.getEditText().getText().toString().trim();
            userMainData[2] = email.getEditText().getText().toString().trim();
            userMainData[3] = password.getEditText().getText().toString().trim();
            userMainData[4] = "+" + phoneNumber.getEditText().getText().toString().trim();
            mListener.toNextSign(userMainData);
        }

        interface ToNextSignListener {

            void toNextSign(String[] arr);
        }

        @Override
        public void onAttach (Context context){
            super.onAttach(context);
            try {
                mListener = (ToNextSignListener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString()
                        + " должен реализовывать интерфейс OnFragmentInteractionListener");
            }
        }
    }

