package fr.unicaen.info.dnr.rssapp.sqlite.rssfeed;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;


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
        values.put(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_LASTUPDATE, System.currentTimeMillis() / 1000);

        Log.d("RSSFeedDb:adding", item.toString());

        return db.insert(RSSFeedDbOperation.FeedEntry.TABLE_NAME, null, values);
    }

    /**
     * Update the RSSFeed item
     * @param db The database
     * @param feedEntry The feed to update
     */
    public static void update(SQLiteDatabase db, RSSFeed feedEntry) {
        ContentValues values = new ContentValues();
        values.put(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME, feedEntry.getName());
        values.put(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL, feedEntry.getUrl());
        values.put(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_LASTUPDATE, System.currentTimeMillis() / 1000);

        db.update(
            RSSFeedDbOperation.FeedEntry.TABLE_NAME,
            values,
            RSSFeedDbOperation.FeedEntry._ID + " = ?",
            new String[]{ feedEntry.getId() + "" }
        );
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
     * Get a RSS feed using his id.
     * @param db : database.
     * @param feedId : id of the RSS feed.
     * @return The RSS feed.
     */
    public static RSSFeed getFeedById(SQLiteDatabase db, long feedId) {
        String[] projection = {
            RSSFeedDbOperation.FeedEntry._ID,
            RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME,
            RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL,
            RSSFeedDbOperation.FeedEntry.COLUMN_NAME_LASTUPDATE
        };

        // Find in db if there is a RSS feed with same id
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

        // If there is a result, return the RSS feed
        if (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL));
            long lastupdate = cursor.getLong(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_LASTUPDATE));

            feed = new RSSFeed(id, name, url, lastupdate);
        }
        cursor.close();

        return feed;
    }

    /**
     * Get a RSS feed using his name and URL.
     * @param db : database.
     * @param paramName : name of the RSS feed.
     * @param paramUrl : url of the RSS feed.
     * @return : the RSS feed.
     */
    public static RSSFeed getFeedByNameOrUrl(SQLiteDatabase db, String paramName, String paramUrl) {
        String[] projection = {
                RSSFeedDbOperation.FeedEntry._ID,
                RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME,
                RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL,
                RSSFeedDbOperation.FeedEntry.COLUMN_NAME_LASTUPDATE,
        };

        // Find in database if there is a RSS feed with same name or same url
        String selection = RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL + " = ? OR " + RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME + " = ?";
        String[] where = { paramName, paramUrl };

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

        // If there is a result, return the RSS feed
        if (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL));
            long lastupdate = cursor.getLong(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_LASTUPDATE));

            feed = new RSSFeed(id, name, url, lastupdate);
        }
        cursor.close();

        return feed;
    }

    /**
     * Get all the feeds from the database.
     * @param db : database.
     * @return : List of RSS feeds.
     */
    public static List<RSSFeed> getAllFeeds(SQLiteDatabase db) {
        Cursor cursor = db.query(
                RSSFeedDbOperation.FeedEntry.TABLE_NAME, // The table to query
                null, // The columns to return
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );
        // List of RSS Feeds
        List<RSSFeed> feeds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL));
            long lastupdate = cursor.getLong(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_LASTUPDATE));

            feeds.add(new RSSFeed(id, name, url, lastupdate));
        }
        cursor.close();
        return feeds;
    }

    /**
     * Get the feeds cursor
     * @param db The database
     * @return The cursor
     */
    public static Cursor getFeedsCursor(SQLiteDatabase db) {
        return db.query(
            RSSFeedDbOperation.FeedEntry.TABLE_NAME, // The table to query
            null, // The columns to return
            null, // The columns for the WHERE clause
            null, // The values for the WHERE clause
            null, // don't group the rows
            null, // don't filter by row groups
            RSSFeedDbOperation.FeedEntry.COLUMN_NAME_NAME + " ASC" // The sort order
        );
    }
}
