package com.feedreader.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.feedreader.myapplication.data.PetContract;
import com.feedreader.myapplication.data.FeedReaderDbHelper;

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

        // Setup FAB to open EditorActivity
        Button like = findViewById(R.id.buttonLike);
        if (like != null) {

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getIntent();
                    startActivity(intent);
                }
            });
        }


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

        displayDatabaseInfo();
    }


    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);


        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + PetContract.PetEntry.TABLE_NAME, null);
        // Cursor cursor = db.rawQuery("SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME, null);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("Number of rows in Favourites database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

/*    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME, null);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }*/


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

