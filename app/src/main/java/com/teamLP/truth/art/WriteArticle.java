package com.teamLP.truth.art;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.teamLP.truth.art.model.ArticleModel;
import com.teamLP.truth.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class WriteArticle extends ControllerArticle implements View.OnClickListener {

    private Button button;
    private ImageView picture1;
    EditText name;
    EditText description;
    EditText content;
    TextView dateArticle;
    TextView ownerArticle;
    Spinner categoryArticle;


    private final int PICK_IMAGE = 1;
    String nam, cat, desk, cont, dat, own;
    Bitmap bit;

    Date dateNow = new Date();
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

        final String[] categories = getResources().getStringArray(R.array.categories);
        String nameUser = getArguments().getString(NAME_USER);

        button = rootView.findViewById(R.id.sendArt);
        button.setOnClickListener(this);

        picture1 = rootView.findViewById(R.id.pic_article);
        picture1.setOnClickListener(this);

        name = rootView.findViewById(R.id.nameArticle);
        description = rootView.findViewById(R.id.articleDescription);
        content = rootView.findViewById(R.id.contentArticle);
        dateArticle = rootView.findViewById(R.id.dateArticleWrite);
        ownerArticle = rootView.findViewById(R.id.ownerArticleWrite);

        dateArticle.setText(formatForDate.format(dateNow));
        categoryArticle = rootView.findViewById(R.id.categoryArticle);


        ownerArticle.setText(nameUser);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryArticle.setAdapter(adapter);
        // заголовок
        categoryArticle.setPrompt("Category");

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                cat = item;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryArticle.setSelection(6);
            }
        };
        categoryArticle.setOnItemSelectedListener(itemSelectedListener);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendArt:
                nam = name.getText().toString();
                desk = description.getText().toString();
                cont = content.getText().toString();
                dat = dateArticle.getText().toString();
                own = ownerArticle.getText().toString();


                if (!nam.isEmpty() && !desk.isEmpty() && !cont.isEmpty()) {
                    generateArticle(new ArticleModel(nam, cat, desk, cont, dat, own, bit));
                    name.setText("");
                    description.setText("");
                    content.setText("");
                    categoryArticle.setSelection(6);
                    bit = null;
                    picture1.setImageResource(R.drawable.image_icon);
                } else {
                    Toast.makeText(getActivity(), "Fill in every row pls!!!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.pic_article:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;


        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    bit = bitmap;
                    picture1.setImageBitmap(bitmap);
                }
        }
    }
}
