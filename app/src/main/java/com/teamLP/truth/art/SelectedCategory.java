package com.teamLP.truth.art;


import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.teamLP.truth.art.model.ArticleModel;
import com.teamLP.truth.R;

import java.util.ArrayList;
import java.util.List;


public class SelectedCategory extends ControllerArticle {

    ListView categoryArticleList;
    private TopArticles.OnSelectArticleListener selectArticle;
    private static final String NAME_CATEGORY = "name_category";

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
        View rootView = inflater.inflate(R.layout.fragment_selected_category, container, false);


        categoryArticleList = (ListView) rootView.findViewById(R.id.selectedCategoryList);
        CategoryArticleAdapter adapter = new CategoryArticleAdapter(getActivity(), getCategoryList(getArguments().getString(NAME_CATEGORY)));
        categoryArticleList.setAdapter(adapter);

        //Перехід на конкретну статтю
        categoryArticleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String message = ((TextView) view.findViewById(R.id.articleName)).getText().toString().trim();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                selectArticle.onSelectArticle(message);
            }
        });

        return rootView;
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

    public List<ArticleModel> getCategoryList(String category){
        List<ArticleModel> selectedCategoryList = new ArrayList<ArticleModel>();
        for (ArticleModel el:containerArticles) {
            if(category.equals(el.categoryArticle)){
                selectedCategoryList.add(el);
                Log.d("New List el - " + el.nameArticle, " | has category ---> " + el.categoryArticle);
            }
        }
        return selectedCategoryList;
    }

}












class CategoryArticleAdapter extends ArrayAdapter<ArticleModel>
{
    private Context context;
    public List<ArticleModel> articles;

    public CategoryArticleAdapter(Context context, List<ArticleModel> articles)
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
        TextView articleDescriptionView = (TextView) view.findViewById(R.id.articleDescription);
        TextView articleOwnerView = (TextView) view.findViewById(R.id.articleOwner);
        TextView articleDateView = (TextView) view.findViewById(R.id.articleDate);
        ImageView articlePictureView = (ImageView) view.findViewById(R.id.articleMainPicture);


        articleNameView.setText(article.nameArticle);
        articleDescriptionView.setText(article.descriptionArticle);
        articleOwnerView.setText(article.owner);
        articleDateView.setText(article.dateArticle);
        if (article.image != null) {
            articlePictureView.setVisibility(View.VISIBLE);
            Picasso.get().load(article.image).into(articlePictureView);
        } else {
            articlePictureView.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public int getCount()
    {
        return articles.size();
    }
}