package fr.unicaen.info.dnr.rssapp.sqlite;

import android.provider.BaseColumns;

/**
 * Created by lenaic on 11/01/2017.
 */

public class MessageDbOperation {

    public static String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TEXT + " TEXT," +
                    FeedEntry.COLUMN_NAME_DATE + " TEXT)";

    public static String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "message";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
