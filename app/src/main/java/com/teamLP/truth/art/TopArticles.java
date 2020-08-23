package com.teamLP.truth.art;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamLP.truth.R;
import com.teamLP.truth.art.model.ArticleModel;

import java.util.List;


public class TopArticles extends ControllerArticle {

    ListView articleList;
    private OnSelectArticleListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_articles, container, false);

        articleList = (ListView) rootView.findViewById(R.id.articlesList);
        ArticleAdapter adapter = new ArticleAdapter(getActivity(), containerArticles);
        articleList.setAdapter(adapter);

        //Перехід на конкретну статтю
        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String message = ((TextView) view.findViewById(R.id.articleName)).getText().toString();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                nameSelectArticle = message;
                mListener.onSelectArticle();
            }
        });

        return rootView;
    }

    interface OnSelectArticleListener {

        void onSelectArticle();
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












class ArticleAdapter extends ArrayAdapter<ArticleModel>
{
    private Context context;
    public List<ArticleModel> articles;

    public ArticleAdapter(Context context, List<ArticleModel> articles)
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
        ArticleModel article = articles.get(position);
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
        articlePictureView.setImageBitmap(article.bitmap);

        return view;
    }

    @Override
    public int getCount()
    {
        return articles.size();
    }
}
