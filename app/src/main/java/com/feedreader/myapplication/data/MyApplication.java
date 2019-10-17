package com.feedreader.myapplication.data;

import android.app.Application;
import android.graphics.Color;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: Zixin Ye
 * This Class control all global variables in the whole Android Application
 */
public class MyApplication extends Application implements Serializable {

    //Provide initial values for all global variables
    private static final ArrayList<LinearLayout> LAYOUTLIST = new ArrayList<>();
    private static final ArrayList<RSSElement> RSS_ELEMENTS_lIST = new ArrayList<>();
    private static final ArrayList<RSSElement> NEWS_LIST = new ArrayList<>();
    private static final RSSElement RSS_ELEMENT = new RSSElement("", "", "");
    private static final ArrayList<CheckBox> CHECKBOXLIST = new ArrayList<>();
    private static final ArrayList<String> URL_LIST = new ArrayList<>();

    private ArrayList<RSSElement> filteredNewsList;
    private ArrayList<RSSElement> newsList;
    private ArrayList<RSSElement> RSSElementList;
    private RSSElement rssElement;
    private ArrayList<LinearLayout> layoutList;
    private ArrayList<CheckBox> checkBoxList;
    private String url;
    private ArrayList<String> urlList;


    @Override
    public void onCreate() {
        super.onCreate();

        //Set initial values
        setLayoutList(LAYOUTLIST);
        setCheckBoxList(CHECKBOXLIST);
        setRssElement(RSS_ELEMENT);
        setRSSElementList(RSS_ELEMENTS_lIST);
        setNewsList(NEWS_LIST);
        setUrlList(URL_LIST);

        //Add check box
        addCheckbox();
    }
    public void setUrlList(ArrayList<String> urlList) {
        this.urlList = urlList;
    }

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    public void setFilteredNewsList(ArrayList<RSSElement> filteredNewsList) {
        this.filteredNewsList = filteredNewsList;
    }

    public ArrayList<RSSElement> getFilteredNewsList() {
        return filteredNewsList;
    }


    public void setNewsList(ArrayList<RSSElement> newsList) {
        this.newsList = newsList;
    }

    public ArrayList<RSSElement> getNewsList() {
        return newsList;
    }

    public void setRSSElementList(ArrayList<RSSElement> RSSElementList) {
        this.RSSElementList = RSSElementList;
    }

    public void setRssElement(RSSElement rssElement) {
        this.rssElement = rssElement;
    }

    public void setLayoutList(ArrayList<LinearLayout> layoutList) {
        this.layoutList = layoutList;
    }

    public ArrayList<RSSElement> getRSSElementList() {
        return RSSElementList;
    }

    public RSSElement getRssElement() {
        return rssElement;
    }

    public ArrayList<LinearLayout> getLayoutList() {
        return layoutList;
    }

    public ArrayList<CheckBox> getCheckBoxList() {
        return checkBoxList;
    }

    public void setCheckBoxList(ArrayList<CheckBox> checkBoxList) {
        this.checkBoxList = checkBoxList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<RSSElement> addNewsSource(ArrayList<RSSElement> newList) {

        for (RSSElement re: newList) newsList.add(re);
        return newsList;
    }

    /* *
     * Author: Mingzhen Ao
     * Add one xml url
     */
    public void addOneCheckBox(String text, String tag) {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(text);
        checkBox.setTextColor(Color.WHITE);
        checkBox.setTag(tag);
        checkBoxList.add(checkBox);
    }

    /* *
     * Author: Mingzhen Ao
     * Add original xml url
     */
    public void addCheckbox() {
        addOneCheckBox("BBC World News", "http://feeds.bbci.co.uk/news/world/rss.xml");
        addOneCheckBox("ABC World News", "http://www.abc.net.au/news/feed/51120/rss.xml");
        addOneCheckBox("CNBC TopStories News", "https://www.cnbc.com/id/100003114/device/rss/rss.html");
        addOneCheckBox("CBS TopStories News", "https://www.cbsnews.com/latest/rss/main/");
        addOneCheckBox("NBC Politics News", "http://feeds.nbcnews.com/nbcnews/public/politics");
        addOneCheckBox("BBC Business News", "http://feeds.bbci.co.uk/news/business/rss.xml");
        addOneCheckBox("BBC Technologynews", "http://feeds.bbci.co.uk/news/technology/rss.xml");
        addOneCheckBox("New York Times Sports", "http://feeds1.nytimes.com/nyt/rss/Sports");
        addOneCheckBox("Techworld News", "https://www.techworld.com/news/rss");
        addOneCheckBox("BBC Science News", "http://feeds.bbci.co.uk/news/science_and_environment/rss.xml");

    }
}
