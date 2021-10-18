package com.zadev.qiosku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AsyncRequestQueue;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.zadev.qiosku.Helper.Server;
import com.zadev.qiosku.Helper.Snacc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Session.UserSession;

public class AuthActivity extends AppCompatActivity {
    MaterialTextView txt_register,txt_info;
    LinearLayout hidden_view,login_view;
    MaterialCardView main_cardview;
    MaterialButton btn_login, btn_signup;

    //Another class
    Server server;
    Snacc snacc;
    UserSession userSession;
    //Editable stuff
    TextInputEditText edt_name_signup, edt_email_signup,edt_address_signup,edt_phone_signup,edt_username_signup,edt_password_signup, edt_username_login, edt_password_login;

    //Map
    //Login hashmap
    HashMap<String, String> loginParam = new HashMap<>();
    HashMap<String, String> signupParam = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        HideSystemUI(AuthActivity.this);

        //init class
        server = new Server();
        snacc = new Snacc(AuthActivity.this);
        userSession = new UserSession(AuthActivity.this);
        //

        init();
        logic();
    }
    public static void HideSystemUI(Activity activity)
    {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

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
                   less();
                }else
                {
                    expand();
                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = edt_name_signup.getText().toString();
                final String phone = edt_phone_signup.getText().toString();
                final String address= edt_address_signup.getText().toString();
                final String email = edt_email_signup.getText().toString();
                final String password = edt_password_signup.getText().toString();
                final String username = edt_username_signup.getText().toString();

                //Demnnn :V
                if( name.length() < 3 )
                {
                    edt_name_signup.setError("Please fill name correctly.");
                }else
                {
                    if( phone.length() < 10 )
                    {
                        edt_phone_signup.setError("Please input phone number correctly.");
                    }else
                    {
                        if( address.length() < 5 )
                        {
                            edt_address_signup.setError("Where are you from ?");
                        }else
                        {
                            if( email.length() < 3 )
                            {
                                edt_email_signup.setError("Please fill email correctly.");
                            }else
                            {
                                if( password.length() < 5 )
                                {
                                    edt_password_signup.setError("You need a strong password not less than 5 characters !");
                                }else
                                {
                                    if( username.length() < 2 )
                                    {
                                        edt_username_signup.setError("Not less than 2 characters !");
                                    }else
                                    {
                                        signupParam.put("token","VjJ0V2FrNVhUbk5qUm1oUFZteEtiMVpxU2xOTlZuQkhZVVZPWVdGNlJsWlZNV2gyVUZFOVBRPT0=");
                                        signupParam.put("u_name", name);
                                        signupParam.put("u_username",username);
                                        signupParam.put("u_password",password);
                                        signupParam.put("req","authSignup");
                                        signupParam.put("u_address",address);
                                        signupParam.put("u_email", email);
                                        signupParam.put("u_phone", phone);

                                        if( signupParam.size() > 0 )
                                        {
                                            //Go ahead
                                            signup();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Auth
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
                        loginParam.put("u_username",username);
                        loginParam.put("u_password",password);
                        loginParam.put("token","VjFkMGFrNVhTblJTYkdoUFZteGFjRlJYTlZOak1XeDBaSHBTYkZKVVJuaFdSbEYzVUZFOVBRPT0=");
                        loginParam.put("req","authLogin");

                        if( loginParam.size() > 0 )
                        {
                            login();
                        }
                    }
                }
            }
        });
    }

    private void expand()
    {
        TransitionManager.beginDelayedTransition(main_cardview, new AutoTransition());
        hidden_view.setVisibility(View.VISIBLE);
        login_view.setVisibility(View.GONE);
        btn_login.setVisibility(View.GONE);
        btn_signup.setVisibility(View.VISIBLE);
        txt_info.setText("Already have an account ?,");
        txt_register.setText(" Login.");
    }
    private void less()
    {
        TransitionManager.beginDelayedTransition(main_cardview, new AutoTransition());
        hidden_view.setVisibility(View.GONE);
        login_view.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.VISIBLE);
        btn_signup.setVisibility(View.GONE);
        txt_info.setText("Doesn't have any account ?,");
        txt_register.setText(" Register.");
    }

    private void clearSignup()
    {
        edt_name_signup.setText("");
        edt_username_signup.setText("");
        edt_password_signup.setText("");
        edt_phone_signup.setText("");
        edt_email_signup.setText("");
        edt_address_signup.setText("");
    }

    private void signupParse(String json) throws JSONException {
        JSONObject jsObject = new JSONObject(json);
        JSONArray jsonArray = jsObject.getJSONArray("signup");
        for( int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);
            if( object.getBoolean("signup_status") == true)
            {

                clearSignup();
                less();

            }else
            {
                snacc.Snackme(object.getString("msg"), false);
            }
        }
    }

    private void loginParse(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("login");

        for( int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);
            if( object.getBoolean("login_status") == true )
            {
                Toast.makeText(this, "Login Successfuly.", Toast.LENGTH_SHORT).show();
                userSession.setIsLogin(true, object.getString("u_name"), object.getString("u_address"), object.getString("u_phone"), object.getString("u_email"), object.getString("role"), object.getString("token"));
                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(intent);
                AuthActivity.this.finish();
            }else
            {
                snacc.Snackme(object.getString("msg"), false);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void signup()
    {
        StringRequest sr = new StringRequest(Request.Method.POST, server.api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            signupParse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Volley_Response_> "+error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return signupParam;
            }
        };
        RequestQueue asyncRequestQueue = (RequestQueue) Volley.newRequestQueue(this);
        asyncRequestQueue.add(sr);
    }
    @SuppressLint("StaticFieldLeak")
    private void login()
    {
        StringRequest sr = new StringRequest(Request.Method.POST, server.api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            loginParse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Volley_Response_> "+error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return loginParam;
            }
        };
        RequestQueue asyncRequestQueue = (RequestQueue) Volley.newRequestQueue(this);
        asyncRequestQueue.add(sr);
    }

}