package com.feedreader.myapplication.tools;

import com.feedreader.myapplication.data.RSSElement;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortAndFilterAdapter {
    DateTimeAdapter dta = new DateTimeAdapter();

    public ArrayList<RSSElement> filterToday(ArrayList<RSSElement> RSSList) {
        ArrayList<RSSElement> filteredRSSList = new ArrayList<>();
        for (RSSElement re: RSSList) {
            System.out.println(re.getLink());
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

    public ArrayList<RSSElement> sortNewestFirst(ArrayList<RSSElement> RSSList) {
        Collections.sort(RSSList, new Comparator<RSSElement>() {
            @Override
            public int compare(RSSElement re1, RSSElement re2) {
                if (dta.getDateTime(re1.pubDate).isBefore(dta.getDateTime(re2.pubDate))) return 1;
                else return -1;
            }
        });
        return RSSList;
    }

    public ArrayList<RSSElement> sortOldestFirst(ArrayList<RSSElement> RSSList) {
        Collections.sort(RSSList, new Comparator<RSSElement>() {
            @Override
            public int compare(RSSElement re1, RSSElement re2) {
                if (dta.getDateTime(re1.pubDate).isBefore(dta.getDateTime(re2.pubDate))) return -1;
                else return 1;
            }
        });
        return RSSList;
    }

    public ArrayList<RSSElement> sortBySource(ArrayList<RSSElement> RSSList) {
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
        return RSSList;
    }

    public ArrayList<RSSElement> sortByTitle(ArrayList<RSSElement> RSSList) {
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
        return RSSList;
    }


}
