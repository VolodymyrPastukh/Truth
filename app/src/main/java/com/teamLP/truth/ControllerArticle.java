package com.teamLP.truth;


import androidx.fragment.app.Fragment;

import com.teamLP.truth.model.ArticleModel;

import java.util.ArrayList;
import java.util.List;


public class ControllerArticle extends Fragment {
    public static List<ArticleModel> containerArticles = new ArrayList<ArticleModel>();

    public static int numSelectArticle;

    public void generateArticle(ArticleModel article){
        containerArticles.add(article);
    }


}
