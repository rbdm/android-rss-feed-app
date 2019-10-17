package com.feedreader.myapplication;

import com.feedreader.myapplication.data.RSSElement;
import com.feedreader.myapplication.tools.DateTimeAdapter;
import com.feedreader.myapplication.tools.RSSFeedParser;
import com.feedreader.myapplication.tools.SortAndFilterAdapter;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilterTest {
    SortAndFilterAdapter sfa = new SortAndFilterAdapter();
    DateTimeAdapter dta = new DateTimeAdapter();

    @Test
    public void testFilterToday() {
        DateTime[] testInput = new DateTime[]{
                new DateTime(), //current time
                new DateTime().withTimeAtStartOfDay(),
                new DateTime().minusDays(1),
                new DateTime().minusDays(2),
                new DateTime().minusDays(6),
                new DateTime().minusDays(8),
        };

        boolean[] expectedOutput = new boolean[]{
                true,
                true,
                false,
                false,
                false,
                false
        };

        for (int i=0; i<testInput.length; i++) {
            RSSElement re = new RSSElement();
            ArrayList<RSSElement> reList = new ArrayList<>();
            reList.add(re);
            re.setPubDate(dta.formatDateTime(testInput[i]));
            assertEquals(sfa.filterTodayWithoutToast(reList).size()>0, expectedOutput[i]);
        }
    }

    @Test
    public void testFilterLastHour() {
        DateTime[] testInput = new DateTime[]{
                new DateTime(), //current time
                new DateTime().minusHours(1),
                new DateTime().minusMinutes(50),
                new DateTime().minusDays(2),
                new DateTime().minusDays(6),
                new DateTime().minusDays(8),
        };

        boolean[] expectedOutput = new boolean[]{
                true,
                true,
                true,
                false,
                false,
                false
        };

        for (int i=0; i<testInput.length; i++) {
            RSSElement re = new RSSElement();
            ArrayList<RSSElement> reList = new ArrayList<>();
            reList.add(re);
            re.setPubDate(dta.formatDateTime(testInput[i]));
            assertEquals(sfa.filterLastHourWithoutToast(reList).size()>0, expectedOutput[i]);
        }
    }

    @Test
    public void testFilterThisWeek() {
        DateTime[] testInput = new DateTime[]{
                new DateTime(), //current time
                new DateTime().withTimeAtStartOfDay(),
                new DateTime().minusDays(1),
                new DateTime().minusDays(7),
                new DateTime().minusDays(8),
                new DateTime().minusDays(9),
        };

        boolean[] expectedOutput = new boolean[]{
                true,
                true,
                true,
                true,
                false,
                false
        };

        for (int i=0; i<testInput.length; i++) {
            RSSElement re = new RSSElement();
            ArrayList<RSSElement> reList = new ArrayList<>();
            reList.add(re);
            re.setPubDate(dta.formatDateTime(testInput[i]));
            assertEquals(sfa.filterThisWeekWithoutToast(reList).size()>0, expectedOutput[i]);
        }
    }
}
