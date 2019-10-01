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
        setContentView(R.layout.feed_layout);
    }


    public void putweb(TextView web) {
        LinearLayout layout = findViewById(R.id.layout);
        Button new_button = new Button(this);
        new_button.setText("http://" + web.getText().toString());
        new_button.setTag("http://" + web.getText().toString());
        new_button.setLayoutParams(new ViewGroup.LayoutParams(1500, 200));
        new_button.setX(0);
        new_button.setY(550);
        new_button.setAllCaps(false);
        new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RSSfeedshow.class);
                intent.putExtra("url", v.getTag().toString());
                startActivity(intent);
            }
        });
        layout.addView(new_button);
    }

    public void addSite(View v) {
        TextView web = findViewById(R.id.editText1);
        check checkWeb = new check();
        checkWeb.execute("http://" + web.getText().toString());
    }

    public void Delete(View v) {

    }

    class check extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            final String url = args[0];
            RSSFeedparser parser = new RSSFeedparser();
            ArrayList<RSSElement> a = parser.getRSSfeedFromUrl(url);
            if (a != null)
                runOnUiThread(new Runnable() {
                    public void run() {
                        LinearLayout layout = findViewById(R.id.layout);
                        Button new_button = new Button(getApplicationContext());
                        new_button.setText(url);
                        new_button.setTag(url);
                        new_button.setLayoutParams(new ViewGroup.LayoutParams(1500, 200));
                        new_button.setX(0);
                        new_button.setY(550);
                        new_button.setAllCaps(false);
                        new_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), RSSfeedshow.class);
                                intent.putExtra("url", v.getTag().toString());
                                startActivity(intent);
                            }
                        });
                        layout.addView(new_button);
                    }
                });
            else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        LinearLayout layout = findViewById(R.id.layout);
                        TextView reminder = new Button(getApplicationContext());
                        reminder.setText("This url is invalid" + "\r\n" +
                                "Please press to close the window");
                        reminder.setX(100);
                        reminder.setY(800);
                        reminder.setBackgroundColor(Color.WHITE);
                        reminder.setTextColor(Color.RED);
                        reminder.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                v.setVisibility(View.INVISIBLE);
                            }
                        });
                        layout.addView(reminder);
                    }
                });
            }

            return null;
        }

    }

    public void returnHome(View v) {
        setContentView(R.layout.feed_layout);
    }

    public void savedSitePage(View v) {
        Intent intent = new Intent(getApplicationContext(), savedsiteshow.class);
        startActivity(intent);
    }

    public void addSitePage(View v) {
        setContentView(R.layout.activity);
    }

}