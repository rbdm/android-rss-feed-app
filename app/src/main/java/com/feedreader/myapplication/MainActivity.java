package com.feedreader.myapplication;



import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


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
        new_button.setText(web.getText().toString());
        new_button.setLayoutParams(new ViewGroup.LayoutParams(1500, 200));
        new_button.setX(0);
        new_button.setY(550);
        layout.addView(new_button);
        rssLists.add(web.getText().toString());
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
        TextView web=(TextView) findViewById(R.id.editText1);
        putweb(number,web);

        Intent intent = new Intent(this,RSSfeedshow.class);
        intent.putExtra("url","http://"+web.getText().toString());
        startActivity(intent);



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