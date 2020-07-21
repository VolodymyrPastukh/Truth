package com.teamLP.truth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamLP.truth.model.ArticleModel;


public class Article extends ControllerArticle {

    TextView name;
    TextView description;
    TextView content;
    TextView owner;
    TextView date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);

        name = rootView.findViewById(R.id.viewNameArticle);
        description = rootView.findViewById(R.id.viewDescriptionArticle);
        content = rootView.findViewById(R.id.viewContentArticle);
        owner = rootView.findViewById(R.id.viewOwnerArticle);
        date = rootView.findViewById(R.id.viewDateArticle);

        viewArticle(numSelectArticle);

        return rootView;
    }


    public void viewArticle(int poss){
        ArticleModel art = containerArticles.get(poss);
        name.setText(art.nameArticle);
        description.setText(art.descriptionArticle);
        content.setText(art.contentArticle);
        owner.setText(art.owner);
        date.setText(art.dateArticle);
    }
}