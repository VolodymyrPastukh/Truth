package com.teamLP.truth.art.WriteArticle;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.teamLP.truth.R;
import com.teamLP.truth.art.model.ArticleData;
import com.teamLP.truth.art.model.ArticleModel;

public class WriteArticlePresenter {
    WriteArticle view;
    ArticleModel model;

    public WriteArticlePresenter(ArticleModel model) {
        this.model = model;
    }


    public void setView(WriteArticle view) {
        this.view = view;
    }

    public void generateArticle(){
        String[] articleData = view.getViewData();
        if(validateFields()){
            if(view.isPhoto){
                uploadImage(articleData);
            }else{
                addArticle(new ArticleData(articleData));
            }
        }
    }

    private void addArticle(ArticleData article){
        model.addArticle(article, new ArticleModel.AddArticleCallback() {
            @Override
            public void onAdd() {
                view.cleanFields();
            }
        });
    }

    private void uploadImage(final String[] articleData){
        view.progressBar.setVisibility(View.VISIBLE);
        Bitmap bitmap = ((BitmapDrawable) view.picture1.getDrawable()).getBitmap();
        model.uploadImage(bitmap, articleData[0], new ArticleModel.UploadImageCallback() {
            @Override
            public void onUploadImage(String uri) {
                addArticle(new ArticleData(articleData, uri));
                view.progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private boolean validateFields() {
        String valName, valDesc, valCont;
        valName = view.name.getEditText().getText().toString().trim();
        valDesc = view.description.getEditText().getText().toString().trim();
        valCont = view.content.getEditText().getText().toString().trim();

        if (valName.isEmpty()) {
            view.name.setError("Fill this field!");
            return false;
        }else if(valName.length()>20){
            view.name.setError("Name is too large");
            return false;
        } else if (valDesc.isEmpty()) {
            view.description.setError("Fill this field!");
            return false;
        } else if (valCont.isEmpty()) {
            view.content.setError("Fill this field!");
            return false;
        } else {
            view.name.setErrorEnabled(false);
            view.description.setErrorEnabled(false);
            view.content.setErrorEnabled(false);
            return true;
        }
    }
}
