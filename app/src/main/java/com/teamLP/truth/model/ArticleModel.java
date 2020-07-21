package com.teamLP.truth.model;

public class ArticleModel {
    public static int idArticle;
    public String nameArticle;
    public String descriptionArticle;
    public String contentArticle;
    public String dateArticle;
    public String owner;



    public ArticleModel(String nameArticle, String descriptionArticle, String contentArticle, String dateArticle, String owner) {
        idArticle++;
        this.nameArticle = nameArticle;
        this.descriptionArticle = descriptionArticle;
        this.contentArticle = contentArticle;
        this.dateArticle = dateArticle;
        this.owner = owner;
    }


}
