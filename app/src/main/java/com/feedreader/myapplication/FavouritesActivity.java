package com.feedreader.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class FavouritesActivity extends AppCompatActivity {
    ArrayList<String> collectionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.favourites_layout);
        Intent intent = getIntent();
        String url = intent.getStringExtra("webLink");


        collectionList.add("111");
        collectionList.add("1211");
        collectionList.add("11331");
        collectionList.add("114421");

        if (url != null) {

            boolean flag = true;

            if (collectionList != null) {
                for (int i = 0; i < collectionList.size(); i++) {
                    if (url.equals(collectionList.get(i))) {
                        flag = false;
                    }
                }
                if (flag) {
                    collectionList.add(url);
                }
            }
        }


        TextView favouritesView = new TextView(this);


        ArrayAdapter itemsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, collectionList);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

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

