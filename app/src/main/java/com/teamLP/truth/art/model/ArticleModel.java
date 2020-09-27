package com.teamLP.truth.art.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArticleModel {
    DatabaseReference referenceDB;

    public ArticleModel(DatabaseReference referenceDB) {
        this.referenceDB = referenceDB;
    }

    public void loadArticles(final LoadArticlesCallback callback) { //Метод отримання списку статей
        Log.i("Start loadArticles ---", "[        ]");
        final List<ArticleData> articles = new ArrayList<>();
        referenceDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (articles.size() > 0) {
                    articles.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ArticleData article = ds.getValue(ArticleData.class);
                    assert article != null;
                    articles.add(article);
                    Collections.sort(articles, new Comparator<ArticleData>() {
                        @Override
                        public int compare(ArticleData o1, ArticleData o2) {
                            return o2.likes - o1.likes;
                        }
                    });
                    callback.onLoad(articles);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Load Articles Error", error.toString());
            }
        });
    } //Метод отримання списку статей

    public void loadArticleData(String name, LoadArticleDataCallback callback) { //Метод отримання статті
        LoadArticleData loadArticleData = new LoadArticleData(callback);
        loadArticleData.execute(name);
    }

    public void likeArticle(String name, LikeArticleCallback callback) {//Метод отримання статті
        LikeArticle likeArticle = new LikeArticle(callback);
        likeArticle.execute(name);
    } //Метод отримання статті

    public void addArticle(ArticleData article, AddArticleCallback callback) {
        AddArticle addArticle = new AddArticle(callback);
        addArticle.execute(article);
    } //метод додавання статті

    public void uploadImage(Bitmap bitmap, String name, final UploadImageCallback callback) {

        final StorageReference referenceStore = FirebaseStorage.getInstance().getReference("ArticleImages");

        Log.i("Bitmaps == ", bitmap.toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] byteArr = baos.toByteArray();
        final StorageReference childRef = referenceStore.child(name);
        UploadTask uploadTask = childRef.putBytes(byteArr);
        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return childRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri uploadImageUri = task.getResult();
                assert uploadImageUri != null;
                String uriString = uploadImageUri.toString();
                Log.i("Uri == ", uriString);
                callback.onUploadImage(uriString);
            }
        });


    } //метод завантаження зображення

    public interface LoadArticlesCallback {
        void onLoad(List<ArticleData> articles);
    } //Callback переліку статей

    public interface LoadArticleDataCallback { //Callback статті
        void onSelect(ArticleData article);
    }

    public interface LikeArticleCallback { //Callback лайку
        void onLike();
    }

    public interface AddArticleCallback {
        void onAdd();
    }

    public interface UploadImageCallback {
        void onUploadImage(String uri);
    }


    /*
    Клас для асинхронної дії отримання статті
     */
    class LoadArticleData extends AsyncTask<String, Void, Void> {

        LoadArticleDataCallback callback;

        LoadArticleData(LoadArticleDataCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(final String... strings) {

            Query query = referenceDB.orderByChild("nameArticle").equalTo(strings[0]);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ArticleData articleData = snapshot.child(strings[0]).getValue(ArticleData.class);
                        Log.i("Get Article ---", "Article exists");
                        callback.onSelect(articleData);
                    } else {
                        Log.i("Get Article ---", "Article does not exist");
                        callback.onSelect(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Get Article ---", "Error: " + error.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("Load article Data ---", "complete!");
        }
    }

    /*
    Клас для асинхронної дії лайка
     */
    class LikeArticle extends AsyncTask<String, Void, Void> { //Клас для асинхронної дії ставлення лайку

        LikeArticleCallback callback;

        public LikeArticle(LikeArticleCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(String... strings) {

            referenceDB.child(strings[0]).child("likes").runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                    Integer lk = currentData.getValue(Integer.class);
                    if (lk == null) {
                        return Transaction.success(currentData);
                    }
                    Integer res = lk + 1;
                    currentData.setValue(res);
                    return Transaction.success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                    if (error == null) {
                        callback.onLike();
                    } else {
                        Log.e("Like ---", "Like error");
                    }
                }
            });
            return null;
        }
    }


    /*
    Клас для асинхронної дії додавання статті
     */
    class AddArticle extends AsyncTask<ArticleData, Void, Void> {

        AddArticleCallback callback;

        public AddArticle(AddArticleCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(ArticleData... articleData) {
            referenceDB.child(articleData[0].nameArticle).setValue(articleData[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            callback.onAdd();
        }
    }

}
