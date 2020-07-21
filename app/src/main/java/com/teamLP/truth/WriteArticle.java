package com.teamLP.truth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.teamLP.truth.model.ArticleModel;

import java.text.SimpleDateFormat;
import java.util.Date;


public class WriteArticle extends ControllerArticle implements View.OnClickListener {

    private Button button;
    EditText name;
    EditText description;
    EditText content;
    TextView dateArticle;
    TextView ownerArticle;

    String nam, desk, cont, dat, own;

    Date dateNow = new Date();
    SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy.MM.dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_article, container, false);

        button = rootView.findViewById(R.id.sendArt);
        button.setOnClickListener(this);
        name = rootView.findViewById(R.id.nameArticle);
        description = rootView.findViewById(R.id.articleDescription);
        content = rootView.findViewById(R.id.contentArticle);
        dateArticle = rootView.findViewById(R.id.dateArticleWrite);
        ownerArticle = rootView.findViewById(R.id.ownerArticleWrite);

        dateArticle.setText(formatForDate.format(dateNow));

        return rootView;
    }


//    public void sendArticle(View view){
//
//        nam = name.getText().toString();
//        desk = description.getText().toString();
//        cont = content.getText().toString();
//        dat = dateArticle.getText().toString();
//        own = ownerArticle.getText().toString();
//
//        if( !nam.isEmpty() && !desk.isEmpty() && !cont.isEmpty()){
//            generateArticle(new Article(nam, desk, cont, dat, own));
//            name.setText("");
//            description.setText("");
//            content.setText("");
//        }
//        else{
//            Toast.makeText(getActivity(),"Fill in every row pls!!!", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sendArt) {
            nam = name.getText().toString();
            desk = description.getText().toString();
            cont = content.getText().toString();
            dat = dateArticle.getText().toString();
            own = ownerArticle.getText().toString();

            if (!nam.isEmpty() && !desk.isEmpty() && !cont.isEmpty()) {
                generateArticle(new ArticleModel(nam, desk, cont, dat, own));
                name.setText("");
                description.setText("");
                content.setText("");
            } else {
                Toast.makeText(getActivity(), "Fill in every row pls!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
