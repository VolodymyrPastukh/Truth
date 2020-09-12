package com.teamLP.truth.art.SelectedCategory;

import android.util.Log;
import android.view.View;

import com.teamLP.truth.art.model.ArticleData;
import com.teamLP.truth.art.model.ArticleModel;

import java.util.ArrayList;
import java.util.List;

public class SelectedCategoryPresenter {
    private SelectedCategory view;
    private ArticleModel model;

    public SelectedCategoryPresenter(ArticleModel model) {
        this.model = model;
    }

    public void setView(SelectedCategory view) {
        this.view = view;
    }

    public void loadArticles(final String category){
        view.progressBar.setVisibility(View.VISIBLE);
        model.loadArticles(new ArticleModel.LoadArticlesCallback() {
            @Override
            public void onLoad(List<ArticleData> articles) {
                List<ArticleData> selectedCategoryList = new ArrayList<ArticleData>();
                for (ArticleData el:articles) {
                    if(category.equals(el.categoryArticle)){
                        selectedCategoryList.add(el);
                    }
                }
                view.showArticle(selectedCategoryList);
                view.progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
