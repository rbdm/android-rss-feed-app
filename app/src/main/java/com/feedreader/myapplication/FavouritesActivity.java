package com.feedreader.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.ArrayList;


public class FavouritesActivity extends AppCompatActivity {
    ArrayList<String> collectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Intent intent = getIntent();
        String url = intent.getStringExtra("webLink");
        if (url != null) {
            collectionList.add(url);
        }

        setContentView(R.layout.favourites_layout);


        // collectionList.add(url);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/delete_menu.xmlfile.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        deleteAll();
        return super.onOptionsItemSelected(item);
    }

    private void deleteAll() {

    }


}

