package com.teamLP.truth.art.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamLP.truth.R;
import com.teamLP.truth.Users.SessionManager;
import com.teamLP.truth.Users.model.UserData;
import com.teamLP.truth.Users.model.UserModel;
import com.teamLP.truth.art.common.RecyclerCustomAdapter;
import com.teamLP.truth.art.model.ArticleData;
import com.teamLP.truth.art.model.ArticleModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Profile extends Fragment {

    RecyclerView recyclerView;
   TextView username, fullname, email, phone, gender, age, ownInfo, myLikes;
   DatabaseReference referenceDB;
   UserModel model;
   ProfilePresenter presenter;
   ArticleModel articleModel;



    private static final String USERNAME = "username";


    public static Profile newInstance(String username) {
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        Profile fragment = new Profile();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        init(rootView);

        assert getArguments() != null;
        final String _username = getArguments().getString(USERNAME);

        presenter.loadUserData(_username);
        presenter.loadAuthorArticles(_username);



        return rootView;
    }

    private void init(View rootView){
        username = rootView.findViewById(R.id.profile_username);
        fullname = rootView.findViewById(R.id.profile_fullname);
        email = rootView.findViewById(R.id.profile_email);
        phone = rootView.findViewById(R.id.profile_phone);
        gender = rootView.findViewById(R.id.profile_gender);
        age = rootView.findViewById(R.id.profile_age);
        ownInfo = rootView.findViewById(R.id.profile_ownInfo);
        myLikes = rootView.findViewById(R.id.my_likes);
        recyclerView = rootView.findViewById(R.id.recyclerList);

        referenceDB = FirebaseDatabase.getInstance().getReference("User");
        model = new UserModel(referenceDB);
        presenter = new ProfilePresenter(model);
        presenter.setView(this);

    }



    public void loadUserProfile(UserData user){
        username.setText(user.getUsername());
        fullname.setText("Name: " + user.getFullname());
        email.setText("Email: " + user.getEmail());
        phone.setText("Phone: " + user.getPhoneNumber());
        gender.setText("Gender: " + user.getGender());
        age.setText("Age: " + user.getAge());
        ownInfo.setText("About me: " + user.getOwninfo());
    }

    public void viewProfileArticles(List<ArticleData> articleData, int likes){
        if(getActivity() != null) {
            myLikes.setText("My Likes: " + likes);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            SnapHelper snapHelper = new PagerSnapHelper();
            recyclerView.setOnFlingListener(null);
            snapHelper.attachToRecyclerView(recyclerView);
            RecyclerCustomAdapter recyclerCustomAdapter = new RecyclerCustomAdapter(getActivity(), articleData);
            recyclerView.setAdapter(recyclerCustomAdapter);
        }
    }


}