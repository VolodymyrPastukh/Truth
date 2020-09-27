package com.teamLP.truth.art.model;


import android.net.Uri;

import java.util.Random;


public class ArticleData {
    public String nameArticle;
    public String categoryArticle;
    public String descriptionArticle;
    public String contentArticle;
    public String dateArticle;
    public String owner;
    public int likes;
    public String image;

    public ArticleData(){

    }

    public ArticleData(String[] articleData){
        nameArticle = articleData[0];
        categoryArticle = articleData[1];
        descriptionArticle = articleData[2];
        contentArticle = articleData[3];
        dateArticle = articleData[4];
        owner = articleData[5];
        image = null;
        randomLikes();
    }

    public ArticleData(String[] articleData, String image) {
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

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameArticle() {
        return nameArticle;
    }

    public String getCategoryArticle() {
        return categoryArticle;
    }

    public String getDescriptionArticle() {
        return descriptionArticle;
    }

    public String getContentArticle() {
        return contentArticle;
    }

    public String getDateArticle() {
        return dateArticle;
    }

    public String getOwner() {
        return owner;
    }

    public int getLikes() {
        return likes;
    }

    public String getImage() {
        return image;
    }
}

