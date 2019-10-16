package com.feedreader.myapplication;


import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.feedreader.myapplication.data.MyApplication;
import com.feedreader.myapplication.data.RSSElement;
import com.feedreader.myapplication.tools.DateTimeAdapter;
import com.feedreader.myapplication.tools.RSSFeedParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    //String filePath;
    //File file;
    private int permissionCode;
    private int[] permissionReceived;

    ArrayList<RSSElement> RSSList = new ArrayList<>();
    ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
    RSSFeedParser parser = new RSSFeedParser();

    String filePath = Environment.getExternalStorageDirectory().getPath();
    File checkBoxFile = new File(filePath + "/isCheckedList.xml");
    File newsListFile = new File(filePath + "/newsList.xml");

    DateTimeAdapter dta = new DateTimeAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, permissionCode);
        onRequestPermissionsResult(permissionCode, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, permissionReceived);

        setContentView(R.layout.activity);

        loadCheckBoxList(checkBoxFile);
        loadNewsList(newsListFile);

        display();
    }



    void display() {
        ArrayList<String> urlList = new ArrayList<>();

        MyApplication app = (MyApplication) getApplication();
        for (int i = 0; i < app.getCheckBoxList().size(); i++) {
            CheckBox checkBox = (CheckBox) app.getCheckBoxList().get(i);
            if (checkBox.getTag().toString() != null) {
                if (checkBox.isChecked()) {
                    urlList.add(checkBox.getTag().toString());
                }
            }
        }
        for (String s : urlList) {
            putLayout putlayout = new putLayout();
            putlayout.execute(s);
        }
    }



    /**
     Author: Mingzhen Ao
     * add button to the layout
     */
    public void addButtonToLayout() {
        LinearLayout layout = findViewById(R.id.linearLayout);
        MyApplication app = (MyApplication) getApplication();

        if (app.getLayoutList() != null) {
            for (int i = 0; i < app.getLayoutList().size(); i++) {
                int count = app.getLayoutList().get(i).getChildCount();
                for (int j = 0; j < count; j++) {
                    Button child = (Button) app.getLayoutList().get(i).getChildAt(j);
                    if (child != null && child.getParent() != null) {
                        ((ViewGroup) child.getParent()).removeView(child);
                        layout.addView(child);
                    }
                }
            }

        }
    }




    /**
     * Author: Mingzhen Ao
     * if click favourite button, go to favouritesactivity
     *
     * @param view
     */
    public void goToFavorite(View view) {
        Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
        MainActivity.this.startActivity(intent);
    }


    /**
     * Author: Mingzhen Ao
     * if click add button, go to addSitesShowActivity
     *
     * @param view
     */
    public void goToAddLayout(View view) {
        Intent intent = new Intent(MainActivity.this, AddSitesShowActivity.class);
        MainActivity.this.startActivity(intent);
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

    public void loadCheckBoxList(File file) {
        ArrayList<Boolean> isCheckedList = new ArrayList<>();

        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        //load the state of checkbox from file and update it to isCheckedList
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(file);
            NodeList isCheckedNL = dom.getElementsByTagName("isChecked");

            for (int i = 0; i < isCheckedNL.getLength(); i++) {
                Element isCheckedElem = (Element) isCheckedNL.item(i);
                String isChecked = isCheckedElem.getElementsByTagName("value").item(0).getTextContent();
                isCheckedList.add(Boolean.parseBoolean(isChecked));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //passes the new state from isCheckedList to current application
        final MyApplication app = (MyApplication) getApplication();
        if (isCheckedList.size() > 0) {
            if (app.getCheckBoxList() != null) {
                for (int i = 0; i < app.getCheckBoxList().size(); i++) {
                    if (isCheckedList.get(i)) {
                        app.getCheckBoxList().get(i).setChecked(true);
                    } else {
                        app.getCheckBoxList().get(i).setChecked(false);
                    }
                }
            }
        }
        app.setCheckBoxList(app.getCheckBoxList());
    }


    public void loadNewsList(File file) {
        final MyApplication app = (MyApplication) getApplication();
        ArrayList<RSSElement> newsList = new ArrayList<>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(file);

            NodeList newsNL = dom.getElementsByTagName("news");

            for (int i = 0; i < newsNL.getLength(); i++) {
                Node newsNode = newsNL.item(i);

                String title = newsNode.getAttributes().getNamedItem("title").getTextContent();
                String link = newsNode.getAttributes().getNamedItem("link").getTextContent();
                String pubdate = newsNode.getAttributes().getNamedItem("pubdate").getTextContent();

                newsList.add(new RSSElement(title, link, pubdate));
            }
            app.setRSSElementList(newsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLinearLayout(ArrayList<RSSElement> list) {
        for (int i = 0; i < list.size(); i++) {
            final String formattedDate = dta.formatDateTime(dta.getDateTime(list.get(i).pubDate));

            LinearLayout layout = findViewById(R.id.linearLayout);
            Button new_button = new Button(getApplicationContext());
            int number = i + 1;
            final String newsTitle = list.get(i).title;
            new_button.setText(number + ". " + newsTitle + "\r\n" + formattedDate +"\r\n");
            new_button.setLayoutParams(new ViewGroup.LayoutParams(1450, 300));
            new_button.setX(0);
            new_button.setY(0);
            new_button.setAllCaps(false);
            new_button.setTag(list.get(i).link);
            new_button.setFadingEdgeLength(10);
            new_button.setPadding(50,50,50,50);
            GradientDrawable background = new GradientDrawable();
            background.setColor(Color.WHITE);
            background.setStroke(40, 999);
            background.setCornerRadius(15);
            new_button.setBackground(background);
            new_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                    intent.putExtra("url", v.getTag().toString());
                    intent.putExtra("title", newsTitle);
                    intent.putExtra("date",formattedDate);
                    startActivity(intent);
                }
            });
            new_button.setGravity(0);//Text to the left
            layout.addView(new_button);
        }
    }

    public class putFilteredLayout extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            runOnUiThread(new Runnable() {
                public void run() {
                    setLinearLayout(filteredRSSList);
                }
            });
            return null;
        }
    }

    public class putLayout extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            String url = args[0];
            RSSList = parser.getRSSfeedFromUrl(url);

            runOnUiThread(new Runnable() {
                public void run() {
                    setLinearLayout(RSSList);
                }
            });
            return null;
        }
    }

    public ArrayList<RSSElement> sortRSSList(ArrayList<RSSElement> RSSList) {

        return null;
    }



}