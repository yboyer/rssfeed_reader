package fr.unicaen.info.dnr.rssapp.sqlite.rssitem;

import android.provider.BaseColumns;
import fr.unicaen.info.dnr.rssapp.sqlite.rssfeed.RSSFeedDbOperation;


public class RSSItemDbOperation {

    public static String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + ItemEntry.TABLE_NAME + " (" +
            ItemEntry._ID + " INTEGER PRIMARY KEY," +
            ItemEntry.COLUMN_NAME_TITLE + " TEXT," +
            ItemEntry.COLUMN_NAME_CONTENT + " TEXT," +
            ItemEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
            ItemEntry.COLUMN_NAME_LINK + " TEXT," +
            ItemEntry.COLUMN_NAME_PUBDATE + " TEXT," +
            ItemEntry.COLUMN_NAME_FEEDID + " INTEGER," +
            "FOREIGN KEY(" + ItemEntry.COLUMN_NAME_FEEDID + ") REFERENCES " + RSSFeedDbOperation.FeedEntry.TABLE_NAME + "(" + RSSFeedDbOperation.FeedEntry._ID + ")" +
        ")";

    public static String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME;

    public static class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "rssitem";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_PUBDATE = "pubdate";
        public static final String COLUMN_NAME_FEEDID = "feedid";
    }
}
