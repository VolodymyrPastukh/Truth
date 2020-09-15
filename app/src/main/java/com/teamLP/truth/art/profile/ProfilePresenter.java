package com.teamLP.truth.art.profile;

import android.util.Log;

import com.teamLP.truth.Users.model.UserData;
import com.teamLP.truth.Users.model.UserModel;


import java.util.List;

public class ProfilePresenter {
    UserModel model;
    Profile view;

    public ProfilePresenter(UserModel model) {
        this.model = model;
    }

    public void setView(Profile view) {
        this.view = view;
    }

//    public void loadUserData(){
//        view.loadOwnProfile();
//    }

    public void loadUserData(final String _username){
        model.loadUsers(new UserModel.LoadUserDataCallback() {
            @Override
            public void onLoadUsers(List<UserData> users) {
                for(UserData ud:users){
                    if(ud.getUsername().equals(_username)){
                        view.loadUserProfile(ud);
                    }
                }
            }

        });
    }


}
