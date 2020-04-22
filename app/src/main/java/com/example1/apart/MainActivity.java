package com.example1.apart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static int Time_Out = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, Login_Form.class);
                startActivity(homeIntent);
                finish();

            }
        }, Time_Out);


    }
}




