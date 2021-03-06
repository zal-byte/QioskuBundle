package com.zadev.qiosku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import Session.UserSession;

public class LandingActivity extends AppCompatActivity {
    private final static int READ_EXTERNAL_PERMISSION_CODE = 0;
    UserSession userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        AuthActivity.HideSystemUI(LandingActivity.this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        CheckExternalStoragePermission();



    }

    public void initialize()
    {
        userSession = new UserSession(LandingActivity.this);
        if(userSession.isLogin())
        {
            //Goto MainActivity
            startActivity(new Intent(LandingActivity.this, MainActivity.class));
        }else
        {
            //Need an authentication first
            startActivity(new Intent(LandingActivity.this, AuthActivity.class));
        }
        LandingActivity.this.finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == READ_EXTERNAL_PERMISSION_CODE) {//call cursor loader
            new Handler().postDelayed(this::initialize, 1500);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private  void CheckExternalStoragePermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){

            if(ContextCompat.checkSelfPermission(LandingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(LandingActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED )
                new Handler().postDelayed(this::initialize, 1500);
            else
            {

                if( shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
                {

                    Toast.makeText(this, "This apps need storage permission", Toast.LENGTH_SHORT).show();
                    CheckExternalStoragePermission();

                }else if( shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                {
                    Toast.makeText(this, "This apps need camera permission", Toast.LENGTH_SHORT).show();
                    CheckExternalStoragePermission();
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, READ_EXTERNAL_PERMISSION_CODE);

            }

        }
    }
}