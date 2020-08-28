package com.teamLP.truth.art.model;


import android.net.Uri;

import java.util.Random;


public class ArticleModel {
    public String nameArticle;
    public String categoryArticle;
    public String descriptionArticle;
    public String contentArticle;
    public String dateArticle;
    public String owner;
    public int likes;
    public String image;

    public ArticleModel(){

    }

    public ArticleModel(String[] articleData){
        nameArticle = articleData[0];
        categoryArticle = articleData[1];
        descriptionArticle = articleData[2];
        contentArticle = articleData[3];
        dateArticle = articleData[4];
        owner = articleData[5];
        image = null;
        randomLikes();
    }

    public ArticleModel(String[] articleData, String image) {
        nameArticle = articleData[0];
        categoryArticle = articleData[1];
        descriptionArticle = articleData[2];
        contentArticle = articleData[3];
        dateArticle = articleData[4];
        owner = articleData[5];
        this.image = image;
        randomLikes();
    }

    private void randomLikes(){
        likes = new Random().nextInt(138 + 1) * new Random().nextInt(30);
    }



}

