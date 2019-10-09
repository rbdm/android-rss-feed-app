package com.feedreader.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.feedreader.myapplication.tools.FavouritesAdapter;
import com.feedreader.myapplication.data.MyApplication;
import com.feedreader.myapplication.tools.RSSFeedparser;

public class FavouritesActivity extends AppCompatActivity {
    RSSFeedparser parser = new RSSFeedparser();
    private FavouritesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.favourites_layout);

        MyApplication app = (MyApplication) getApplication();


        adapter = new FavouritesAdapter(this, app.getContentList());
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

    }





    public void returnHome(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void addSitePage(View v) {
        Intent intent = new Intent(getApplicationContext(), AddSitesShowActivity.class);
        startActivity(intent);
    }

}

