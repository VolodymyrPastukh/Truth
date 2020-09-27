package com.teamLP.truth.art.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.teamLP.truth.R;
import com.teamLP.truth.art.model.ArticleData;

import java.util.List;

public class ArticleAdapter extends ArrayAdapter<ArticleData> {
    private Context context;
    public List<ArticleData> articles;

    public ArticleAdapter(Context context, List<ArticleData> articles) {
        super(context, R.layout.list_articles, articles);
        this.context = context;
        this.articles = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_articles, parent, false);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context)); //Image Loader
        ImageLoader imageLoader = ImageLoader.getInstance();

        ArticleData article = articles.get(position);
        TextView articleNameView = (TextView) view.findViewById(R.id.articleName);
        TextView articleCategoryView = (TextView) view.findViewById(R.id.articleCategory);
        TextView articleDescriptionView = (TextView) view.findViewById(R.id.articleDescription);
        TextView articleOwnerView = (TextView) view.findViewById(R.id.articleOwner);
        TextView articleDateView = (TextView) view.findViewById(R.id.articleDate);
        ImageView articlePictureView = (ImageView) view.findViewById(R.id.articleMainPicture);


        articleNameView.setText(article.nameArticle);
        articleCategoryView.setText(article.categoryArticle);
        articleDescriptionView.setText(article.descriptionArticle);
        articleOwnerView.setText(article.owner);
        articleDateView.setText(article.dateArticle);
        if (article.image != null) {
            articlePictureView.setVisibility(View.VISIBLE);
            imageLoader.displayImage(article.getImage(), articlePictureView); //Load image
        } else {
            articlePictureView.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public int getCount() {
        return articles.size();
    }
}