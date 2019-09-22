package com.feedreader.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
    }


    public void addSite(View v){
        TextView ds=(TextView) findViewById(R.id.textView);
        ds.setText("Discover new site:");
    }
}
