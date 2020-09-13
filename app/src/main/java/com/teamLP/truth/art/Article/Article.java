package com.teamLP.truth.art.Article;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.teamLP.truth.R;
import com.teamLP.truth.art.model.ArticleData;
import com.teamLP.truth.art.model.ArticleModel;


public class Article extends Fragment {

    TextView name;
    TextView category;
    TextView description;
    TextView content;
    TextView owner;
    TextView date;
    TextView likes;
    ImageView picture;
    ImageView like;



    DatabaseReference referenceDB;
    private ArticleModel model;
    private ArticlePresenter presenter;

    private static final String NAME_ARTICLE = "name_article";
    private String aName;

    public static Article newInstance(String nameArticle) {
        Bundle args = new Bundle();
        args.putString(NAME_ARTICLE, nameArticle);
        Article fragment = new Article();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);

        init(rootView);
        presenter.loadArticleData(aName);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.likeArticle(aName);
            }
        });

        return rootView;
    }

    private void init(View rootView){
        aName = getArguments().getString(NAME_ARTICLE);

        name = rootView.findViewById(R.id.viewNameArticle);
        category = rootView.findViewById(R.id.viewCategoryArticle);
        description = rootView.findViewById(R.id.viewDescriptionArticle);
        content = rootView.findViewById(R.id.viewContentArticle);
        owner = rootView.findViewById(R.id.viewOwnerArticle);
        date = rootView.findViewById(R.id.viewDateArticle);
        picture = rootView.findViewById(R.id.viewPictureArticle);
        likes = rootView.findViewById(R.id.viewLikes);
        like = rootView.findViewById(R.id.like);

        referenceDB = FirebaseDatabase.getInstance().getReference("Article");
        model = new ArticleModel(referenceDB);
        presenter = new ArticlePresenter(model);
        presenter.setView(this);

    }


    protected void viewArticle(ArticleData selectArticle) {
        ArticleData art = selectArticle;
        name.setText(art.nameArticle);
        category.setText("[" + art.categoryArticle + "]");
        description.setText(art.descriptionArticle);
        content.setText(art.contentArticle);
        owner.setText(art.owner);
        date.setText(art.dateArticle);
        likes.setText(Integer.toString(art.likes));
        if (selectArticle.image != null) {
            picture.setVisibility(View.VISIBLE);
            Picasso.get().load(selectArticle.image).into(picture);
        } else {
            picture.setVisibility(View.GONE);
        }

    }


    protected void setLikes(){
        int lukas = Integer.parseInt(likes.getText().toString().trim());
         likes.setText(String.valueOf(lukas + 1));
         like.setClickable(false);
         like.setImageResource(R.drawable.icon_heart_black);
    }

}