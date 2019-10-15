package com.feedreader.myapplication.data;

/* *
 * Author: Mingzhen Ao & Zixin Ye
 * This class used to get title, link ,and pubDate of the rss feed
 */
public class RSSElement {
    public String title;
    public String link;
    public String pubDate;

    public RSSElement() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public RSSElement(String title, String link, String pubDate) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
    }
}
