package com.teamLP.truth.art.WriteArticle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamLP.truth.R;
import com.teamLP.truth.art.TopArticles.TopArticles;
import com.teamLP.truth.art.model.ArticleModel;


import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class WriteArticle extends Fragment implements View.OnClickListener {

    RelativeLayout progressBar;
    protected Button button;
    protected ImageView picture1;
    TextInputLayout name, description, content;
    TextView dateArticle, ownerArticle;
    Spinner categoryArticle;
    Boolean isPhoto = false;
    Date dateNow = new Date();
    String category;
    private TopArticles.OnSelectArticleListener mListener;

    ArticleModel model;
    WriteArticlePresenter presenter;
    DatabaseReference referenceDB;



    private final int PICK_IMAGE = 1;
    SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy.MM.dd");
    private static final String NAME_USER = "name_user";

    public static WriteArticle newInstance(String nameUser) {
        Bundle args = new Bundle();
        args.putString(NAME_USER, nameUser);
        WriteArticle fragment = new WriteArticle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_article, container, false);

        init(rootView);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                category = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryArticle.setSelection(6);
            }
        };
        categoryArticle.setOnItemSelectedListener(itemSelectedListener);

        return rootView;
    }

    private void init(View rootView) {


        referenceDB = FirebaseDatabase.getInstance().getReference("Article");
        model = new ArticleModel(referenceDB);
        presenter = new WriteArticlePresenter(model);
        presenter.setView(this);

        final String[] categories = getResources().getStringArray(R.array.categories);
        String nameUser = getArguments().getString(NAME_USER);

        button = rootView.findViewById(R.id.sendArt);
        button.setOnClickListener(this);

        picture1 = rootView.findViewById(R.id.pic_article);
        picture1.setOnClickListener(this);

        name = rootView.findViewById(R.id.nameArticle);
        description = rootView.findViewById(R.id.descriptionArticle);
        content = rootView.findViewById(R.id.contentArticle);
        dateArticle = rootView.findViewById(R.id.dateArticleWrite);
        ownerArticle = rootView.findViewById(R.id.ownerArticleWrite);

        dateArticle.setText(formatForDate.format(dateNow));
        categoryArticle = rootView.findViewById(R.id.categoryArticle);

        progressBar = rootView.findViewById(R.id.loadingImage);
        progressBar.setVisibility(View.INVISIBLE);




        ownerArticle.setText(nameUser);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryArticle.setAdapter(adapter);
        // заголовок
        categoryArticle.setPrompt("Category");
    }

    public String[] getViewData(){
        String[] articleData = new String[6];
        articleData[0] = name.getEditText().getText().toString().trim();
        articleData[1] = category;
        articleData[2] = description.getEditText().getText().toString().trim();
        articleData[3] = content.getEditText().getText().toString().trim();
        articleData[4] = dateArticle.getText().toString().trim();
        articleData[5] = ownerArticle.getText().toString().trim();

        return articleData;
    }

    public void finishWriting(String nameArticle){
        categoryArticle.setSelection(6);
        name.getEditText().setText("");
        description.getEditText().setText("");
        content.getEditText().setText("");
        picture1.setImageResource(R.drawable.image_photo);

        mListener.onSelectArticle(nameArticle);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendArt:
                presenter.generateArticle();
                break;

            case R.id.pic_article:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
                isPhoto = true;
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    picture1.setImageURI(selectedImage);
                }
        }
    }

}


