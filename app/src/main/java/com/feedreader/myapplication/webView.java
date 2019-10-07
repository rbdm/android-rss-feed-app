package com.feedreader.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.URL;

public class webView extends AppCompatActivity {

    ImageButton buttonShare;
    Button buttonHome;
    Button buttonLike;
    Intent shareIntent, homeIntent, FavouritesIntent;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        RSSfeedshow.putLayout putlayout = new RSSfeedshow.putLayout();
        putlayout.execute(url);*/


        super.onCreate(savedInstanceState);

        // initialize facebook SDK
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        // initialize twitter SDK
        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(new TwitterAuthConfig("@strings/twitter_consumer_key", "@strings/twitter_consumer_secret"))
                .build();
        Twitter.initialize(this);

        setContentView(R.layout.web_view);
        final Intent intent = getIntent();
        url = intent.getStringExtra("url");
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

        // initialize facebook dialog
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // set popup menu to get options from res/menu/share_menu
                PopupMenu popupMenu = new PopupMenu(webView.this, buttonShare);
                popupMenu.getMenuInflater().inflate(R.menu.share_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.shareToFacebook) {
                            ShareHashtag facebookHashtag = new ShareHashtag.Builder().setHashtag("#RSSFeedGp19s2").build();

                            ShareLinkContent facebookContent = new ShareLinkContent.Builder()
                                    .setContentDescription("testCD")
                                    .setContentTitle("testCT")
                                    .setShareHashtag(facebookHashtag)
                                    .setQuote(getIntent().getStringExtra("title"))
                                    .setContentUrl(Uri.parse(getIntent().getStringExtra("url")))
                                    .build();
                            if (ShareDialog.canShow(ShareLinkContent.class)) {
                                shareDialog.show(facebookContent);
                            }
                        } else if (menuItem.getItemId() == R.id.shareToTwitter) {
                            TwitterResultReceiver a = new TwitterResultReceiver();

                            try {
                                URL url = new URL(getIntent().getStringExtra("url"));

                                TweetComposer.Builder builder = new TweetComposer.Builder(webView.this)
                                        .text("#RSSFeedGp19s2")
                                        .url(url);
                                builder.show();

                                a.onReceive(webView.this, homeIntent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            /*
                            final TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                            final Intent intent = new ComposerActivity.Builder(webView.this)
                                .session(twitterSession)
                                .text("a")
                                .createIntent();
                            startActivity(intent);
                            */
                        } else if (menuItem.getItemId() == R.id.shareToWeChat) {

                        }

                        return true;
                    }
                });

                popupMenu.show();

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

        buttonLike = findViewById(R.id.buttonLike);
        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                LinearLayout layout = findViewById(R.id.linearLayout);
                layout.removeAllViews();*/
                FavouritesIntent = new Intent(webView.this, FavouritesActivity.class);
                FavouritesIntent.putExtra("url", url);
                webView.this.startActivity(FavouritesIntent);
            }
        });


    }
}

