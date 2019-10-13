package com.feedreader.myapplication.data;

/* * Author: Mingzhen Ao
 * This class used to get title, link ,and pubdate of the rss feed
 */
public class RSSElement {
    public String title;
    public String link;
    public String pubdate;
    public RSSElement(){

    }
    public RSSElement(String title, String link, String pubdate) {
        this.title = title;
        this.link = link;
        this.pubdate = pubdate;
    }
}
