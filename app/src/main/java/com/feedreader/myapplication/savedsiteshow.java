package com.feedreader.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class savedsiteshow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.savedsite_layout);
    }
    public void clickBbc(View v){
        Intent intent = new Intent(getApplicationContext(), RSSfeedshow.class);
        intent.putExtra("url", v.getTag().toString());
        startActivity(intent);
    }
    public void clickCnn(View v){
        Intent intent = new Intent(getApplicationContext(), RSSfeedshow.class);
        intent.putExtra("url", v.getTag().toString());
        startActivity(intent);
    }
    public void clickNbc(View v){
        Intent intent = new Intent(getApplicationContext(), RSSfeedshow.class);
        intent.putExtra("url", v.getTag().toString());
        startActivity(intent);
    }
}
