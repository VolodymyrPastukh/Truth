package com.teamLP.truth.art;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamLP.truth.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutUs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutUs extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME = "name";

    String visitorName;
    TextView title;


    public static AboutUs newInstance(String _name) {
        AboutUs fragment = new AboutUs();
        Bundle args = new Bundle();
        args.putString(NAME, _name);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        if (getArguments() != null) {
            visitorName = getArguments().getString(NAME);

        }
        title = rootView.findViewById(R.id.visitorName);
        title.setText("Hi, " + visitorName);

        rootView.findViewById(R.id.gitLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/VolodymyrPastukh"));
                startActivity(browserIntent);
            }
        });
        return rootView;
    }
}