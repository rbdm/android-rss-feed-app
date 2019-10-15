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
    private static final RSSElement RSS_ELEMENT = new RSSElement("", "", "");
    private static final ArrayList<CheckBox> CHECKBOXLIST = new ArrayList<>();


    private ArrayList<RSSElement> RSSElementList;
    private RSSElement rssElement;
    private ArrayList<LinearLayout> layoutList;
    private ArrayList<CheckBox> checkBoxList;


    @Override
    public void onCreate() {
        super.onCreate();

        //Set initial values
        setLayoutList(LAYOUTLIST);
        setCheckBoxList(CHECKBOXLIST);
        setRssElement(RSS_ELEMENT);
        setRSSElementList(RSS_ELEMENTS_lIST);

        //Add check box
        addCheckbox();
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
        addOneCheckBox("BBC world news", "http://feeds.bbci.co.uk/news/world/rss.xml");
        addOneCheckBox("ABC world news", "http://www.abc.net.au/news/feed/51120/rss.xml");
        addOneCheckBox("CNBC topStories news", "https://www.cnbc.com/id/100003114/device/rss/rss.html");
        addOneCheckBox("CBS topStories news", "https://www.cbsnews.com/latest/rss/main/");
        addOneCheckBox("NBC politics news", "http://feeds.nbcnews.com/nbcnews/public/politics");
        addOneCheckBox("BBC business news", "http://feeds.bbci.co.uk/news/business/rss.xml");
        addOneCheckBox("BBC technologynews", "http://feeds.bbci.co.uk/news/business/rss.xml");
        addOneCheckBox("New York Times Sports news", "http://feeds1.nytimes.com/nyt/rss/Sports");
        addOneCheckBox("Techworld news", "https://www.techworld.com/news/rss");
        addOneCheckBox("BBC business news", "http://feeds.bbci.co.uk/news/business/rss.xml");

    }
}
