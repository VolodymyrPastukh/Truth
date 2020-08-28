package com.teamLP.truth.art;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamLP.truth.R;


public class Profile extends Fragment {

   TextView username, fullname, email, phone, gender, age, ownInfo;


    private static final String PHONE_USER = "phone_user";

    public static Profile newInstance(String phoneUser) {
        Bundle args = new Bundle();
        args.putString(PHONE_USER, phoneUser);
        Profile fragment = new Profile();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        final String phoneUser = getArguments().getString(PHONE_USER);

        username = rootView.findViewById(R.id.profile_username);
        fullname = rootView.findViewById(R.id.profile_fullname);
        email = rootView.findViewById(R.id.profile_email);
        phone = rootView.findViewById(R.id.profile_phone);
        gender = rootView.findViewById(R.id.profile_gender);
        age = rootView.findViewById(R.id.profile_age);
        ownInfo = rootView.findViewById(R.id.profile_ownInfo);

        Query takeUser = FirebaseDatabase.getInstance().getReference("User").orderByChild("phoneNumber").equalTo(phoneUser);

        takeUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String systemUsername = snapshot.child(phoneUser).child("username").getValue(String.class);
                    String systemFullname = snapshot.child(phoneUser).child("fullname").getValue(String.class);
                    String systemEmail = snapshot.child(phoneUser).child("email").getValue(String.class);
                    String systemPhone = snapshot.child(phoneUser).child("phoneNumber").getValue(String.class);
                    String systemGender = snapshot.child(phoneUser).child("gender").getValue(String.class);
                    String systemAge = snapshot.child(phoneUser).child("age").getValue(String.class);
                    String systemOwnInfo = snapshot.child(phoneUser).child("owninfo").getValue(String.class);

                    username.setText(systemUsername);
                    fullname.setText(fullname.getText()+ " " + systemFullname);
                    email.setText(email.getText()+ " " + systemEmail);
                    phone.setText(phone.getText()+ " " + systemPhone);
                    gender.setText(gender.getText()+ " " + systemGender);
                    age.setText(age.getText()+ " " + systemAge);
                    ownInfo.setText(ownInfo.getText() + " " + systemOwnInfo);

                }else {
                    Toast.makeText(getActivity(),"No such user(((", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"????????(", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}