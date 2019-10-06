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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class addSitesShow extends AppCompatActivity {

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
        if(layout.findViewWithTag(tag)!=null)
        {    View c=layout.findViewWithTag(tag);
            layout.removeView(c);
        }
        }


    public void clickBbc(View v) {
        Intent intent = new Intent(getApplicationContext(), RSSfeedshow.class);
        intent.putExtra("url", v.getTag().toString());
        startActivity(intent);
    }

    public void clickCnn(View v) {
        Intent intent = new Intent(getApplicationContext(), RSSfeedshow.class);
        intent.putExtra("url", v.getTag().toString());
        startActivity(intent);
    }

    public void clickNbc(View v) {
        Intent intent = new Intent(getApplicationContext(), RSSfeedshow.class);
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

}
