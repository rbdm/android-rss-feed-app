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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feedreader.myapplication.data.MyApplication;
import com.feedreader.myapplication.data.RSSElement;
import com.feedreader.myapplication.tools.RSSFeedParser;

import java.util.ArrayList;

/* * Author: Mingzhen Ao
 * This class aims to show the websites that contains the rss feeds
 */
public class AddSitesShowActivity extends AppCompatActivity {
    ArrayList<RSSElement> a = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.addsite_layout);
        checkBox();

    }

    /* * Author: Mingzhen Ao
     * This class aims to add the clicklistener, if click the checkbox, then check checkbox status,if it's checked,
     * add feeds to Myapplication,
     * if it's not checked, delete the feeds belong to this xml url
     */
    public void checkBox() {
        LinearLayout layout = findViewById(R.id.linearLayout3);
        final MyApplication app = (MyApplication) getApplication();
        for (int i = 0; i < app.getCheckBoxList().size(); i++) {
            if (app.getCheckBoxList().get(i).getParent() != null)
                ((ViewGroup) app.getCheckBoxList().get(i).getParent()).removeView(app.getCheckBoxList().get(i));
            app.getCheckBoxList().get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkBox = (CheckBox) view;
                    if (checkBox.getTag().toString() != null) {
                        if (checkBox.isChecked()) {
                            putLayout putlayout = new putLayout();
                            putlayout.execute(view.getTag().toString());
                        } else {
                            for (int j = 0; j < app.getLayoutList().size(); j++) {
                                if (app.getLayoutList().get(j).getTag().equals(view.getTag().toString())) {
                                    app.getLayoutList().remove(j);
                                }
                            }
                        }
                    }
                }
            });
                layout.addView(app.getCheckBoxList().get(i));
        }
    }

    /* * Author: Mingzhen Ao
     * When click on the home button, go to mainActivity
     */

    public void goBackToHome(View view) {
        Intent intent = new Intent(AddSitesShowActivity.this, MainActivity.class);
        AddSitesShowActivity.this.startActivity(intent);
    }

    /* * Author: Mingzhen Ao
     * When click on the favourite button, go to FavouritesActivity
     */
    public void goToFavourite(View view) {
        Intent intent = new Intent(AddSitesShowActivity.this, FavouritesActivity.class);
        AddSitesShowActivity.this.startActivity(intent);
    }

    /* * Author: Mingzhen Ao
     * This function aims to add site if the site is right xml url
     */
    public void addSite(View v) {
        EditText url_text = findViewById(R.id.editText1);
        String url = "http://" + url_text.getText().toString();
        Check checkurl = new Check();
        checkurl.execute(url);
    }

    /* * Author: Mingzhen Ao
     * This function aims to delete the exact site if the site is right xml url
     */
    public void Delete(View v) {
        ConstraintLayout layout = findViewById(R.id.layout);
        EditText tag_text = findViewById(R.id.editText);
        String tag = "http://" + tag_text.getText().toString();
        if (layout.findViewWithTag(tag) != null) {
            View c = layout.findViewWithTag(tag);
            layout.removeView(c);
        }
    }

    /* * Author: Mingzhen Ao
     * When click the web button, will transfer to rssfeedshow activity
     */
    public void Onclick(View v) {
        Intent intent = new Intent(getApplicationContext(), RSSFeedShowActivity.class);
        intent.putExtra("url", v.getTag().toString());
        startActivity(intent);
    }

    /* * Author: Mingzhen Ao
     * This function aims to check if there is a right xml url or not,
     * if it's right ,added it, if not, show not valid.
     */
    class Check extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            final String url = args[0];
            RSSFeedParser parser = new RSSFeedParser();
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
                                Intent intent = new Intent(getApplicationContext(), RSSFeedShowActivity.class);
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

    /* * Author: Mingzhen Ao
     * This function aims to save corresponding rss feeds to Myapplication
     */
    public class putLayout extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            final String url = args[0];
            RSSFeedParser parser = new RSSFeedParser();
            a = parser.getRSSfeedFromUrl(url);
            final LinearLayout layout = new LinearLayout(getApplicationContext());
            runOnUiThread(new Runnable() {
                public void run() {
                    for (int i = 0; i < a.size(); i++) {
                        Button new_button = new Button(getApplicationContext());
                        int number = i + 1;
                        final String newsTitle = a.get(i).title;
                        new_button.setText(number + ". " + newsTitle + "\r\n" + a.get(i).pubdate + "\r\n");
                        new_button.setLayoutParams(new ViewGroup.LayoutParams(1450, 300));
                        new_button.setX(0);
                        new_button.setY(0);
                        new_button.setAllCaps(false);
                        new_button.setTag(a.get(i).link);
                        new_button.setBackgroundColor(Color.WHITE);
                        new_button.setFadingEdgeLength(10);
                        new_button.setGravity(0);//Text to the left
                        new_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                                intent.putExtra("url", v.getTag().toString());
                                intent.putExtra("title", newsTitle);
                                startActivity(intent);
                            }
                        });
                        layout.setTag(url);
                        layout.addView(new_button);
                    }
                }
            });
            MyApplication app = (MyApplication) getApplication();
            app.getLayoutList().add(layout);
            return null;
        }
    }
}