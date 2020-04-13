package com.example1.apart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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

public class Login_Form extends AppCompatActivity {
    EditText txtid;
    Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__form);
        getSupportActionBar().setTitle("Login Form");


        txtid = (EditText) findViewById(R.id.User_ID);
        btn_login = findViewById(R.id.login);
        btn_login.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
           /* final Handler handler = new Handler(){

                public void call(){
                    //startActivity(new Intent(getApplicationContext(), web.class));
                }


            };*/

          /*  Runnable r = new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));

                    handler.sendEmptyMessage(0);
                }
            };
            Thread threead = new Thread(r);
            threead.start();*/



        // WebView myWebView = (WebView) findViewById(R.id.webview);
        //myWebView.loadUrl("https://www.keil.com/download/");






           /* Runnable r = new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.sendEmptyMessage(0);
                }
            };
            Thread threead = new Thread(r);
            threead.start();*/


        // WebView webview =new WebView();
        // setContentView(webview);
        //webview.loadUrl("https://www.keil.com/download/");




            String user_id = txtid.getText().toString();
            // startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            int finalvalue = Integer.parseInt(user_id);
            finalvalue = finalvalue - 1000;


            //Intent i = new Intent(Login_Form.this, MapsActivity.class);
            Intent i = new Intent(Login_Form.this, MapsActivity.class);
            i.putExtra("key", finalvalue);
            startActivity(i);

        }


                                        /* public void btn_Signup_Form(View view) {
                                             startActivity(new Intent(getApplicationContext(), Signup_Form.class));
                                         }*/
    }
        );

    /*class map implements Runnable{
        @Override
        public void run() {
            for(int i = 0;i<5;i++) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        Toast.makeText(Login_Form.this, "1st activity", Toast.LENGTH_SHORT).show();
                    }
                });
                //startActivity(new Intent(getApplicationContext(), web.class));
                Toast.makeText(Login_Form.this, "2nd activity", Toast.LENGTH_SHORT).show();
            }
                try {
                    Thread.sleep(1000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }*/
    }




    public void btn_Login(View view) {

        startActivity(new Intent(getApplicationContext(), MapsActivity.class));


    }

    public void btn_Signup_Form(View view) {

        startActivity(new Intent(getApplicationContext(), Signup_Form.class));

    }

    public void btn_web(View view) {

    startActivity(new Intent(getApplicationContext(), web.class));


   }
}