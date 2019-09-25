package com.feedreader.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;




public class RSSfeedshow extends AppCompatActivity {
    ArrayList<RSSElement> a = new ArrayList<>();
    RSSFeedparser parser = new RSSFeedparser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_layout);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        putlayout layout=new putlayout();
        layout.execute(url);
    }

    public class putlayout extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            String url = args[0];
            a=parser.getRSSfeedFromUrl(url);
            runOnUiThread(new Runnable() {
                public void run() {
            for(int i=0;i<a.size();i++){
                LinearLayout layout=(LinearLayout) findViewById(R.id.layout1);
                 Button new_button = new Button(getApplicationContext());
                 new_button.setText(a.get(i).title + "\r\n"+ a.get(i).pubdate);
                 new_button.setLayoutParams(new ViewGroup.LayoutParams(1500, 200));
                 new_button.setX(0);
                 new_button.setY(200);
                 layout.addView(new_button);
             }}});

             return null;
    }


}}
