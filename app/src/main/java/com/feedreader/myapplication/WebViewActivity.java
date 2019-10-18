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
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.feedreader.myapplication.data.MyApplication;
import com.feedreader.myapplication.data.RSSElement;
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

    // initialize variables
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
        super.onCreate(savedInstanceState);

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

        /**
         * Author: Mirhady Dorodjatun
         * This part defines the behaviour of the share button.
         * It aims to share the url content to Facebook and Twitter using their respective APIs
         * @param
         */
        // initialize facebook and twitter SDK
        // in case of facebook it simply initializes on context, where the keys are defined in manifest.
        // for twitter, we build an auth instance by passing consumer key and secret to an auth builder.
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        TwitterConfig.Builder tc = new TwitterConfig.Builder(WebViewActivity.this);
        TwitterAuthConfig auth = new TwitterAuthConfig("@strings/twitter_consumer_key", "@strings/twitter_consumer_secret");
        TwitterConfig twitterConfig = tc
                .twitterAuthConfig(auth)
                .build();
        Twitter.initialize(this);
        
        // initialize facebook dialog
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        // defines event on buttonShare.onClick()
        buttonShare = findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // inflates popup menu from R.menu.share_menu
                PopupMenu popupMenu = new PopupMenu(WebViewActivity.this, buttonShare);
                popupMenu.getMenuInflater().inflate(R.menu.share_menu, popupMenu.getMenu());

                // set listener for each popup menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        // sharing to facebook
                        if (menuItem.getItemId() == R.id.shareToFacebook) {

                            // generate hashtag with our group ID
                            ShareHashtag facebookHashtag = new ShareHashtag.Builder().setHashtag("#RSSFeedGp19s2").build();

                            // calls an instance of ShareLinkContent by utilizing its Builder() function
                            // to define the share parameters
                            ShareLinkContent slc = new ShareLinkContent.Builder()
                                    .setShareHashtag(facebookHashtag)
                                    .setQuote(getIntent().getStringExtra("title"))
                                    .setContentUrl(Uri.parse(getIntent().getStringExtra("url")))
                                    .build();
                            // show the facebook content after user login
                            if (ShareDialog.canShow(ShareLinkContent.class)) {
                                shareDialog.show(slc);
                            }

                        }
                        // sharing to twitter
                        else if (menuItem.getItemId() == R.id.shareToTwitter) {

                            try {
                                // get url from string passed from previous intent
                                URL url = new URL(getIntent().getStringExtra("url"));

                                // builds an instance of tweetcomposer using its respective builder
                                TweetComposer.Builder tc = new TweetComposer.Builder(WebViewActivity.this);
                                TweetComposer.Builder builder = tc
                                        // generate hashtag with our group ID
                                        .text("#RSSFeedGp19s2")
                                        .url(url);
                                builder.show();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        // returns home when home button is clicked
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

                    for (RSSElement rss : app.getRSSElementList()) {
                        if (rss.getLink().equals(url)) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        app.setRssElement(new RSSElement(title, url, date));
                        app.getRSSElementList().add(app.getRssElement());
                        Toast.makeText(
                                WebViewActivity.this,
                                "Successfully added:"+"\r\n"+title+" to favourites",
                                Toast.LENGTH_SHORT
                        ).show();
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
        MyApplication app = (MyApplication) getApplication();
        ArrayList<RSSElement> newsList = app.getRSSElementList();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.newDocument();

            Element newsListElement = dom.createElement("newsList");

            for (int i=0;i<newsList.size();i++) {
                RSSElement news = newsList.get(i);

                Element newsElem = dom.createElement("news");

                newsElem.setAttribute("title", news.getTitle());
                newsElem.setAttribute("link", news.getLink());
                newsElem.setAttribute("pubdate", news.getPubDate());

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
