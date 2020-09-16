package com.teamLP.truth.art.profile;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamLP.truth.Users.model.UserData;
import com.teamLP.truth.Users.model.UserModel;
import com.teamLP.truth.art.common.RecyclerCustomAdapter;
import com.teamLP.truth.art.model.ArticleData;
import com.teamLP.truth.art.model.ArticleModel;


import java.util.ArrayList;
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

    public void loadAuthorArticles(final String _username){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Article");
        ArticleModel articleModel = new ArticleModel(ref);
        articleModel.loadArticles(new ArticleModel.LoadArticlesCallback() {
            @Override
            public void onLoad(List<ArticleData> articles) {
                List<ArticleData> data = new ArrayList<>();
                int likes=0;
                for(ArticleData art: articles){
                    if(art.getOwner().equals(_username)){
                        data.add(art);
                        likes += art.likes;
                    }
                }
                view.viewProfileArticles(data, likes);
            }
        });
    }


}
