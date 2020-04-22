package com.example1.apart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

public class Login_Form extends AppCompatActivity {
    Handler mHandler = new Handler();
    EditText txtid;
    Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__form);
        getSupportActionBar().setTitle("Login Form");


        txtid = (EditText) findViewById(R.id.User_ID);
        btn_login = findViewById(R.id.login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_id = txtid.getText().toString();
                int finalvalue = Integer.parseInt(user_id);
                finalvalue = finalvalue - 1000;


                //Intent i = new Intent(Login_Form.this, MapsActivity.class);
                Intent i = new Intent(Login_Form.this, MapsActivity.class);
                i.putExtra("key", finalvalue);
                i.putExtra("key1",finalvalue);
                startActivity(i);

            }
        });
    }


        public void btn_Login (View view){

             startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }

        public void btn_Signup_Form (View view){

            startActivity(new Intent(getApplicationContext(), Signup_Form.class));

        }

    }






