package com.teamLP.truth.art;


import androidx.fragment.app.Fragment;


import com.teamLP.truth.art.model.ArticleModel;

import java.util.ArrayList;
import java.util.List;


public class ControllerArticle extends Fragment {
    public static List<ArticleModel> containerArticles = new ArrayList<ArticleModel>();

    public static String nameSelectCategory;
    public static String nameSelectArticle;
    public static String nameOwner;

    public void generateArticle(ArticleModel article){
        containerArticles.add(article);
    }


}
