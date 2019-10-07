package com.feedreader.myapplication;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.LitePal;

import java.io.Serializable;
import java.util.ArrayList;

public class MyApplication extends Application implements Serializable {
    private static final ArrayList<String> COLLECTIONLIST = new ArrayList<>();
    private ArrayList<String> collectionList;

    @Override
    public void onCreate() {
        super.onCreate();

        LitePal.initialize(this);
        SQLiteDatabase db = LitePal.getDatabase();

        setCollectionList(COLLECTIONLIST);

    }

    public ArrayList<String> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(ArrayList<String> collectionList) {
        this.collectionList = collectionList;
    }



}
