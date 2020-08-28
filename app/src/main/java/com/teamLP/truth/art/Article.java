package com.teamLP.truth.art;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;
import com.teamLP.truth.R;
import com.teamLP.truth.art.model.ArticleModel;


public class Article extends ControllerArticle implements View.OnClickListener {

    TextView name;
    TextView category;
    TextView description;
    TextView content;
    TextView owner;
    TextView date;
    TextView likes;
    ImageView picture;
    ImageView like;

    DatabaseReference ref;

    private static final String NAME_ARTICLE = "name_article";

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
        viewArticle(selectArticle(getArguments().getString(NAME_ARTICLE)));
        return rootView;
    }

    private void init(View rootView){
        name = rootView.findViewById(R.id.viewNameArticle);
        category = rootView.findViewById(R.id.viewCategoryArticle);
        description = rootView.findViewById(R.id.viewDescriptionArticle);
        content = rootView.findViewById(R.id.viewContentArticle);
        owner = rootView.findViewById(R.id.viewOwnerArticle);
        date = rootView.findViewById(R.id.viewDateArticle);
        picture = rootView.findViewById(R.id.viewPictureArticle);
        likes = rootView.findViewById(R.id.viewLikes);
        like = rootView.findViewById(R.id.like);
        like.setOnClickListener(this);

        ref = FirebaseDatabase.getInstance()
                .getReference("Article")
                .child(getArguments().getString(NAME_ARTICLE))
                .child("likes");

    }


    public void viewArticle(ArticleModel selectArticle) {
        ArticleModel art = selectArticle;
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

    public ArticleModel selectArticle(String selectName) {
        int i = 0;
        ArticleModel selectArt;
        selectArt = containerArticles.get(i);
        while (!selectName.equals(selectArt.nameArticle)) {
            selectArt = containerArticles.get(i);
            i++;
        }
        Log.i("Article" + i, selectArt.nameArticle);

        return selectArt;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.like:

                ref.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        Integer lk = currentData.getValue(Integer.class);
                        if(lk == null){
                            return Transaction.success(currentData);
                        }
                        Integer res = lk + 1;
                        currentData.setValue(res);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        if(error == null){
                            setLikes();
                            Toast.makeText(getActivity(), "Likeeeeeeeeee", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("Like", "Like error");
                        }
                    }
                });
                break;
        }
    }

    private void setLikes(){
        int lukas = Integer.parseInt(likes.getText().toString().trim());
         likes.setText(String.valueOf(lukas + 1));
         like.setClickable(false);
         like.setImageResource(R.drawable.icon_heart_black);
    }

}