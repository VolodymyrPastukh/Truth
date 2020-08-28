package com.teamLP.truth.art;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teamLP.truth.art.model.ArticleModel;
import com.teamLP.truth.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class WriteArticle extends ControllerArticle implements View.OnClickListener {

    private Button button;
    private ImageView picture1;
    TextInputLayout name, description, content;
    TextView dateArticle, ownerArticle;
    Spinner categoryArticle;
    String[] articleData;
    Boolean isPhoto = false;
    Date dateNow = new Date();
    String category;
    Uri uploadImageUri;



    private final int PICK_IMAGE = 1;
    SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy.MM.dd");
    private StorageReference mStorageRef;
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
        mStorageRef = FirebaseStorage.getInstance().getReference("ArticleImages");

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

        articleData = new String[6];


        ownerArticle.setText(nameUser);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryArticle.setAdapter(adapter);
        // заголовок
        categoryArticle.setPrompt("Category");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendArt:
                if (validateFields()) {

                    articleData[0] = name.getEditText().getText().toString().trim();
                    articleData[1] = category;
                    articleData[2] = description.getEditText().getText().toString().trim();
                    articleData[3] = content.getEditText().getText().toString().trim();
                    articleData[4] = dateArticle.getText().toString().trim();
                    articleData[5] = ownerArticle.getText().toString().trim();

                    if (isPhoto) {
                        uploadImage();

                    } else {
                        generateArticle(new ArticleModel(articleData));
                    }

                    categoryArticle.setSelection(6);
                    name.getEditText().setText("");
                    description.getEditText().setText("");
                    content.getEditText().setText("");
                    picture1.setImageResource(R.drawable.image_photo);
                }else {
                    Toast.makeText(getActivity(), "Fill in every row pls!!!", Toast.LENGTH_SHORT).show();
                }

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

    private void uploadImage() {

        Bitmap bitty = ((BitmapDrawable) picture1.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitty.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] byteArr = baos.toByteArray();
        final StorageReference childRef = mStorageRef.child(articleData[0]);
        UploadTask uploadTask = childRef.putBytes(byteArr);
        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return childRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadImageUri = task.getResult();
                assert uploadImageUri != null;
                generateArticle(new ArticleModel(articleData, uploadImageUri.toString()));
            }
        });

    }

    private boolean validateFields() {
        String valName, valDesc, valCont;
        valName = name.getEditText().getText().toString().trim();
        valDesc = description.getEditText().getText().toString().trim();
        valCont = content.getEditText().getText().toString().trim();

        if (valName.isEmpty()) {
            name.setError("Fill this field!");
            return false;
        }else if(valName.length()>20){
            name.setError("Name is too large");
            return false;
        } else if (valDesc.isEmpty()) {
            description.setError("Fill this field!");
            return false;
        } else if (valCont.isEmpty()) {
            content.setError("Fill this field!");
            return false;
        } else {
            name.setErrorEnabled(false);
            description.setErrorEnabled(false);
            content.setErrorEnabled(false);
            return true;
        }
    }
}


