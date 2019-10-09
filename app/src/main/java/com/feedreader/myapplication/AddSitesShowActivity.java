package com.feedreader.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.feedreader.myapplication.data.MyApplication;
import com.feedreader.myapplication.data.RSSElement;
import com.feedreader.myapplication.tools.RSSFeedparser;

import java.util.ArrayList;

public class AddSitesShowActivity extends AppCompatActivity {
    ArrayList<RSSElement> a = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.addsite_layout);
    }

    public void addSite(View v) {
        EditText url_text = findViewById(R.id.editText1);
        String url = "http://" + url_text.getText().toString();
        Check checkurl = new Check();
        checkurl.execute(url);
    }

    public void Delete(View v) {
        ConstraintLayout layout = findViewById(R.id.layout);
        EditText tag_text = findViewById(R.id.editText);
        String tag = "http://" + tag_text.getText().toString();
        if (layout.findViewWithTag(tag) != null) {
            View c = layout.findViewWithTag(tag);
            layout.removeView(c);
        }
    }

    public void OnCheck(View v) {
        if (v.getTag().toString() != null) {
            putLayout putlayout = new putLayout();
            putlayout.execute(v.getTag().toString());
        }
    }

    public void Onclick(View v) {
        Intent intent = new Intent(getApplicationContext(), RSSfeedshowActivity.class);
        intent.putExtra("url", v.getTag().toString());
        startActivity(intent);
    }


    class Check extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            final String url = args[0];
            RSSFeedparser parser = new RSSFeedparser();
            ArrayList<RSSElement> a = parser.getRSSfeedFromUrl(url);
            if (a != null)
                runOnUiThread(new Runnable() {
                    public void run() {
                        ConstraintLayout layout = findViewById(R.id.layout);
                        Button new_button = new Button(getApplicationContext());
                        new_button.setText(url);
                        new_button.setTag(url);
                        new_button.setLayoutParams(new ViewGroup.LayoutParams(1500, 200));
                        new_button.setX(0);
                        new_button.setY(1100);
                        new_button.setAllCaps(false);
                        new_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), RSSfeedshowActivity.class);
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
                        ConstraintLayout layout = findViewById(R.id.layout);
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

    public class putLayout extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            final String url = args[0];
            RSSFeedparser parser = new RSSFeedparser();
            a = parser.getRSSfeedFromUrl(url);
            runOnUiThread(new Runnable() {
                public void run() {
                    for (int i = 0; i < a.size(); i++) {
                        Button new_button = new Button(getApplicationContext());
                        int number = i + 1;
                        final String newsTitle = a.get(i).title;
                        new_button.setText(number + ". " + newsTitle + "\r\n" + a.get(i).pubdate + "\r\n" + a.get(i).category);
                        new_button.setLayoutParams(new ViewGroup.LayoutParams(1450, 300));
                        new_button.setX(0);
                        new_button.setY(0);
                        new_button.setAllCaps(false);
                        new_button.setTag(a.get(i).link);
                        new_button.setBackgroundColor(Color.WHITE);
                        new_button.setFadingEdgeLength(10);
                        new_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                                intent.putExtra("url", v.getTag().toString());
                                intent.putExtra("title", newsTitle);
                                startActivity(intent);
                            }
                        });

                        MyApplication app = (MyApplication) getApplication();
                        new_button.setGravity(0);//Text to the left
                        app.getButtonList().add(new_button);
                    }
                }
            });
            return null;
        }


        public void openFavourites(View v) {
            Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
            startActivity(intent);
        }

        public void returnHome(View v) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

    }
}
