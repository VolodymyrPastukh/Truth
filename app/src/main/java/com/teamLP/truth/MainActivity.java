package com.teamLP.truth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*-----------Hooks----------*/
        drawerLayout = findViewById(R.id.drawer_layout_main);
        navigationView = findViewById(R.id.nav_view_menu);
        toolbar = findViewById(R.id.toolbar);

        /*----------Show-Items--------*/

        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);

        /*-----------Toolbar----------*/
        setSupportActionBar(toolbar);

        /*-----------Hooks----------*/
        navigationView.bringToFront(); // робить роботу меню нормальною (гортабельною та клікабельною)
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        fragmentManager = getSupportFragmentManager();
////        fragmentTransaction = fragmentManager.beginTransaction();
////        fragmentTransaction.add(R.id.flContent, new TopArticles());
////        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



            Fragment fragment = null;
            Class fragmentClass;
            switch(item.getItemId()) {

                case R.id.nav_top_articles:
                    fragmentClass = TopArticles.class;
                    break;
                case R.id.nav_write_articles:
                    fragmentClass = WriteArticle.class;
                    break;
                default:
                    fragmentClass = TopArticles.class;
            }


            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Вставить фрагмент, заменяя любой существующий
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

            // Выделение существующего элемента выполнено с помощью
            // NavigationView
            item.setChecked(true);
            // Установить заголовок для action bar'а
            setTitle(item.getTitle());
            // Закрыть navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START);


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
