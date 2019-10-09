package com.feedreader.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.feedreader.myapplication.data.MyApplication;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity);
        LinearLayout layout = findViewById(R.id.LinearLayout);

        MyApplication app = (MyApplication) getApplication();
        if (app.getButtonList() != null) {
            boolean flag = true;

            for (Button b1 : app.getButtonList()) {

                for (Button b2 : app.getButtonList()) {
                    if (b1 == b2) {
                        flag = false;
                    }
                }

                if (flag) {
                    layout.addView(b1);
                }

            }
        }
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