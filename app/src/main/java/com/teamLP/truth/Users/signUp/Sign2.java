package com.teamLP.truth.Users.signUp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.teamLP.truth.R;

import java.util.Calendar;


public class Sign2 extends Fragment implements View.OnClickListener {


    Button completeButton;
    completeSignIn mListener;
    DatePicker age;
    TextInputLayout ownInfo;
    RadioGroup gender;
    String selectedGender;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign2, container, false);

        age = rootView.findViewById(R.id.signup_age);
        ownInfo = rootView.findViewById(R.id.signup_ownInfo);
        gender = rootView.findViewById(R.id.signup_gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioMale:
                        selectedGender = "Male";
                        break;
                    case R.id.radioFemale:
                        selectedGender = "Female";
                        break;
                    case R.id.radioAlien:
                        selectedGender = "Alien";
                        break;
                }
            }
        });

        completeButton = rootView.findViewById(R.id.completeButton);
        completeButton.setOnClickListener(this);

        return rootView;
    }

    private boolean validateGender() {
        if (gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getActivity(), "Select gender!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = age.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 18) {
            Toast.makeText(getActivity(), "You can`t write articles)", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onClick(View v) {
        if (!validateAge() | !validateGender()) {
            return;
        }

        int day = age.getDayOfMonth();
        int month = age.getMonth() + 1;
        int year = age.getYear();
        String[] userOtherData = new String[3];
        userOtherData[0] = selectedGender;
        userOtherData[1] = ownInfo.getEditText().getText().toString().trim();
        userOtherData[2] = "+" + day + "." + month + "." + year;

        mListener.toComleteSignIn(userOtherData);
    }

    interface completeSignIn {
        void toComleteSignIn(String[] arr);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (completeSignIn) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }
}