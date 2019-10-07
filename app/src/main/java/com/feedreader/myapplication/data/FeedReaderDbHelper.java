package com.feedreader.myapplication.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Database helper for Pets app. Manages database creation and version management.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = FeedReaderDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "favourites.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link FeedReaderDbHelper}.
     *
     * @param context of the app
     */
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_FEEDS_TABLE = "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " ("
                + FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FeedReaderContract.FeedEntry.Column_Name_Links + " TEXT NOT NULL );";

        // db.execSQL(SQL_CREATE_FEEDS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
