package com.feedreader.myapplication;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity);
    }

    public void addSitePage(View v) {
     Intent intent =new Intent(getApplicationContext(),addSitesShow.class);
     startActivity(intent);
    }

        //Skip to Favourites activity
    public void openFavourites(View view) {

        Intent i = new Intent(this, FavouritesActivity.class);
        startActivity(i);

    }

}