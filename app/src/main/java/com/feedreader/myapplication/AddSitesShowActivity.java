package com.feedreader.myapplication;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/* *
 * Author: Mingzhen Ao
 * This class aims to show the websites that contains the rss feeds
 */
public class AddSitesShowActivity extends AppCompatActivity {
    ArrayList<RSSElement> a = new ArrayList<>();
    String filePath;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.addsite_layout);
        checkBox();
    }

    /* *
     * Author: Mingzhen Ao
     * This class aims to add the clicklistener, if click the checkbox, then check checkbox status,if it's checked,
     * add feeds to Myapplication,
     * if it's not checked, delete the feeds belong to this xml url
     */
    public void checkBox() {
        LinearLayout layout = findViewById(R.id.linearLayout3);
        final MyApplication app = (MyApplication) getApplication();
        filePath = Environment.getExternalStorageDirectory().getPath(); // AddSitesShowActivity.this.getApplicationContext().getFilesDir().getPath().toString();
        // Save it to the specified file.
        file = new File(filePath + "/isCheckedList.xml");

        for (int i = 0; i < app.getCheckBoxList().size(); i++) {
            if (app.getCheckBoxList().get(i).getParent() != null)
                ((ViewGroup) app.getCheckBoxList().get(i).getParent()).removeView(app.getCheckBoxList().get(i));
            Button corresponding_button = createButton(app.getCheckBoxList().get(i));
            corresponding_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), RSSFeedShowActivity.class);
                    intent.putExtra("url", view.getTag().toString());
                    startActivity(intent);
                }
            });
            app.getCheckBoxList().get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkBox = (CheckBox) view;
                    if (checkBox.getTag().toString() != null) {
                        if (checkBox.isChecked()) {
                            //putLayout putlayout = new putLayout();
                            //putlayout.execute(view.getTag().toString());
                        } else {
                            for (int j = 0; j < app.getLayoutList().size(); j++) {
                                if (app.getLayoutList().get(j).getTag().equals(view.getTag().toString())) {
                                    app.getLayoutList().remove(j);
                                }
                            }
                        }
                    }
                    saveCheckBoxList(file);
                    System.out.println(filePath + "/isCheckedList.xml");
                }
            });
            LinearLayout this_layout = new LinearLayout(this);
            this_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            this_layout.addView(corresponding_button);
            this_layout.addView(app.getCheckBoxList().get(i));
            layout.addView(this_layout);
        }
    }

    public void saveCheckBoxList(File file) {
        ArrayList<Boolean> isCheckedList = new ArrayList<>();
        final MyApplication app = (MyApplication) getApplication();

        for (int i = 0; i < app.getCheckBoxList().size(); i++) {
            CheckBox checkBox = app.getCheckBoxList().get(i);
            if (checkBox.getParent() != null) {
                if (checkBox.isChecked()) {
                    isCheckedList.add(true);
                } else {
                    isCheckedList.add(false);
                }
            } else {
                throw new IllegalArgumentException();
            }
        }

        try {
            Document dom;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();

            Element isCheckedListElement = dom.createElement("isCheckedList");

            for (int i = 0; i < isCheckedList.size(); i++) {
                Element isCheckedElement = dom.createElement("isChecked");

                Element value = dom.createElement("value");
                value.appendChild(dom.createTextNode(isCheckedList.get(i).toString()));
                isCheckedElement.appendChild(value);

                isCheckedListElement.appendChild(isCheckedElement);
            }
            for (Boolean b : isCheckedList) {
                System.out.println(isCheckedList.toString());
            }

            System.out.println(isCheckedListElement.toString());

            Transformer t = TransformerFactory.newInstance().newTransformer();
            DOMSource ds = new DOMSource(isCheckedListElement);
            StreamResult sr = new StreamResult(file);

            t.transform(ds, sr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /* *
     * Author: Mingzhen Ao
     * When click on the home button, go to mainActivity
     */

    public void goBackToHome(View view) {
        Intent intent = new Intent(AddSitesShowActivity.this, MainActivity.class);
        AddSitesShowActivity.this.startActivity(intent);
    }

    /* *
     * Author: Mingzhen Ao
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


    /* * Author: Mingzhen Ao
     * This function aims to check if there is a right xml url or not,
     * if it's right ,added it, if not, show not valid.
     */

    class Check extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            final String url = args[0];
            RSSFeedParser parser = new RSSFeedParser();
            final ArrayList<RSSElement> a = parser.getRSSfeedFromUrl(url);
            if (a != null)
                runOnUiThread(new Runnable() {
                    public void run() {
                        MyApplication app = (MyApplication) getApplication();
                        CheckBox checkBox = new CheckBox(getApplicationContext());
                        checkBox.setText(url);
                        checkBox.setTextColor(Color.WHITE);
                        checkBox.setTag(url);
                        checkBox.setChecked(false);
                        app.getCheckBoxList().add(checkBox);
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

    public Button createButton(CheckBox checkBox) {
        CheckBox this_box = checkBox;
        Button button = new Button(this);
        button.setText(this_box.getText());
        button.setTextColor(Color.WHITE);
        button.setY(this_box.getY());
        button.setTag(this_box.getTag());
        return button;
    }


    /* *
     * Author: Mingzhen Ao
     * This function aims to save corresponding rss feeds to Myapplication
     */

    /*
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
                        new_button.setText(number + ". " + newsTitle + "\r\n" + a.get(i).pubDate + "\r\n");
                        new_button.setLayoutParams(new ViewGroup.LayoutParams(1450, 300));
                        new_button.setX(0);
                        new_button.setY(0);
                        new_button.setAllCaps(false);
                        new_button.setTag(a.get(i).link);
                        new_button.setBackgroundColor(Color.WHITE);
                        new_button.setFadingEdgeLength(10);
                        new_button.setGravity(Gravity.LEFT);//Text to the left（horizontally）
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
    */
}