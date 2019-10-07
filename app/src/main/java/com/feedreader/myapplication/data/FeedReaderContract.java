package com.feedreader.myapplication.data;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    public FeedReaderContract() {
    }

    public abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Favourites";
        public static final String _ID = BaseColumns._ID;
        public static final String Column_Name_Links = "EntryID";


    }
}
