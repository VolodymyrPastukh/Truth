package com.teamLP.truth.art;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.teamLP.truth.Users.Login;
import com.teamLP.truth.R;

public class Content extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        TopArticles.OnSelectArticleListener,
        Categories.OnSelectCategoryListener
         {


    static final float END_SCALE = 0.7f;

    LinearLayout contentLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String owner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle startArgs = getIntent().getExtras();
        int key = startArgs.getInt("startKey");
        owner =startArgs.getString("username");


        /*-----------Hooks----------*/
        contentLayout = findViewById(R.id.contentLayout);
        drawerLayout = findViewById(R.id.drawer_layout_main);
        navigationView = findViewById(R.id.nav_view_menu);
        toolbar = findViewById(R.id.toolbar);


        /*----------Show-Items--------*/
        Menu menu = navigationView.getMenu();

        if(key > 0){
            menu.findItem(R.id.nav_login).setVisible(false);
        }
        else{
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_write_articles).setVisible(false);
        }


        /*-----------Toolbar----------*/
        setSupportActionBar(toolbar);


        /*-----------Hooks----------*/
        navigationView.bringToFront(); // робить роботу меню нормальною (гортабельною та клікабельною)
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //animateNavigationDrawer();


        /*-----------First page----------*/
        if(key > 0){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.flContent, new Categories());
            fragmentTransaction.commit();
        }
        else{
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.flContent, new Categories());
            fragmentTransaction.commit();
        }

    }






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Class fragmentClass;
            switch(item.getItemId()) {

                case R.id.nav_top_articles:
                    fragmentClass = TopArticles.class;
                    openFragment(fragmentClass);
                    break;
                case R.id.nav_write_articles:
                    //fragmentClass = WriteArticle.class;
                    fragmentManager = getSupportFragmentManager();
                    Fragment writeArticle = WriteArticle.newInstance(owner);
                    fragmentManager.beginTransaction().replace(R.id.flContent, writeArticle).commit();
                    break;
                case R.id.nav_categories:
                    fragmentClass = Categories.class;
                    openFragment(fragmentClass);
                    break;
                case R.id.nav_login:
                    Intent loginIntent = new Intent(this, Login.class);
                    startActivity(loginIntent);
                    break;
                default:
                    fragmentClass = TopArticles.class;
                    openFragment(fragmentClass);
            }


            item.setChecked(true);
            setTitle(item.getTitle());
            drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    private void openFragment(Class fragClass){
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Вставить фрагмент, заменяя любой существующий
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }


    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentLayout.setScaleX(offsetScale);
                contentLayout.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentLayout.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentLayout.setTranslationX(xTranslation);
            }
        });

    }

     @Override
     public void onBackPressed() {
         if(drawerLayout.isDrawerOpen(GravityCompat.START)){
             drawerLayout.closeDrawer(GravityCompat.START);
         }

     }


     /*----------------------------Fragment`s methods-----------------------------------*/
    /*---Select article---*/
    @Override
    public void onSelectArticle() {

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, new Article());
        fragmentTransaction.commit();

    }

    /*---Select category---*/
    public void onSelectCategory(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, new SelectedCategory());
        fragmentTransaction.commit();
    }
}
