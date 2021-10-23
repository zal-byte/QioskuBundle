package com.zadev.qiosku;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import Fragments.CartFrag;
import Fragments.MainFrag;
import Fragments.PayFrag;
import Session.UserSession;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout main_drawer;
    NavigationView main_nav;
    MaterialToolbar main_toolbar;
    BottomNavigationView main_bottom_nav_view;
    //session

    UserSession userSession;

    //end of session
    Bundle instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.instance = savedInstanceState;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        HideSystemUI();


        init();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.cus_action_bar);
        logic();

    }
    private void HideSystemUI()
    {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }
    private void init()
    {
        userSession = new UserSession(MainActivity.this);

        main_drawer = findViewById(R.id.main_drawer);
        main_nav = findViewById(R.id.nav_view);
        main_toolbar = findViewById(R.id.main_toolbar);
        main_bottom_nav_view = findViewById(R.id.main_bottom_nav_view);
        setSupportActionBar(main_toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, main_drawer, null, R.string.open, R.string.close);
        main_drawer.addDrawerListener(toggle);

        toggle.syncState();
        main_nav.setNavigationItemSelectedListener(this);



        if( this.instance == null)
        {
            main_bottom_nav_view.setSelectedItemId(R.id.bottom_nav_home);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new MainFrag()).commit();
        }


    }
    private void logic()
    {
        main_bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment sf = null;
                switch (item.getItemId())
                {
                    case R.id.bottom_nav_payment:
                        sf = new PayFrag();
                        break;
                    case R.id.bottom_nav_home:
                        sf = new MainFrag();
                        break;
                    case R.id.bottom_nav_cart:
                        sf = new CartFrag();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, sf).commit();
                return true;
            }
        });
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
    @Override
    public void onResume() {
        super.onResume();

    }

    private void logout()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Logout ?");
        dialog.setMessage("Are you sure want to logout??");
        dialog.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            userSession.setLogout();

            startActivity(new Intent(MainActivity.this, AuthActivity.class));

            MainActivity.this.finish();
        });
        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.logout_menu:
                logout();
                break;
        }
        return false;
    }
}