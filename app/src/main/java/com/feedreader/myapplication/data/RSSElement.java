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

        if (s.contains("abc.net")) out = "ABC World News";
        else if (s.contains("cnbc.com")) out = "CNBC TopStories News";
        else if (s.contains("cbsnews.com")) out = "CBS TopStories News";
        else if (s.contains("nbcnews.com")) out = "NBC Politics";
        else if (s.contains("bbc.co.uk/news/business")) out = "BBC Business";
        else if (s.contains("bbc.co.uk/news/technology")) out = "BBC Technology";
        else if (s.contains("bbc.co.uk/news/science")) out = "BBC Science";
        else if (s.contains("bbc.co.uk")) out = "BBC World News";
        else if (s.contains("nytimes.com")) out = "New York Times Sports";
        else if (s.contains("techworld.com")) out = "Techworld News";

        else out = s;
        return out;
    }
}
