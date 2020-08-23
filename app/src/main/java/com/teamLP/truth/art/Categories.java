package com.teamLP.truth.art;

import android.content.Context;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamLP.truth.R;


public class Categories extends ControllerArticle {

    ListView categoryList;
    private OnSelectCategoryListener categoryListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        categoryList = rootView.findViewById(R.id.categoryList);
        final String[] categories = getResources().getStringArray(R.array.categories);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_categories, R.id.nameCategory, categories);

        categoryList.setAdapter(adapter);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String message = ((TextView) view.findViewById(R.id.nameCategory)).getText().toString();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                nameSelectCategory = message;
                categoryListener.onSelectCategory();
            }
        });
        return rootView;
    }

    interface OnSelectCategoryListener {

        void onSelectCategory();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            categoryListener = (OnSelectCategoryListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnSelectCategoryListener");
        }
    }
}