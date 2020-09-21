package com.teamLP.truth.art.SelectedCategory;


import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamLP.truth.art.TopArticles.TopArticles;
import com.teamLP.truth.art.common.ArticleAdapter;
import com.teamLP.truth.art.model.ArticleData;
import com.teamLP.truth.R;
import com.teamLP.truth.art.model.ArticleModel;

import java.util.List;


public class SelectedCategory extends Fragment {

    ListView articleList;
    RelativeLayout progressBar;
    private ArticleAdapter adapter;
    private TopArticles.OnSelectArticleListener selectArticle;
    private static final String NAME_CATEGORY = "name_category";
    private String aCategory;

    DatabaseReference referenceDB;
    private ArticleModel model;
    private SelectedCategoryPresenter presenter;

    public static SelectedCategory newInstance(String nameCategory) {
        Bundle args = new Bundle();
        args.putString(NAME_CATEGORY, nameCategory);
        SelectedCategory fragment = new SelectedCategory();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_articles, container, false);
        aCategory = getArguments().getString(NAME_CATEGORY);


        articleList = (ListView) rootView.findViewById(R.id.articlesList);
        progressBar = rootView.findViewById(R.id.prBar);
        progressBar.setVisibility(View.INVISIBLE);

        referenceDB = FirebaseDatabase.getInstance().getReference("Article");
        model = new ArticleModel(referenceDB);
        presenter = new SelectedCategoryPresenter(model);
        presenter.setView(this);
        presenter.loadArticles(aCategory);


        //Перехід на конкретну статтю
        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String message = ((TextView) view.findViewById(R.id.articleName)).getText().toString().trim();
                Log.i("Open article: ", message);
                selectArticle.onSelectArticle(message);
            }
        });

        return rootView;
    }

    public void showArticle(List<ArticleData> data){
        if(getActivity() != null) {
            adapter = new ArticleAdapter(getActivity(), data);
            articleList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            selectArticle = (TopArticles.OnSelectArticleListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }


}