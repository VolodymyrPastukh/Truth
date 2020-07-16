package com.teamLP.truth;

import android.annotation.SuppressLint;


import androidx.fragment.app.Fragment;

import com.teamLP.truth.model.Article;

import java.util.ArrayList;
import java.util.List;


public class ControllerArticle extends Fragment {
    public static List<Article> containerArticles = new ArrayList<Article>();


    public void generateArticle(Article article){
        containerArticles.add(article);
    }


}
