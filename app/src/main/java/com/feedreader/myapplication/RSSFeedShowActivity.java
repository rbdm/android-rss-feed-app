package com.feedreader.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TimePicker;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.feedreader.myapplication.data.RSSElement;
import com.feedreader.myapplication.tools.RSSFeedParser;
import com.feedreader.myapplication.tools.TwitterResultReceiver;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class RSSFeedShowActivity extends AppCompatActivity {
    ArrayList<RSSElement> RSSList = new ArrayList<>();
    ArrayList<RSSElement> tempRSSList = new ArrayList<>();
    ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<>();
    RSSFeedParser parser = new RSSFeedParser();
    Button buttonHome;
    ImageButton imageButtonSort;
    String url;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        Intent intent = new Intent(this, AddSitesShowActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_layout);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        putLayout putlayout = new putLayout();
        putlayout.execute(url);

        String tag = "imageButtonRefreshInFeedLayout";
        LinearLayout layout = findViewById(R.id.linearLayout2);
        ImageButton refreshBtn = layout.findViewWithTag(tag);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout = findViewById(R.id.linearLayout);
                layout.removeAllViews();
                putLayout putlayout = new putLayout();
                putlayout.execute(url);
            }
        });

        imageButtonSort = findViewById(R.id.imageButtonSort);
        imageButtonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu sortMenu = new PopupMenu(RSSFeedShowActivity.this, imageButtonSort);
                sortMenu.getMenuInflater().inflate(R.menu.sort_menu, sortMenu.getMenu());

                sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        filteredRSSList.clear();
                        categoryList.clear();

                        if (menuItem.getItemId() == R.id.filterLastHour) {
                            tempRSSList = RSSList;
                            for (RSSElement re: tempRSSList) {
                                DateTime dateTime = getDateTime(re.pubdate);
                                if (dateTime.isAfter(new DateTime().minusHours(1))) {
                                    filteredRSSList.add(re);
                                }
                            }
                        } else if (menuItem.getItemId() == R.id.filterToday) {
                            tempRSSList = RSSList;
                            for (RSSElement re: tempRSSList) {
                                DateTime dateTime = getDateTime(re.pubdate);
                                if (dateTime.isAfter(new DateMidnight())) {
                                    filteredRSSList.add(re);
                                }
                            }
                        } else if (menuItem.getItemId() == R.id.filterThisWeek) {
                            tempRSSList = RSSList;
                            for (RSSElement re: tempRSSList) {
                                DateTime dateTime = getDateTime(re.pubdate);
                                if (dateTime.isAfter(new DateTime().minusDays(7))) {
                                    filteredRSSList.add(re);
                                }
                            }
                        } else if (menuItem.getItemId() == R.id.filterDate) {
                            DialogFragment newFragment = new DatePickerFragment();
                            newFragment.show(getFragmentManager(), "datePicker");
                            ((DatePickerFragment) newFragment).dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker view, int year, int month, int day) {
                                    tempRSSList = RSSList;
                                    DateTime selectedDate = new DateTime(year, month+1, day, 0, 0, 0);

                                    for (RSSElement re: tempRSSList) {
                                        DateTime dateTime = getDateTime(re.pubdate);
                                        if (dateTime.isAfter(selectedDate) && dateTime.isBefore(selectedDate.plusDays(1))) {
                                            filteredRSSList.add(re);
                                        }
                                    }

                                    LinearLayout layout = findViewById(R.id.linearLayout);
                                    layout.removeAllViews();
                                    putFilteredLayout pfl = new putFilteredLayout();
                                    pfl.execute(url);
                                }
                            };
                        }

                        LinearLayout layout = findViewById(R.id.linearLayout);
                        layout.removeAllViews();
                        putFilteredLayout pfl = new putFilteredLayout();
                        pfl.execute(url);

                        return true;
                    }
                });

                sortMenu.show();
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        DatePickerDialog.OnDateSetListener dateSetListener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Set default date using joda time
            final DateTime now = new DateTime();
            int year = now.getYear();
            int month = now.getMonthOfYear();
            int day = now.getDayOfMonth();
            return new DatePickerDialog(getActivity(), dateSetListener, year, month-1, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Must define inside button, because this class is static
        }
    }

    public DateTime getDateTime(String s) {
        String[] dateTime = s.split(" ");

        if (dateTime[4].length() == 5) {
            dateTime[4] = dateTime[4] + ":00";
        }
        String dateTimeUpdate = dateTime[0];
        for (int i=1; i<dateTime.length; i++) {
            dateTimeUpdate += " " + dateTime[i];
        }

        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss z");

        DateTime result = new DateTime();
        try {
            Date date = df.parse(dateTimeUpdate);
            DateTime resultSucceed = new DateTime(date);
            return resultSucceed;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(dateTimeUpdate);
            return null;
        }
    }

    public String formatDateTime(DateTime dt) {
        dt.getZone();
        String formattedDateString = dt.toString(DateTimeFormat.forPattern("EEE, dd MMM yy, kk:mm")) + " AEST";
        return formattedDateString;
    }

    public class putFilteredLayout extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {

            runOnUiThread(new Runnable() {
                public void run() {
                    for (int i = 0; i < filteredRSSList.size(); i++) {
                        String formattedDate = formatDateTime(getDateTime(RSSList.get(i).pubdate));
                        LinearLayout layout = findViewById(R.id.linearLayout);
                        Button new_button = new Button(getApplicationContext());
                        int number = i + 1;
                        final String newsTitle = filteredRSSList.get(i).title;
                        new_button.setText(number + ". " + newsTitle + "\r\n" + formattedDate +"\r\n"+filteredRSSList.get(i).category);
                        new_button.setLayoutParams(new ViewGroup.LayoutParams(1450, 300));
                        new_button.setX(0);
                        new_button.setY(0);
                        new_button.setAllCaps(false);
                        new_button.setTag(filteredRSSList.get(i).link);
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
                        new_button.setGravity(0);//Text to the left
                        layout.addView(new_button);

                    }
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
                    for (int i = 0; i < RSSList.size(); i++) {
                        String formattedDate = formatDateTime(getDateTime(RSSList.get(i).pubdate));
                        LinearLayout layout = findViewById(R.id.linearLayout);
                        Button new_button = new Button(getApplicationContext());
                        int number = i + 1;
                        final String newsTitle = RSSList.get(i).title;
                        new_button.setText(number + ". " + newsTitle + "\r\n" + formattedDate +"\r\n"+RSSList.get(i).category);
                        new_button.setLayoutParams(new ViewGroup.LayoutParams(1450, 300));
                        new_button.setX(0);
                        new_button.setY(0);
                        new_button.setAllCaps(false);
                        new_button.setTag(RSSList.get(i).link);
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
                        new_button.setGravity(0);//Text to the left
                        layout.addView(new_button);

                    }
                }
            });
            return null;
        }


    }

    public void openFavourites(View v) {
        Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
        startActivity(intent);
    }

    public void addSitePage(View v) {
        Intent intent = new Intent(getApplicationContext(), AddSitesShowActivity.class);
        startActivity(intent);
    }

    public void returnHome(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
