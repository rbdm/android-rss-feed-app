package com.feedreader.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;

import com.feedreader.myapplication.data.MyApplication;
import com.feedreader.myapplication.tools.FavouritesAdapter;
import com.feedreader.myapplication.tools.RSSFeedParser;


/**
 * Author: Zixin Ye
 * This class is the Activity for Favourites
 */
public class FavouritesActivity extends AppCompatActivity {
    RSSFeedParser parser = new RSSFeedParser();
    private FavouritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.favourites_layout);

        ImageButton imageButtonHome = findViewById(R.id.imageButtonHome);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(FavouritesActivity.this, MainActivity.class);
                FavouritesActivity.this.startActivity(homeIntent);
            }
        });

        ImageButton imageButtonAdd = findViewById(R.id.imageButtonAdd);
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavouritesActivity.this, AddSitesShowActivity.class);
                FavouritesActivity.this.startActivity(intent);
            }
        });


        MyApplication app = (MyApplication) getApplication();
        adapter = new FavouritesAdapter(this, app.getNewsList());
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

    }


}

