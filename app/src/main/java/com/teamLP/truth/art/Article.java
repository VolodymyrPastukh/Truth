package com.teamLP.truth.art;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamLP.truth.R;
import com.teamLP.truth.art.model.ArticleModel;


public class Article extends ControllerArticle {

    TextView name;
    TextView category;
    TextView description;
    TextView content;
    TextView owner;
    TextView date;
    ImageView picture;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);

        name = rootView.findViewById(R.id.viewNameArticle);
        category = rootView.findViewById(R.id.viewCategoryArticle);
        description = rootView.findViewById(R.id.viewDescriptionArticle);
        content = rootView.findViewById(R.id.viewContentArticle);
        owner = rootView.findViewById(R.id.viewOwnerArticle);
        date = rootView.findViewById(R.id.viewDateArticle);
        picture = rootView.findViewById(R.id.viewPictureArticle);

        viewArticle(selectArticle(nameSelectArticle));

        return rootView;
    }


    public void viewArticle(ArticleModel selectArticle){
        ArticleModel art = selectArticle;
        name.setText(art.nameArticle);
        category.setText("[" + art.categoryArticle + "]");
        description.setText(art.descriptionArticle);
        content.setText(art.contentArticle);
        owner.setText(art.owner);
        date.setText(art.dateArticle);
        picture.setImageBitmap(art.bitmap);

    }

    public ArticleModel selectArticle(String selectName){
        int i=0;
        ArticleModel selectArt;
        selectArt = containerArticles.get(i);
        while(!selectName.equals(selectArt.nameArticle)){
            selectArt = containerArticles.get(i);
            i++;
        }
        Log.i("Article" + i, selectArt.nameArticle);

        return selectArt;
    }
}