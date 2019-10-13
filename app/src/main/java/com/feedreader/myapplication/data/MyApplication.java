package com.feedreader.myapplication.data;

import android.app.Application;
import android.graphics.Color;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Class control all global variables in the whole Android Application
 */
public class MyApplication extends Application implements Serializable {

    //Provide initial values for all global variables
    private static final ArrayList<Button> BUTTONLIST = new ArrayList<>();
    private static final ArrayList<LinearLayout> LAYOUTLIST = new ArrayList<>();
    private static final ArrayList<String> COLLECTIONLIST = new ArrayList<>();
    private static final ArrayList<Content> MYDATA = new ArrayList<>();
    private static final Content CONTENT = new Content("");
    private static final ArrayList<CheckBox> CHECKBOXLIST = new ArrayList<>();


    private ArrayList<String> collectionList;
    private ArrayList<Content> contentList;
    private Content content;
    private ArrayList<LinearLayout> layoutList;
    private ArrayList<Button> buttonList;
    private ArrayList<CheckBox> checkBoxList;


    @Override
    public void onCreate() {
        super.onCreate();

        //Set initial values

        setButtonList(BUTTONLIST);
        setLayoutList(LAYOUTLIST);
        setCollectionList(COLLECTIONLIST);
        setContentList(MYDATA);
        setContent(CONTENT);
        setCheckBoxList(CHECKBOXLIST);
        addCheckbox();

    }
    /* * Author: Mingzhen Ao
     * add one xml url
     */
    public void addonecheckbox(String text,String tag,int positionY){
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(text);
        checkBox.setTextColor(Color.WHITE);
        checkBox.setY(positionY);
        checkBox.setTag(tag);
        checkBoxList.add(checkBox);
    }
    /* * Author: Mingzhen Ao
     * add original xml url
     */
    public void addCheckbox() {
        addonecheckbox("BBC world news","http://feeds.bbci.co.uk/news/world/rss.xml",0);
        addonecheckbox("ABC world news","http://www.abc.net.au/news/feed/51120/rss.xml",25);
        addonecheckbox("CNBC topStories news","https://www.cnbc.com/id/100003114/device/rss/rss.html",50);
        addonecheckbox("CBS topStories news","https://www.cbsnews.com/latest/rss/main/",75);
        addonecheckbox("NBC politics news","http://feeds.nbcnews.com/nbcnews/public/politics",100);
        addonecheckbox("BBC business news","http://feeds.bbci.co.uk/news/business/rss.xml",125);
        addonecheckbox("BBC technologynews","http://feeds.bbci.co.uk/news/business/rss.xml",150);
        addonecheckbox("New York Times Sports news","http://feeds1.nytimes.com/nyt/rss/Sports",175);
        addonecheckbox("Techworld news","https://www.techworld.com/news/rss",200);
        addonecheckbox("BBC business news","http://feeds.bbci.co.uk/news/business/rss.xml",225);

    }

    public void setCheckBoxList(ArrayList<CheckBox> checkBoxList) {
        this.checkBoxList = checkBoxList;

    }

    public ArrayList<CheckBox> getCheckBoxList() {

        return checkBoxList;
    }

    public ArrayList<Button> getButtonList() {
        return buttonList;
    }

    public void setButtonList(ArrayList<Button> buttonList) {

        this.buttonList = buttonList;
    }


    public void setLayoutList(ArrayList<LinearLayout> layoutList) {
        this.layoutList = layoutList;
    }

    public ArrayList<LinearLayout> getLayoutList() {

        return layoutList;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Content getContent() {
        return content;
    }

    public ArrayList<Content> getContentList() {
        return contentList;
    }

    public void setContentList(ArrayList<Content> contentList) {
        this.contentList = contentList;
    }

    public ArrayList<String> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(ArrayList<String> collectionList) {
        this.collectionList = collectionList;
    }


}
