package com.teamLP.truth.art;


import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamLP.truth.art.model.ArticleModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ControllerArticle extends Fragment {
    public static List<ArticleModel> containerArticles = new ArrayList<ArticleModel>();

    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    DatabaseReference refArticle = rootNode.getReference("Article");


    public void generateArticle(ArticleModel article){

        refArticle.child(article.nameArticle).setValue(article);
    }

    protected void getDataArticle(final ArrayAdapter<ArticleModel> adapter){
        ValueEventListener articleListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(containerArticles.size() > 0){
                    containerArticles.clear();
                }
                for(DataSnapshot ds : snapshot.getChildren()){
                    ArticleModel article = ds.getValue(ArticleModel.class);
                    assert article != null;
                    containerArticles.add(article);
                    Collections.sort(containerArticles, new Comparator<ArticleModel>() {
                        @Override
                        public int compare(ArticleModel o1, ArticleModel o2) {
                            return o2.likes - o1.likes;
                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Dataaaa Bazocha vpala", Toast.LENGTH_SHORT).show();
            }
        };
        refArticle.addValueEventListener(articleListener);
    }



}
