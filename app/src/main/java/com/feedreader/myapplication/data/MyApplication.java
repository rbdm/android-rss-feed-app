package com.feedreader.myapplication.data;

import android.app.Application;
import android.graphics.Color;
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
    private static final ArrayList<News> MYDATA = new ArrayList<>();
    private static final News NEWS = new News("", "", "");
    private static final ArrayList<CheckBox> CHECKBOXLIST = new ArrayList<>();


    private ArrayList<News> newsList;
    private News news;
    private ArrayList<LinearLayout> layoutList;
    private ArrayList<CheckBox> checkBoxList;


    @Override
    public void onCreate() {
        super.onCreate();

        //Set initial values
        setLayoutList(LAYOUTLIST);
        setNewsList(NEWSLIST);
        setNews(NEWS);
        setCheckBoxList(CHECKBOXLIST);

        //Add check box
        addCheckbox();
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    public News getNews() {
        return news;
    }

    public ArrayList<LinearLayout> getLayoutList() {
        return layoutList;
    }

    public ArrayList<CheckBox> getCheckBoxList() {
        return checkBoxList;
    }

    public void setNewsList(ArrayList<News> newsList) {
        this.newsList = newsList;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public void setLayoutList(ArrayList<LinearLayout> layoutList) {
        this.layoutList = layoutList;
    }

    public void setCheckBoxList(ArrayList<CheckBox> checkBoxList) {
        this.checkBoxList = checkBoxList;
    }


    /* *
     * Author: Mingzhen Ao
     * Add one xml url
     */
    public void addOneCheckBox(String text, String tag, int positionY) {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(text);
        checkBox.setTextColor(Color.WHITE);
        checkBox.setY(positionY);
        checkBox.setTag(tag);
        checkBoxList.add(checkBox);
    }

    /* *
     * Author: Mingzhen Ao
     * Add original xml url
     */
    public void addCheckbox() {
        addOneCheckBox("BBC world news", "http://feeds.bbci.co.uk/news/world/rss.xml", 0);
        addOneCheckBox("ABC world news", "http://www.abc.net.au/news/feed/51120/rss.xml", 25);
        addOneCheckBox("CNBC topStories news", "https://www.cnbc.com/id/100003114/device/rss/rss.html", 50);
        addOneCheckBox("CBS topStories news", "https://www.cbsnews.com/latest/rss/main/", 75);
        addOneCheckBox("NBC politics news", "http://feeds.nbcnews.com/nbcnews/public/politics", 100);
        addOneCheckBox("BBC business news", "http://feeds.bbci.co.uk/news/business/rss.xml", 125);
        addOneCheckBox("BBC technologynews", "http://feeds.bbci.co.uk/news/business/rss.xml", 150);
        addOneCheckBox("New York Times Sports news", "http://feeds1.nytimes.com/nyt/rss/Sports", 175);
        addOneCheckBox("Techworld news", "https://www.techworld.com/news/rss", 200);
        addOneCheckBox("BBC business news", "http://feeds.bbci.co.uk/news/business/rss.xml", 225);

    }
}
