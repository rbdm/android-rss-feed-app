package com.feedreader.myapplication;


import com.feedreader.myapplication.data.RSSElement;
import com.feedreader.myapplication.tools.RSSFeedParser;

import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;
/*
 * Author: Mingzhen Ao
 * This class aims to check whether we successful
 * parser information from internet
 */
public class RSSFeedParserTest {
    String Validurl1 = "https://rss.csmonitor.com/feeds/usa";
    String Validurl2 = "https://www.npr.org/rss/rss.php?id=1003";
    String Invalidurl1 = "COOL";
    String Invalidurl2 = "just for test";
    /*
     * Author: Mingzhen Ao
     * test web
     */
    @Test
    public void testRSSFeedweb() {
        RSSFeedParser parser = new RSSFeedParser();
        assertTrue(parser.getRSSfeedFromUrl(Validurl1)!=null);
        assertTrue(parser.getRSSfeedFromUrl(Validurl2)!=null);
        assertTrue(parser.getRSSfeedFromUrl(Invalidurl1)==null);
        assertTrue(parser.getRSSfeedFromUrl(Invalidurl2)==null);
    }
    /*
     * Author: Mingzhen Ao
     * test whether valid web1 can get information
     */
    @Test
    public void testRSSFeedItem1() {
        RSSFeedParser parser = new RSSFeedParser();
        ArrayList<RSSElement> a=parser.getRSSfeedFromUrl(Validurl1);
        for(int i=0;i<a.size();i++)
        {assertTrue(a.get(i).link!=null);
        assertTrue(a.get(i).pubDate!=null);
        assertTrue(a.get(i).title!=null);}
    }
    /*
     * Author: Mingzhen Ao
     * test whether valid web2 can get information
     */
    @Test
    public void testRSSFeedItem2() {
        RSSFeedParser parser = new RSSFeedParser();
        ArrayList<RSSElement> a=parser.getRSSfeedFromUrl(Validurl2);
        for(int i=0;i<a.size();i++)
        {assertTrue(a.get(i).link!=null);
            assertTrue(a.get(i).pubDate!=null);
            assertTrue(a.get(i).title!=null);}
    }
}
