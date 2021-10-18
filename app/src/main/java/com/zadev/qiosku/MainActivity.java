package com.zadev.qiosku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    MaterialToolbar main_toolbar;
    DrawerLayout main_drawer;
    NavigationView main_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        HideSystemUI();
        init();
        logic();
    }
    private void HideSystemUI()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    private void init()
    {

        main_drawer = findViewById(R.id.main_drawer);
        main_nav = findViewById(R.id.nav_view);

//        setSupportActionBar(main_toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, main_drawer, null, R.string.open, R.string.close);
        main_drawer.addDrawerListener(toggle);

        toggle.syncState();
        main_nav.setNavigationItemSelectedListener(this);
    }
    private void logic()
    {

    }

    public void onBackPressed()
    {
        if( main_drawer.isDrawerOpen(GravityCompat.START))
        {
            main_drawer.closeDrawer(GravityCompat.START);
        }else
        {
            super.onBackPressed();
        }
    }

    public void onResume() {
        super.onResume();
        HideSystemUI();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}