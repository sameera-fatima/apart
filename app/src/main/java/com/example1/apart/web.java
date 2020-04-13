package com.example1.apart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class web extends Activity {

    public WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web);

        webView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.timeanddate.com/worldclock/india");
        webView.setWebViewClient(new WebViewClient());



        //WebView webview =new WebView(this);
        //setContentView(webview);
        // webview.loadUrl("3.16.1.63:5000/");
        //webview.loadUrl("https://www.timeanddate.com/worldclock/india");

    }
}
