package com.feedreader.myapplication;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.feedreader.myapplication.data.MyApplication;
import com.feedreader.myapplication.data.RSSElement;
import com.feedreader.myapplication.tools.DateTimeAdapter;
import com.feedreader.myapplication.tools.FunctionContainer;
import com.feedreader.myapplication.tools.RSSFeedParser;
import com.feedreader.myapplication.tools.SortAndFilterAdapter;

import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    // variables to request and store permissions
    private int permissionCode;
    private int[] permissionReceived;
    private String[] permissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] permissionsApproved = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // variables to manage RSS feed list
    ArrayList<RSSElement> RSSList = new ArrayList<>();
    ArrayList<RSSElement> partialRSSList = new ArrayList<>();
    ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
    RSSFeedParser parser = new RSSFeedParser();

    // defines path of external files for persistent data
    String filePath = Environment.getExternalStorageDirectory().getPath();
    File checkBoxFile = new File(filePath + "/isCheckedList.xml");
    File newsListFile = new File(filePath + "/newsList.xml");

    SortAndFilterAdapter sfa = new SortAndFilterAdapter();
    ImageButton imageButtonSort, imageButtonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity);

        //request permission for persistent data
        ActivityCompat.requestPermissions(MainActivity.this, permissions, permissionCode);
        onRequestPermissionsResult(permissionCode, permissionsApproved, permissionReceived);

        //load external source for initial app state
        loadCheckBoxList(checkBoxFile);
        loadNewsList(newsListFile);

        //displays initial news feed
        displayNews();

        /**
         * Author: Mirhady Dorodjatun
         * This part defines the behaviour of the search button.
         * It runs the search method by an instance of SortAndFilterAdapter.
         * @param
         */
        imageButtonSearch = findViewById(R.id.imageButtonSearch);
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if memory runs out and variable is collected by garbage collector after heavy usage, refresh the RSS List
                MyApplication app = (MyApplication) getApplication();
                if (app.getNewsList().size()==0 || app.getNewsList()==null) {
                    MainActivity.RefreshRSSFeed refresh = new MainActivity.RefreshRSSFeed();
                    refresh.execute();
                }
                RSSList = app.getNewsList();

                // calls an instance of editText and put it as input in AlertDialog.Builder
                // then reads the input as the search term
                final EditText et = new EditText(MainActivity.this);
                filteredRSSList.clear();
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Input Search Term");
                builder.setView(et);
                builder.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String searchTerm = et.getText().toString().toLowerCase().trim();
                        filteredRSSList = sfa.filterByTerm(RSSList, searchTerm);
                        refreshFilteredLayout();
                    }
                });
                builder.show();
            }
        });

        /**
         * Author: Mirhady Dorodjatun
         * This part defines the behaviour of the sort/filter button. it inflates menu from R.menu.sort_menu
         * Then runs the sort/filter method by an instance of SortAndFilterAdapter.
         * @param
         */
        imageButtonSort = findViewById(R.id.imageButtonSort);
        imageButtonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if memory runs out and variable is collected by garbage collector after heavy usage, refresh the RSS List
                MyApplication app = (MyApplication) getApplication();
                if (app.getNewsList().size()==0 || app.getNewsList()==null) {
                    MainActivity.RefreshRSSFeed refresh = new MainActivity.RefreshRSSFeed();
                    refresh.execute();
                }
                // inflates sort menu from R.menu.sort_menu
                PopupMenu sortMenu = new PopupMenu(MainActivity.this, imageButtonSort);
                sortMenu.getMenuInflater().inflate(R.menu.sort_menu, sortMenu.getMenu());
                sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        MyApplication app = (MyApplication) getApplication();
                        RSSList = app.getNewsList();
                        filteredRSSList.clear();

                        //calls the corresponding method in SortAndFilterAdapter to perform the respective sorting/filtering operation
                        if (menuItem.getItemId() == R.id.filterLastHour) {
                            filteredRSSList = sfa.filterLastHour(RSSList);
                        } else if (menuItem.getItemId() == R.id.filterToday) {
                            filteredRSSList = sfa.filterToday(RSSList);
                        } else if (menuItem.getItemId() == R.id.filterThisWeek) {
                            RSSList = app.getNewsList();
                            filteredRSSList = sfa.filterThisWeek(RSSList);
                        } else if (menuItem.getItemId() == R.id.filterDate) {

                            // calls an instance of date picker fragment from FunctionContainer
                            DialogFragment newFragment = new FunctionContainer.DatePickerFragment();
                            newFragment.show(getFragmentManager(), "datePicker");
                            FunctionContainer.DatePickerFragment dpf = (FunctionContainer.DatePickerFragment) newFragment;
                            dpf.dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker view, int year, int month, int day) {

                                    //somehow there is a discrepancy of 1 month between Joda's dateTime library and Java.util.Date
                                    DateTime selectedDate = new DateTime(year, month+1, day, 0, 0, 0);
                                    filteredRSSList = sfa.filterByDate(RSSList, selectedDate);
                                    refreshFilteredLayout();
                                }
                            };
                        } else if (menuItem.getItemId() == R.id.sortNewestFirst) {
                            filteredRSSList = sfa.sortNewestFirst(RSSList);
                        } else if (menuItem.getItemId() == R.id.sortOldestFirst) {
                            filteredRSSList = sfa.sortOldestFirst(RSSList);
                        } else if (menuItem.getItemId() == R.id.sortByTitle) {
                            filteredRSSList = sfa.sortByTitle(RSSList);
                        }
                        // refresh displayed news list based on filteredRSSList variable
                        refreshFilteredLayout();
                        return true;
                    }
                });
                sortMenu.show();
            }
        });
    }

    /**
     * Author: Mirhady Dorodjatun
     * This method aims to display the news buttons by extracting the checkBox saved in MyApplication,
     * then runs putLayout() for each checkBox to display them.
     * @param
     */
    void displayNews() {
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
        app.setUrlList(urlList);

        putLayout putlayout = new putLayout();
        putlayout.execute();
    }

    /**
     * Author: Mirhady Dorodjatun
     * This method aims to refresh the feed based on the filteredRSSList, which is the output variable
     * of sort, search and filter methods. It utilizes variable filteredRSSList
     * @param
     */
    public void refreshFilteredLayout() {
        LinearLayout layout = findViewById(R.id.linearLayout);
        layout.removeAllViews();
        MainActivity.putFilteredLayout pfl = new MainActivity.putFilteredLayout();
        pfl.execute();
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


    /**
     * Author: Mirhady Dorodjatun
     * This method aims to load from external file that contains previous state of checkBoxList,
     * then passes the argument to MyApplication.
     * @param file the file containing checkBoxList data
     */
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

    /**
     * Author: Mirhady Dorodjatun
     * This method aims to load from external file that contains previous state of favourite news,
     * then passes the argument to MyApplication.
     * @param file the file containing favourite news data
     */
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


    /***
     * Author: Mingzhen Ao
     * This function aims to display news buttons then save corresponding rss feeds to Myapplication
     */
    public void setLinearLayout(ArrayList<RSSElement> list) {
        for (int i = 0; i < list.size(); i++) {
            DateTimeAdapter dta = new DateTimeAdapter();
            final String formattedDate = dta.formatDateTime(dta.getDateTime(list.get(i).pubDate));

            LinearLayout layout = findViewById(R.id.linearLayout);
            Button new_button = new Button(new ContextThemeWrapper(getApplicationContext(), R.style.NewsButton));

            int number = i + 1;
            final String newsTitle = list.get(i).title;
            String source = list.get(i).getNewsSource(list.get(i).link);
            if (source.contains("http")) source = "";
            new_button.setText(number + ". " + newsTitle + "\r\n" + formattedDate +"\r\n" + source + "\r\n");
            new_button.setLayoutParams(new ViewGroup.LayoutParams(1450, 300));
            new_button.setX(0);
            new_button.setY(0);
            new_button.setTag(list.get(i).link);

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
            new_button.setGravity(Gravity.CENTER_VERTICAL);//Text to the left
            layout.addView(new_button);
        }
    }

    /**
     * Author: Mirhady Dorodjatun
     * This class aims to put the filteredRSSList, which are output of sort, search and filter methods,
     * then passes the argument to method putFilteredLayout();
     * @param
     */
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

    /***
     * Author: Mingzhen Ao
     * This function aims to display news buttons then save corresponding rss feeds to Myapplication
     */
    public class putLayout extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            //String url = args[0];

            final MyApplication app = (MyApplication) getApplication();
            app.getNewsList().clear();
            for (String s: app.getUrlList()) {
                partialRSSList = parser.getRSSfeedFromUrl(s);
                app.addNewsSource(partialRSSList);
            }

            runOnUiThread(new Runnable() {
                public void run() {
                    setLinearLayout(app.getNewsList());
                }
            });

            return null;
        }
    }

    /**
     * Author: Mirhady Dorodjatun
     * This class aims to refresh the rss feed which is stored in MyApplication.
     * Sometimes when the app is used heavily, the stored value might be collected by garbage collector.
     * This class refresh the rss feed for such occasion.
     * @param
     */
    public class RefreshRSSFeed extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            MyApplication app = (MyApplication) getApplication();
            app.getNewsList().clear();
            for (String s: app.getUrlList()) {
                partialRSSList = parser.getRSSfeedFromUrl(s);
                app.addNewsSource(partialRSSList);
            }
            return null;
        }
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
}