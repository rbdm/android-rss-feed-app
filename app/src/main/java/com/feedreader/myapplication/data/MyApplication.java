package com.feedreader.myapplication.data;

import android.app.Application;
import android.widget.Button;
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
    private static final ArrayList<MyContent> MYDATA = new ArrayList<>();
    private static final MyContent CONTENT = new MyContent("");


    private ArrayList<String> collectionList;
    private ArrayList<MyContent> contentList;
    private MyContent content;
    private ArrayList<LinearLayout> layoutList;
    private ArrayList<Button> buttonList;


    @Override
    public void onCreate() {
        super.onCreate();

        //Set initial values
        setButtonList(BUTTONLIST);
        setLayoutList(LAYOUTLIST);
        setCollectionList(COLLECTIONLIST);
        setContentList(MYDATA);
        setContent(CONTENT);

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

    public void setContent(MyContent content) {
        this.content = content;
    }

    public MyContent getContent() {
        return content;
    }

    public ArrayList<MyContent> getContentList() {
        return contentList;
    }

    public void setContentList(ArrayList<MyContent> contentList) {
        this.contentList = contentList;
    }

    public ArrayList<String> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(ArrayList<String> collectionList) {
        this.collectionList = collectionList;
    }


}
