package com.zadev.qiosku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class AuthActivity extends AppCompatActivity {
    MaterialTextView txt_register,txt_info;
    LinearLayout hidden_view,login_view;
    MaterialCardView main_cardview;
    MaterialButton btn_login, btn_signup;


    //Editable stuff
    TextInputEditText edt_name_signup, edt_email_signup,edt_address_signup,edt_phone_signup,edt_username_signup,edt_password_signup, edt_username_login, edt_password_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        logic();
    }
    public void init(){
        txt_info = findViewById(R.id.txt_info);
        txt_register = findViewById(R.id.txt_register);
        hidden_view = findViewById(R.id.hidden_view);
        login_view = findViewById(R.id.login_view);
        main_cardview = findViewById(R.id.main_cardview);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        //editable stuff
        //Signup
        edt_name_signup = findViewById(R.id.edt_name_signup);
        edt_email_signup = findViewById(R.id.edt_email_signup);
        edt_address_signup = findViewById(R.id.edt_address_signup);
        edt_phone_signup = findViewById(R.id.edt_phone_signup);
        edt_username_signup = findViewById(R.id.edt_username_signup);
        edt_password_signup = findViewById(R.id.edt_password_signup);
        //Login
        edt_username_login = findViewById(R.id.edt_username_login);
        edt_password_login = findViewById(R.id.edt_password_login);

    }
    public void logic()
    {
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( hidden_view.getVisibility() == View.VISIBLE)
                {
                    TransitionManager.beginDelayedTransition(main_cardview, new AutoTransition());
                    hidden_view.setVisibility(View.GONE);
                    login_view.setVisibility(View.VISIBLE);
                    btn_login.setVisibility(View.VISIBLE);
                    btn_signup.setVisibility(View.GONE);
                    txt_info.setText("Doesn't have any account ?,");
                    txt_register.setText(" Register.");
                }else
                {
                    TransitionManager.beginDelayedTransition(main_cardview, new AutoTransition());
                    hidden_view.setVisibility(View.VISIBLE);
                    login_view.setVisibility(View.GONE);
                    btn_login.setVisibility(View.GONE);
                    btn_signup.setVisibility(View.VISIBLE);
                    txt_info.setText("Already have an account ?,");
                    txt_register.setText(" Login.");
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = edt_username_login.getText().toString();
                final String password = edt_password_login.getText().toString();
                if( username.length() < 3 )
                {
                    edt_username_login.setError("Please fill username correctly.");
                }else{
                    if( password.length() < 3 )
                    {
                        edt_password_login.setError("Please fill password correctly.");
                    }else
                    {
                        //Continue
                        Toast.makeText(AuthActivity.this, "Hello World !.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}