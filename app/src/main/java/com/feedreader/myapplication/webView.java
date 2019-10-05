package com.feedreader.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

public class webView  extends AppCompatActivity {

    ImageButton buttonShare;
    Button buttonHome;
    Intent shareIntent, homeIntent;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.web_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebView browser = findViewById(R.id.webView);
        browser.loadUrl(url);
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.supportZoom();
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        buttonShare = findViewById(R.id.buttonShare);
        //initialize FB
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentDescription("testCD")
                        .setContentTitle("testCT")
                        .setQuote(getIntent().getStringExtra("title"))
                        .setContentUrl(Uri.parse(getIntent().getStringExtra("url")))
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    shareDialog.show(linkContent);
                }

                /*shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getIntent().getStringExtra("title"));
                shareIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("url"));
                startActivity(Intent.createChooser(shareIntent, "Share"));*/
            }
        });

        buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeIntent = new Intent(webView.this, MainActivity.class);
                webView.this.startActivity(homeIntent);
            }
        });

    }
}
