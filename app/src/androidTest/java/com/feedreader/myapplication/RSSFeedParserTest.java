package com.feedreader.myapplication;


import com.feedreader.myapplication.tools.RSSFeedParser;

import org.junit.Test;


import static org.junit.Assert.*;

public class RSSFeedParserTest {
    @Test
    public void testRSSFeedweb() {
        String Validurl1 = "https://rss.csmonitor.com/feeds/usa";
        String Validurl2 = "https://www.npr.org/rss/rss.php?id=1003";
        String Invalidurl1 = "COOL";
        String Invalidurl2 = "just for test";
        RSSFeedParser parser = new RSSFeedParser();
        assertTrue(parser.getRSSfeedFromUrl(Validurl1)!=null);
        assertTrue(parser.getRSSfeedFromUrl(Validurl2)!=null);
        assertTrue(parser.getRSSfeedFromUrl(Invalidurl1)==null);
        assertTrue(parser.getRSSfeedFromUrl(Invalidurl2)==null);
    }
}
