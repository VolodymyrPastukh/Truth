package com.teamLP.truth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamLP.truth.model.ArticleModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    private final int PICK_IMAGE = 1;
    String nam, desk, cont, dat, own;
    Bitmap bit;

    Date dateNow = new Date();
    SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy.MM.dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_article, container, false);

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
                    generateArticle(new ArticleModel(nam, desk, cont, dat, own, bit));
                    name.setText("");
                    description.setText("");
                    content.setText("");
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
