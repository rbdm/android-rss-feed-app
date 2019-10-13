package com.feedreader.myapplication.data;

/* *
 * Author: Mingzhen Ao
 * This class used to get title, link ,and pubDate of the rss feed
 */
public class RSSElement {
    public String title;
    public String link;
    public String pubDate;

    public RSSElement() {

    }

    public RSSElement(String title, String link, String pubDate) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
    }
}
