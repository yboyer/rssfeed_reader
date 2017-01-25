package fr.unicaen.info.dnr.rssapp.sqlite.rssfeed;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;


/**
 * Created by lenaic on 11/01/2017.
 */
public final class RSSFeedDb {

    /**
     * Create a new link from an RSSFeed in our database.
     * @param db : database.
     * @param item : Object RSSFeed.
     * @return The inserted id
     */
    public static long add(SQLiteDatabase db, RSSFeed item) {
        ContentValues values = new ContentValues();
        values.put(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME, item.getName());
        values.put(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL, item.getUrl());

        Log.d("RSSFeedDb:adding", item.toString());

        return db.insert(RSSFeedDbOperation.FeedEntry.TABLE_NAME, null, values);
    }

    /**
     * Delete a link using an id in our database.
     * @param db : database.
     * @param id : link identifier.
     */
    public static void delete(SQLiteDatabase db, long id) {
        String selection = RSSFeedDbOperation.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = { id+"" };
        db.delete(RSSFeedDbOperation.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Get a rss feed
     * @param db : database.
     * @param args : arguments from the where clause.
     * @return The RSS feed.
     */
    public static RSSFeed get(SQLiteDatabase db, long feedId) {
        String[] projection = {
            RSSFeedDbOperation.FeedEntry._ID,
            RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME,
            RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL,
        };

        String selection = RSSFeedDbOperation.FeedEntry._ID + " = ?";
        String[] where = { feedId + "" };

        Cursor cursor = db.query(
            RSSFeedDbOperation.FeedEntry.TABLE_NAME, // The table to query
            projection, // The columns to return
            selection, // The columns for the WHERE clause
            where, // The values for the WHERE clause
            null, // don't group the rows
            null, // don't filter by row groups
            null // The sort order
        );

        RSSFeed feed = null;

        if (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL));

            feed = new RSSFeed(id, name, url);
        }
        cursor.close();

        return feed;
    }

    /**
     * Get a rss feed
     * @param db : database.
     * @param args : arguments from the where clause.
     * @return The RSS feed.
     */
    public static List<RSSFeed> getAll(SQLiteDatabase db) {
        String[] projection = {
            RSSFeedDbOperation.FeedEntry._ID,
            RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME,
            RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL,
        };

        String selection = RSSFeedDbOperation.FeedEntry._ID + " = ?";
        String[] where = {};

        Cursor cursor = db.query(
            RSSFeedDbOperation.FeedEntry.TABLE_NAME, // The table to query
            projection, // The columns to return
            null, // The columns for the WHERE clause
            null, // The values for the WHERE clause
            null, // don't group the rows
            null, // don't filter by row groups
            null // The sort order
        );

        List feeds = new ArrayList();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL));

            feeds.add(new RSSFeed(id, name, url));
        }
        cursor.close();

        return feeds;
    }
}
