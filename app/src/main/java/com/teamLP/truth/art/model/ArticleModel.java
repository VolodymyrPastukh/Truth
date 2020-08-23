package com.teamLP.truth.art.model;

import android.graphics.Bitmap;


public class ArticleModel {
    public String nameArticle;
    public String categoryArticle;
    public String descriptionArticle;
    public String contentArticle;
    public String dateArticle;
    public String owner;
    public Bitmap bitmap;


    public ArticleModel(String nameArticle, String categoryArticle, String descriptionArticle, String contentArticle, String dateArticle, String owner, Bitmap bitmap) {
        this.nameArticle = nameArticle;
        this.categoryArticle = categoryArticle;
        this.descriptionArticle = descriptionArticle;
        this.contentArticle = contentArticle;
        this.dateArticle = dateArticle;
        this.owner = owner;
        this.bitmap = bitmap;
    }


}

