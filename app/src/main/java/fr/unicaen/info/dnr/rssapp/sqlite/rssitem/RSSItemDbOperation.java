package fr.unicaen.info.dnr.rssapp.sqlite.rssitem;

import android.provider.BaseColumns;

/**
 * Created by lenaic on 11/01/2017.
 */
public class RSSItemDbOperation {

    public static String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_NAME_CONTENT + "TEXT," +
            FeedEntry.COLUMN_NAME_DESCRIPTION + "TEXT," +
            FeedEntry.COLUMN_NAME_LINK + "TEXT," +
            FeedEntry.COLUMN_NAME_PUBDATE + "TEXT" +
        ")";

    public static String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "rssitem";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_PUBDATE = "pubdate";
    }
}
