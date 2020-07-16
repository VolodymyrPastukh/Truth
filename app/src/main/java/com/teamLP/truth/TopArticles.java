package com.teamLP.truth;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.teamLP.truth.model.Article;

import java.util.List;


public class TopArticles extends ControllerArticle {

    ListView articleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_articles, container, false);

        articleList = (ListView) rootView.findViewById(R.id.articlesList);
        ArticleAdapter adapter = new ArticleAdapter(getActivity(), containerArticles);
        articleList.setAdapter(adapter);

        return rootView;
    }
}

class ArticleAdapter extends ArrayAdapter<Article>
{
    private Context context;
    public List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles)
    {
        super(context, R.layout.list_articles, articles);
        this.context   = context;
        this.articles = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View     view       = inflater.inflate(R.layout.list_articles, parent, false);
        Article article = articles.get(position);
        TextView articleNameView = (TextView) view.findViewById(R.id.articleName);
        TextView articleDescriptionView = (TextView) view.findViewById(R.id.articleDescription);
        TextView articleOwnerView = (TextView) view.findViewById(R.id.articleOwner);
        TextView articleDateView = (TextView) view.findViewById(R.id.articleDate);


        articleNameView.setText(article.nameArticle);
        articleDescriptionView.setText(article.descriptionArticle);
        articleOwnerView.setText(article.owner);
        articleDateView.setText(article.dateArticle);

        return view;
    }

    @Override
    public int getCount()
    {
        return articles.size();
    }
}
