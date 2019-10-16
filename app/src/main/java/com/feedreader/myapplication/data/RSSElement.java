package com.feedreader.myapplication.data;

/* *
 * Author: Mingzhen Ao & Zixin Ye
 * This class used to get title, link ,and pubDate of the rss feed
 */
public class RSSElement {
    public String title;
    public String link;
    public String pubDate;
    public String source;

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
        this.source = getNewsSource(link);
    }


    public String getNewsSource(String s) {
        String out = "";
        switch(s) {
            case "http://feeds.bbci.co.uk/news/world/rss.xml":
                out = "BBC World News";
                break;
            case "http://www.abc.net.au/news/feed/51120/rss.xml":
                out = "ABC World News";
                break;
            case "https://www.cnbc.com/id/100003114/device/rss/rss.html":
                out = "CNBC TopStories News";
                break;
            case "https://www.cbsnews.com/latest/rss/main/":
                out = "CBS TopStories News";
                break;
            case "http://feeds.nbcnews.com/nbcnews/public/politics":
                out = "NBC Politics";
                break;
            case "http://feeds.bbci.co.uk/news/business/rss.xml":
                out = "BBC Business";
                break;
            case "http://feeds.bbci.co.uk/news/technology/rss.xml":
                out = "BBC Technology";
                break;
            case "http://feeds1.nytimes.com/nyt/rss/Sports":
                out = "New York Times Sports";
                break;
            case "https://www.techworld.com/news/rss":
                out = "Techworld News";
                break;
        }
        return out;
    }
}
