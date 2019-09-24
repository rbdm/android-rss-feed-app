package com.feedreader.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    int number=0;
    ArrayList<String> rssLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity);
    }

    public void putweb(int number,TextView web){
        LinearLayout layout=(LinearLayout) findViewById(R.id.layout);
        Button new_button=new Button(this);
        new_button.setText("http:"+web.getText().toString());
        new_button.setLayoutParams(new ViewGroup.LayoutParams(1500, 200));
        new_button.setX(0);
        new_button.setY(550);
        layout.addView(new_button);
        rssLists.add("http:"+web.getText().toString());

        /*WebView browser = new WebView(this);
        browser.loadUrl("http:"+web.getText().toString());
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        browser.setLayoutParams(new ViewGroup.LayoutParams(1500, 400));
        browser.setX(0);
        browser.setY(500+100*number);
        layout.addView(browser);
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });*/
    }
    public void addSite(View v){
        number+=1;
        TextView web=(TextView) findViewById(R.id.editText1);
        putweb(number,web);

    }
    public void Delete(View v){
        TextView dweb=(TextView) findViewById(R.id.editText);
    }

    public void onClick(View view) {
        float y=view.getY();
        int count=0;
        while (y>=550)
        {
            y-=200;
            count++;
        }


    }

}