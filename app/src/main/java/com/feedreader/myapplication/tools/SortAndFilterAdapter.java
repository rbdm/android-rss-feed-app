package com.feedreader.myapplication.tools;

import com.feedreader.myapplication.data.RSSElement;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import java.util.ArrayList;

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
        return filteredRSSList;
    }

    public ArrayList<RSSElement> filterByTerm(ArrayList<RSSElement> RSSList, String searchTerm) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            String newsTitle = re.title.toLowerCase().trim();
            String newsCategory = "";
            if (newsTitle.contains(searchTerm) || newsCategory.contains(searchTerm)) {
                filteredRSSList.add(re);
            }
        }
        return filteredRSSList;
    }

}
