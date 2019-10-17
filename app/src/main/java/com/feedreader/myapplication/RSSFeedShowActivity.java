package com.feedreader.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;


import com.feedreader.myapplication.data.MyApplication;
import com.feedreader.myapplication.data.RSSElement;
import com.feedreader.myapplication.tools.DateTimeAdapter;
import com.feedreader.myapplication.tools.RSSFeedParser;
import com.feedreader.myapplication.tools.SortAndFilterAdapter;

import org.joda.time.DateTime;

import java.util.ArrayList;


public class RSSFeedShowActivity extends AppCompatActivity {

    ArrayList<RSSElement> RSSList = new ArrayList<>();
    ArrayList<RSSElement> tempRSSList = new ArrayList<>();
    ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
    RSSFeedParser parser = new RSSFeedParser();
    ImageButton imageButtonSort, imageButtonSearch;
    String url;

    DateTimeAdapter dta = new DateTimeAdapter();
    SortAndFilterAdapter sfa = new SortAndFilterAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_layout);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        MyApplication app = (MyApplication) getApplication();
        app.setUrl(url);
        System.out.println(url);

        putLayout putlayout = new putLayout();
        putlayout.execute(url);

        String tag = "imageButtonRefreshInFeedLayout";
        LinearLayout layout = findViewById(R.id.linearLayout2);
        ImageButton refreshBtn = layout.findViewWithTag(tag);

        imageButtonSearch = findViewById(R.id.imageButtonSearch);
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText et = new EditText(RSSFeedShowActivity.this);
                filteredRSSList.clear();

                final AlertDialog.Builder builder = new AlertDialog.Builder(RSSFeedShowActivity.this);
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

        imageButtonSort = findViewById(R.id.imageButtonSort);
        imageButtonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication app = (MyApplication) getApplication();
                PopupMenu sortMenu = new PopupMenu(RSSFeedShowActivity.this, imageButtonSort);
                sortMenu.getMenuInflater().inflate(R.menu.sort_menu, sortMenu.getMenu());
                sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        MyApplication app = (MyApplication) getApplication();
                        RSSList = app.getNewsList();
                        filteredRSSList.clear();
                        if (menuItem.getItemId() == R.id.filterLastHour) {
                            filteredRSSList = sfa.filterLastHour(RSSList);
                        } else if (menuItem.getItemId() == R.id.filterToday) {
                            filteredRSSList = sfa.filterToday(RSSList);
                        } else if (menuItem.getItemId() == R.id.filterThisWeek) {
                            filteredRSSList = sfa.filterThisWeek(RSSList);
                        } else if (menuItem.getItemId() == R.id.filterDate) {
                            DialogFragment newFragment = new DatePickerFragment();
                            newFragment.show(getFragmentManager(), "datePicker");
                            ((DatePickerFragment) newFragment).dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker view, int year, int month, int day) {
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
                        refreshFilteredLayout();
                        return true;
                    }
                });
                sortMenu.show();
            }
        });
    }

    public void refreshFilteredLayout() {
        LinearLayout layout = findViewById(R.id.linearLayout);
        layout.removeAllViews();
        putFilteredLayout pfl = new putFilteredLayout();
        pfl.execute();
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
            // Must define inside UI Activity, because this class is static
        }
    }

    public void setLinearLayout(ArrayList<RSSElement> list) {
        for (int i = 0; i < list.size(); i++) {
            final String formattedDate = dta.formatDateTime(dta.getDateTime(list.get(i).pubDate));
            LinearLayout layout = findViewById(R.id.linearLayout);
            Button new_button = new Button(new ContextThemeWrapper(getApplicationContext(), R.style.NewsButton));
            int number = i + 1;
            final String newsTitle = list.get(i).title;
            final String source = list.get(i).getNewsSource(list.get(i).link);
            //final String category = list.get(i).getConcatedCategory();
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
            MyApplication app = (MyApplication) getApplication();
            app.setNewsList(RSSList);

            runOnUiThread(new Runnable() {
                public void run() {
                    setLinearLayout(RSSList);
                }
            });
            return null;
        }
    }

    public class RefreshRSSFeed extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            String url = args[0];
            RSSList = parser.getRSSfeedFromUrl(url);
            MyApplication app = (MyApplication) getApplication();
            app.setNewsList(RSSList);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LinearLayout layout = findViewById(R.id.linearLayout);
        layout.removeAllViews();
        putLayout putlayout = new putLayout();
        putlayout.execute(url);

        return super.onOptionsItemSelected(item);
    }
}
