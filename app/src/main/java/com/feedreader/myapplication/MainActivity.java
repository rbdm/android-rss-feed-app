package com.feedreader.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ArrayList<RSSElement> a = new ArrayList<>();
    RSSFeedparser parser = new RSSFeedparser();
    private RecyclerView mRecyclerView;
    private ArrayList<HashMap<String, String>> resultItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity);
        //LitePal初始化
        LitePal.initialize(this);
    }

    public void addSitePage(View v) {
        Intent intent = new Intent(getApplicationContext(), addSitesShow.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    public void openFavourites(View v) {
        Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
        startActivity(intent);
    }

}