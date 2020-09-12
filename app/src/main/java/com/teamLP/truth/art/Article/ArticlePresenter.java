package com.teamLP.truth.art.Article;

import com.teamLP.truth.art.model.ArticleData;
import com.teamLP.truth.art.model.ArticleModel;

public class ArticlePresenter {
    private Article view;
    private final ArticleModel model;

    public ArticlePresenter(ArticleModel model) {
        this.model = model;
    }

    public void setView(Article view) {
        this.view = view;
    }

    public void loadArticleData(String name){
        model.loadArticleData(name, new ArticleModel.LoadArticleDataCallback() {
            @Override
            public void onSelect(ArticleData article) {
                view.viewArticle(article);
            }
        });
    }

    public void likeArticle(String name){
        model.likeArticle(name, new ArticleModel.LikeArticleCallback() {
            @Override
            public void onLike() {
                view.setLikes();
            }
        });
    }
}
