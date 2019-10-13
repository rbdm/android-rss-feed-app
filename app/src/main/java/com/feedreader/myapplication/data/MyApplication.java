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
    private static final Content CONTENT = new Content("", "", "");
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

    public void addCheckbox() {
        CheckBox checkBox1 = new CheckBox(this);
        checkBox1.setText("BBC world news");
        checkBox1.setTextColor(Color.WHITE);
        checkBox1.setY(0);
        checkBox1.setTag("http://feeds.bbci.co.uk/news/world/rss.xml");
        checkBoxList.add(checkBox1);


        CheckBox checkBox2 = new CheckBox(this);
        checkBox2.setText("ABC world news");
        checkBox2.setTextColor(Color.WHITE);
        checkBox2.setY(25);
        checkBox2.setTag("http://www.abc.net.au/news/feed/51120/rss.xml");
        checkBoxList.add(checkBox2);


        CheckBox checkBox3 = new CheckBox(this);
        checkBox3.setText("CNBC topStories news");
        checkBox3.setTextColor(Color.WHITE);
        checkBox3.setY(50);
        checkBox3.setTag("https://www.cnbc.com/id/100003114/device/rss/rss.html");
        checkBoxList.add(checkBox3);


        CheckBox checkBox4 = new CheckBox(this);
        checkBox4.setText("CBS topStories news");
        checkBox4.setTextColor(Color.WHITE);
        checkBox4.setY(75);
        checkBox4.setTag("https://www.cbsnews.com/latest/rss/main/");
        checkBoxList.add(checkBox4);


        CheckBox checkBox5 = new CheckBox(this);
        checkBox5.setText("NBC politics news");
        checkBox5.setTag("http://feeds.nbcnews.com/nbcnews/public/politics");
        checkBox5.setY(100);
        checkBox5.setTextColor(Color.WHITE);
        checkBoxList.add(checkBox5);


        CheckBox checkBox6 = new CheckBox(this);
        checkBox6.setText("BBC business news");
        checkBox6.setTag("http://feeds.bbci.co.uk/news/business/rss.xml");
        checkBox6.setY(125);
        checkBox6.setTextColor(Color.WHITE);
        checkBoxList.add(checkBox6);


        CheckBox checkBox7 = new CheckBox(this);
        checkBox7.setText("BBC technologynews");
        checkBox7.setTag("http://feeds.bbci.co.uk/news/business/rss.xml");
        checkBox7.setY(150);
        checkBox7.setTextColor(Color.WHITE);
        checkBoxList.add(checkBox7);


        CheckBox checkBox8 = new CheckBox(this);
        checkBox8.setText("New York Times Sports news");
        checkBox8.setTag("http://feeds1.nytimes.com/nyt/rss/Sports");
        checkBox8.setY(175);
        checkBox8.setTextColor(Color.WHITE);
        checkBoxList.add(checkBox8);


        CheckBox checkBox9 = new CheckBox(this);
        checkBox9.setText("Techworld news");
        checkBox9.setTag("https://www.techworld.com/news/rss");
        checkBox9.setY(200);
        checkBox9.setTextColor(Color.WHITE);
        checkBoxList.add(checkBox9);


        CheckBox checkBox10 = new CheckBox(this);
        checkBox10.setText("BBC business news");
        checkBox10.setTag("http://feeds.bbci.co.uk/news/business/rss.xml");
        checkBox10.setY(225);
        checkBox10.setTextColor(Color.WHITE);
        checkBoxList.add(checkBox10);


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
