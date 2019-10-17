package com.feedreader.myapplication;

import com.feedreader.myapplication.data.RSSElement;
import com.feedreader.myapplication.tools.DateTimeAdapter;
import com.feedreader.myapplication.tools.SortAndFilterAdapter;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SearchTest {
    SortAndFilterAdapter sfa = new SortAndFilterAdapter();
    DateTimeAdapter dta = new DateTimeAdapter();

    @Test
    public void testSearch() {
        String[] testInput = new String[]{
                "aaaaaaaaaaaaaa", //current time
                "aaa",
                "my_application",
                "a a aa a a a aa a",
                " aa\n \r\naaa \n\r\n  ",
                "adadasaefa    "
        };

        boolean[] expectedOutput = new boolean[]{
                true,
                true,
                false,
                false,
                true,
                false
        };

        for (int i=0; i<testInput.length; i++) {
            RSSElement re = new RSSElement();
            ArrayList<RSSElement> reList = new ArrayList<>();
            reList.add(re);
            re.setTitle(testInput[i]);
            assertEquals(sfa.filterByTermWithoutToast(reList, "aaa").size()>0, expectedOutput[i]);
        }
    }
}
