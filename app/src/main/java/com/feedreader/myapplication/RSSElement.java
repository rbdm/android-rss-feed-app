package com.feedreader.myapplication;

public class RSSElement {
    public String title;
    public String link;
    public String pubdate;
    public String image;
    public String category;
    public RSSElement(){

    }
    public RSSElement(String title, String link, String pubdate,String image,String category) {
        this.title = title;
        this.link = link;
        this.pubdate = pubdate;
        this.category=category;
    }
}
