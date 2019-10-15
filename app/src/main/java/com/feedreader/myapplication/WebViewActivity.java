package com.feedreader.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.feedreader.myapplication.data.News;
import com.feedreader.myapplication.data.MyApplication;
import com.feedreader.myapplication.tools.TwitterResultReceiver;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class WebViewActivity extends AppCompatActivity {

    ImageButton buttonShare;
    ImageButton buttonHome;
    ImageButton buttonLike;
    Intent homeIntent;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    String url;
    String title;
    String date;
    String filePath = Environment.getExternalStorageDirectory().getPath();
    File newsListFile = new File(filePath + "/newsList.xml");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        RSSFeedShowActivity.putLayout putlayout = new RSSFeedShowActivity.putLayout();
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
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
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
                PopupMenu popupMenu = new PopupMenu(WebViewActivity.this, buttonShare);
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

                                TweetComposer.Builder builder = new TweetComposer.Builder(WebViewActivity.this)
                                        .text("#RSSFeedGp19s2")
                                        .url(url);
                                builder.show();

                                a.onReceive(WebViewActivity.this, homeIntent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            /*
                            final TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                            final Intent intent = new ComposerActivity.Builder(WebViewActivity.this)
                                .session(twitterSession)
                                .text("a")
                                .createIntent();
                            startActivity(intent);
                            */
                        } else {

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
                homeIntent = new Intent(WebViewActivity.this, MainActivity.class);
                WebViewActivity.this.startActivity(homeIntent);
            }
        });


        /**
         * Author: Zixin Ye
         * Save data to Favourites
         */
        buttonLike = findViewById(R.id.imageButtonLike);
        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyApplication app = (MyApplication) getApplication();

                //Avoid saving Duplicate values
                if (url != null) {
                    boolean flag = true;

                    for (News news : app.getNewsList()) {
                        if (news.getUrl().equals(url)) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        app.setNews(new News(url, title, date));
                        app.getNewsList().add(app.getNews());
                    }
                }

                saveNewsList(newsListFile);
            }
        });


        ImageButton imageButtonAdd = findViewById(R.id.imageButtonAdd);
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WebViewActivity.this, AddSitesShowActivity.class);
                WebViewActivity.this.startActivity(intent);
            }
        });


        ImageButton imageButtonFavourites = findViewById(R.id.imageButtonFavorites);
        imageButtonFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WebViewActivity.this, FavouritesActivity.class);
                WebViewActivity.this.startActivity(intent);
            }
        });

    }

    public void saveNewsList(File file) {
        /*
        ArrayList<News> newsList = new ArrayList<>();
        final MyApplication app = (MyApplication) getApplication();

        for (int i = 0; i < app.getNewsList().size(); i++) {
            News news = app.getNewsList().get(i);
            if (news != null) {
                newsList.add(news);
            }
            else {
                System.out.println("fuckyeah baby");
                throw new IllegalArgumentException();
            }
        }
        */
        MyApplication app = (MyApplication) getApplication();
        ArrayList<News> newsList = app.getNewsList();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.newDocument();

            Element newsListElement = dom.createElement("newsList");

            for (int i=0;i<newsList.size();i++) {
                News news = newsList.get(i);

                Element newsElem = dom.createElement("news");

                newsElem.setAttribute("url", news.getUrl());
                newsElem.setAttribute("title", news.getTitle());
                newsElem.setAttribute("date", news.getDate());

                newsListElement.appendChild(newsElem);
            }

            Transformer t = TransformerFactory.newInstance().newTransformer();
            DOMSource ds = new DOMSource(newsListElement);
            StreamResult sr = new StreamResult(file);

            t.transform(ds, sr);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
