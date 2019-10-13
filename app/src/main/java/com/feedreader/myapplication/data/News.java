package com.feedreader.myapplication.data;

/**
 * Author: Zixin Ye
 * This class provide a template of the content of a piece of news
 */
public class News {
    private String url;
    private String title;
    private String date;

    public News(String url, String title, String date) {
        this.url = url;
        this.title = title;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
