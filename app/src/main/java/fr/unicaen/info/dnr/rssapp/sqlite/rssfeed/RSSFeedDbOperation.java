package fr.unicaen.info.dnr.rssapp.sqlite.rssfeed;

import android.provider.BaseColumns;

/**
 * Created by lenaic on 11/01/2017.
 */

public class RSSFeedDbOperation {

    protected static String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_NAME_NAME + " TEXT," +
            FeedEntry.COLUMN_NAME_URL + " TEXT" +
        ")";

    protected static String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    /* Inner class that defines the table contents */
    protected static class FeedEntry implements BaseColumns {
        protected static final String TABLE_NAME = "rssfeed";
        protected static final String COLUMN_NAME_NAME = "name";
        protected static final String COLUMN_NAME_URL = "url";
    }
}
