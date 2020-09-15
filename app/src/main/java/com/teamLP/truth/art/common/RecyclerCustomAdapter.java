package com.teamLP.truth.art.common;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.teamLP.truth.R;
import com.teamLP.truth.art.model.ArticleData;

import java.util.List;

public class RecyclerCustomAdapter extends RecyclerView.Adapter<RecyclerCustomAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private List<ArticleData> articleDataList;

    public RecyclerCustomAdapter(Context context, List<ArticleData> articleData) {
        this.articleDataList = articleData;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_articles_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerCustomAdapter.ViewHolder holder, int position) {
        ArticleData article = articleDataList.get(position);
        holder.title.setText(article.getNameArticle());
        holder.date.setText(article.getDateArticle());
        holder.likes.setText("LIKES: " + String.valueOf(article.getLikes()));
        if(article.getImage() != null){
            holder.image.setVisibility(View.VISIBLE);
            Picasso.get().load(article.getImage()).into(holder.image);
        }else{
            holder.image.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return articleDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView title, date, likes;
        ViewHolder(View view){
            super(view);
            image = (ImageView)view.findViewById(R.id.lImage);
            title = (TextView) view.findViewById(R.id.lTitle);
            date = (TextView) view.findViewById(R.id.lDate);
            likes = (TextView) view.findViewById(R.id.lLikes);
        }
    }
}
