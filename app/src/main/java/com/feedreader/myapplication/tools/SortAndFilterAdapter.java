package com.feedreader.myapplication.tools;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Toast;

import com.feedreader.myapplication.AddSitesShowActivity;
import com.feedreader.myapplication.data.RSSElement;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SortAndFilterAdapter {
    DateTimeAdapter dta = new DateTimeAdapter();

    public ArrayList<RSSElement> filterToday(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            DateTime dateTime = dta.getDateTime(re.pubDate);
            if (dateTime.isAfter(new DateMidnight())) {
                filteredRSSList.add(re);
            }
        }
        if (filteredRSSList.size()==0) {
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "No news found for today",
                    Toast.LENGTH_SHORT
            );
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(255, 105, 97), PorterDuff.Mode.SRC_IN);
            toast.show();
        }
        return filteredRSSList;
    }

    public ArrayList<RSSElement> filterTodayWithoutToast(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            DateTime dateTime = dta.getDateTime(re.pubDate);
            if (dateTime.isAfter(new DateMidnight())) {
                filteredRSSList.add(re);
            }
        }
        return filteredRSSList;
    }

    public ArrayList<RSSElement> filterLastHour(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            DateTime dateTime = dta.getDateTime(re.pubDate);
            if (dateTime.isAfter(new DateTime().minusHours(1))) {
                filteredRSSList.add(re);
            }
        }
        if (filteredRSSList.size()==0) {
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "No news found in the last hour",
                    Toast.LENGTH_SHORT
            );
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(255, 105, 97), PorterDuff.Mode.SRC_IN);
            toast.show();
        }
        return filteredRSSList;
    }

    public ArrayList<RSSElement> filterLastHourWithoutToast(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            DateTime dateTime = dta.getDateTime(re.pubDate);
            if (dateTime.isAfter(new DateTime().minusHours(1))) {
                filteredRSSList.add(re);
            }
        }
        return filteredRSSList;
    }

    public ArrayList<RSSElement> filterThisWeek(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            DateTime dateTime = dta.getDateTime(re.pubDate);
            if (dateTime.isAfter(new DateTime().minusDays(7))) {
                filteredRSSList.add(re);
            }
        }
        if (filteredRSSList.size()==0) {
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "No news found for the last week",
                    Toast.LENGTH_SHORT
            );
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(255, 105, 97), PorterDuff.Mode.SRC_IN);
            toast.show();
        }
        return filteredRSSList;
    }

    public ArrayList<RSSElement> filterThisWeekWithoutToast(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            DateTime dateTime = dta.getDateTime(re.pubDate);
            if (dateTime.isAfter(new DateTime().minusDays(7))) {
                filteredRSSList.add(re);
            }
        }
        return filteredRSSList;
    }

    public ArrayList<RSSElement> filterByDate(ArrayList<RSSElement> RSSList, DateTime selectedDate) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            DateTime dateTime = dta.getDateTime(re.pubDate);
            if (dateTime.isAfter(selectedDate) && dateTime.isBefore(selectedDate.plusDays(1))) {
                filteredRSSList.add(re);
            }
        }
        if (filteredRSSList.size()==0) {
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "No news found for " + dta.formatDateTime(selectedDate),
                    Toast.LENGTH_SHORT
            );
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(255, 105, 97), PorterDuff.Mode.SRC_IN);
            toast.show();
        }
        return filteredRSSList;
    }

    public ArrayList<RSSElement> filterByTerm(ArrayList<RSSElement> RSSList, String searchTerm) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            String newsTitle = re.title.toLowerCase().trim();
            String newsCategory = re.getConcatedCategory().toLowerCase().trim();
            if (newsTitle.contains(searchTerm) || newsCategory.contains(searchTerm)) {
                filteredRSSList.add(re);
            }
        }
        if (filteredRSSList.size()==0) {
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "Search term yielded no result",
                    Toast.LENGTH_SHORT
            );
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.rgb(255, 105, 97), PorterDuff.Mode.SRC_IN);
            toast.show();
        }
        return filteredRSSList;
    }

    public ArrayList<RSSElement> filterByTermWithoutToast(ArrayList<RSSElement> RSSList, String searchTerm) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            String newsTitle = re.title.toLowerCase().trim();
            String newsCategory = re.getConcatedCategory().toLowerCase().trim();
            if (newsTitle.contains(searchTerm) || newsCategory.contains(searchTerm)) {
                filteredRSSList.add(re);
            }
        }
        return filteredRSSList;
    }

    public ArrayList<RSSElement> sortNewestFirst(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        Collections.sort(RSSList, new Comparator<RSSElement>() {
            @Override
            public int compare(RSSElement re1, RSSElement re2) {
                if (dta.getDateTime(re1.pubDate).isBefore(dta.getDateTime(re2.pubDate))) return 1;
                else return -1;
            }
        });
        for (RSSElement re: RSSList) filteredRSSList.add(re);
        return filteredRSSList;
    }

    public ArrayList<RSSElement> sortOldestFirst(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        Collections.sort(RSSList, new Comparator<RSSElement>() {
            @Override
            public int compare(RSSElement re1, RSSElement re2) {
                if (dta.getDateTime(re1.pubDate).isBefore(dta.getDateTime(re2.pubDate))) return -1;
                else return 1;
            }
        });
        for (RSSElement re: RSSList) filteredRSSList.add(re);
        return filteredRSSList;
    }

    /*
    public ArrayList<RSSElement> sortBySource(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        Collections.sort(RSSList, new Comparator<RSSElement>() {
            @Override
            public int compare(RSSElement re1, RSSElement re2) {
                if (re1.getNewsSource(re1.link).compareTo(re2.getNewsSource(re2.link))>0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        for (RSSElement re: RSSList) filteredRSSList.add(re);
        return filteredRSSList;
    }
    */

    public ArrayList<RSSElement> sortByTitle(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        Collections.sort(RSSList, new Comparator<RSSElement>() {
            @Override
            public int compare(RSSElement re1, RSSElement re2) {
                if (re1.title.compareTo(re2.title)>0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        for (RSSElement re: RSSList) filteredRSSList.add(re);
        return filteredRSSList;
    }


}
