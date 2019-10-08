package com.feedreader.myapplication.data;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    private FeedReaderContract() {
    }

    public static final class FeedEntry implements BaseColumns {
        public final static String TABLE_NAME = "pets";
        public static final String _ID = BaseColumns._ID;
        public static final String Column_Name_Links = "EntryID";
    }
}
