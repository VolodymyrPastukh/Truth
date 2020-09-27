package com.teamLP.truth.art.TopArticles;

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
import com.teamLP.truth.R;
import com.teamLP.truth.art.common.ArticleAdapter;
import com.teamLP.truth.art.model.ArticleData;
import com.teamLP.truth.art.model.ArticleModel;


import java.util.List;


public class TopArticles extends Fragment {

    ListView articleList;
    RelativeLayout progressBar;
    private OnSelectArticleListener mListener;
    private ArticleAdapter adapter;

    DatabaseReference referenceDB;
    protected ArticleModel model;
    protected TopArticlesPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_articles, container, false);
        articleList = (ListView) rootView.findViewById(R.id.articlesList);
        progressBar = rootView.findViewById(R.id.prBar);
        progressBar.setVisibility(View.INVISIBLE);

        referenceDB = FirebaseDatabase.getInstance().getReference("Article");
        model = new ArticleModel(referenceDB);
        presenter = new TopArticlesPresenter(model);
        presenter.attachView(this);
        presenter.loadArticles();


        //Перехід на конкретну статтю
        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String message = ((TextView) view.findViewById(R.id.articleName)).getText().toString().trim();
                Log.i("Open article: ", message);
                mListener.onSelectArticle(message);
            }
        });

        return rootView;
    }


    public void showArticles(List<ArticleData> data) {
        if (getActivity() != null) {
            adapter = new ArticleAdapter(getActivity(), data);
            articleList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public interface OnSelectArticleListener {

        void onSelectArticle(String nameArticle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnSelectArticleListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }


}

