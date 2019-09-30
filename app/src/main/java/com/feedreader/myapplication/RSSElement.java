package com.feedreader.myapplication;

public class RSSElement {
    public String title;
    public String link;
    public String pubdate;
    public RSSElement(){

    }
    public RSSElement(String title, String link, String pubdate,String description) {
        this.title = title;
        this.link = link;
        this.pubdate = pubdate;

    }
}
