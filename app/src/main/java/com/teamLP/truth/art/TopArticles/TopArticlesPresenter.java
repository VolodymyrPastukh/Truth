package com.teamLP.truth.art.TopArticles;

import android.view.View;

import com.teamLP.truth.art.model.ArticleData;
import com.teamLP.truth.art.model.ArticleModel;

import java.util.List;

public class TopArticlesPresenter {
    private TopArticles view;
    private final ArticleModel model;

    public TopArticlesPresenter(ArticleModel model){
        this.model = model;
    }

    public void attachView(TopArticles view){
        this.view = view;
    }


    public void loadArticles(){
        view.progressBar.setVisibility(View.VISIBLE);
        model.loadArticles(new ArticleModel.LoadArticlesCallback() {
            @Override
            public void onLoad(List<ArticleData> articles) {
                view.showArticles(articles);
                view.progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
